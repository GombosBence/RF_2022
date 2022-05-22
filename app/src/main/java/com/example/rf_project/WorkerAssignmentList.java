package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerAssignmentList extends AppCompatActivity {

    Button allBtn;
    Button pendingBtn;
    Button acceptedBtn;
    Button deniedBtn;
    Button solvedBtn;
    FirebaseDatabase fDatabase;
    FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
    String userId = loggedInUser.getUid();
    ArrayList<AssignmentInfo> allAl;
    ArrayList<AssignmentInfo> pendingAl;
    ArrayList<AssignmentInfo> acceptedAl;
    ArrayList<AssignmentInfo> deniedAl;
    ArrayList<AssignmentInfo> solvedAl;
    ListView taskList;
    WorkerAssignmentListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_assignment_list);
        allBtn = findViewById(R.id.allBtn);
        pendingBtn = findViewById(R.id.pendingBtn);
        acceptedBtn = findViewById(R.id.acceptedBtn);
        deniedBtn = findViewById(R.id.deniedBtn);
        solvedBtn = findViewById(R.id.solvedBtn);
        taskList = findViewById(R.id.TaskList);
        fDatabase = FirebaseDatabase.getInstance();
        allAl = new ArrayList<>();
        pendingAl = new ArrayList<>();
        acceptedAl = new ArrayList<>();
        deniedAl = new ArrayList<>();
        solvedAl = new ArrayList<>();

        adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,allAl);
        taskList.setAdapter(adapter);

        DatabaseReference dbRef = fDatabase.getReference("Assignments");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allAl.clear();
                pendingAl.clear();
                acceptedAl.clear();
                deniedAl.clear();
                solvedAl.clear();

                for(DataSnapshot ds : snapshot.getChildren()) {
                    String taskId = ds.child("Task_ID").getValue().toString();
                    String workerId = ds.child("Worker_ID").getValue().toString();
                    String assignmentInfo = ds.child("Assignment State").getValue().toString();
                    String workerName = ds.child("Worker's name").getValue().toString();
                    String description = ds.child("Description").getValue().toString();
                    String equipment = ds.child("Equipment").getValue().toString();
                    String assignmentId = ds.getKey();
                    String duration = ds.child("Duration").getValue().toString();

                    if(workerId.equals(userId))
                    {
                        allAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("pending") && workerId.equals(userId))
                    {
                        pendingAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("accepted") && workerId.equals(userId))
                    {
                        acceptedAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("denied") && workerId.equals(userId))
                    {
                        deniedAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("solved") && workerId.equals(userId))
                    {
                        solvedAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                }
                adapter.notifyDataSetChanged();
                taskList.setAdapter(adapter);


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void onAllClick(View v)
    {
        WorkerAssignmentListAdapter adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,allAl);
        taskList.setAdapter(adapter);
    }

    public void onPendingClick(View v)
    {
        WorkerAssignmentListAdapter adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,pendingAl);
        taskList.setAdapter(adapter);
    }

    public void onAcceptedClick(View v)
    {
        WorkerAssignmentListAdapter adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,acceptedAl);
        taskList.setAdapter(adapter);
    }

    public void onDeniedClick (View v)
    {
        WorkerAssignmentListAdapter adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,deniedAl);
        taskList.setAdapter(adapter);
    }

    public void onSolvedClick (View v)
    {
        WorkerAssignmentListAdapter adapter = new WorkerAssignmentListAdapter(WorkerAssignmentList.this,solvedAl);
        taskList.setAdapter(adapter);
    }
}