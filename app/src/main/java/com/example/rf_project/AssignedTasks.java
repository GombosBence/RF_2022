package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssignedTasks extends AppCompatActivity {

    Button allBtn;
    Button pendingBtn;
    Button acceptedBtn;
    Button deniedBtn;
    FirebaseDatabase fDatabase;
    ArrayList<AssignmentInfo> allAl;
    ArrayList<AssignmentInfo> pendingAl;
    ArrayList<AssignmentInfo> acceptedAl;
    ArrayList<AssignmentInfo> deniedAl;
    ListView assignmentList;
    AssignmentListViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_tasks);
        allBtn = findViewById(R.id.allBtn);
        pendingBtn = findViewById(R.id.pendingBtn);
        acceptedBtn = findViewById(R.id.acceptedBtn);
        deniedBtn = findViewById(R.id.deniedBtn);
        fDatabase = FirebaseDatabase.getInstance();
        allAl = new ArrayList<>();
        pendingAl = new ArrayList<>();
        acceptedAl = new ArrayList<>();
        deniedAl = new ArrayList<>();
        assignmentList = findViewById(R.id.TaskList);

       adapter = new AssignmentListViewAdapter(AssignedTasks.this,allAl);
       assignmentList.setAdapter(adapter);

        DatabaseReference dbRef = fDatabase.getReference("Assignments");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                allAl.clear();
                pendingAl.clear();
                acceptedAl.clear();
                deniedAl.clear();
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String taskId = ds.child("Task_ID").getValue().toString();
                    String workerId = ds.child("Worker_ID").getValue().toString();
                    String assignmentInfo = ds.child("Assignment State").getValue().toString();
                    String workerName = ds.child("Worker's name").getValue().toString();
                    String description = ds.child("Description").getValue().toString();
                    String equipment = ds.child("Equipment").getValue().toString();
                    String assignmentId = ds.getKey();
                    String duration = ds.child("Duration").getValue().toString();

                    allAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    if(assignmentInfo.equals("pending"))
                    {
                        pendingAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("accepted"))
                    {
                        acceptedAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                    if(assignmentInfo.equals("denied"))
                    {
                        deniedAl.add(new AssignmentInfo(taskId, workerId, assignmentInfo, workerName, description, equipment, assignmentId, duration));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void onAllClick(View v)
    {
        AssignmentListViewAdapter adapter = new AssignmentListViewAdapter(AssignedTasks.this,allAl);
        assignmentList.setAdapter(adapter);
    }

    public void onPendingClick(View v)
    {
        AssignmentListViewAdapter adapter = new AssignmentListViewAdapter(AssignedTasks.this,pendingAl);
        assignmentList.setAdapter(adapter);
    }

    public void onAcceptedClick(View v)
    {
        AssignmentListViewAdapter adapter = new AssignmentListViewAdapter(AssignedTasks.this,acceptedAl);
        assignmentList.setAdapter(adapter);
    }

    public void onDeniedClick (View v)
    {
        AssignmentListViewAdapter adapter = new AssignmentListViewAdapter(AssignedTasks.this,deniedAl);
        assignmentList.setAdapter(adapter);
    }
}