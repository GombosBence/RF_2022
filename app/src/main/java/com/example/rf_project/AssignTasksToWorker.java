package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssignTasksToWorker extends AppCompatActivity {


    TextView msgTv;
    Spinner workerSpinner;
    ArrayList<UserInfo> workers;
    ArrayList<Task> tasks;
    FirebaseDatabase fDb;
    ListViewAdapter listAdapter;
    ListView taskList;
    UserInfo selected;
    TextView hoursTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tasks_to_worker);
        msgTv = findViewById(R.id.messageTv);
        workerSpinner = findViewById(R.id.workers);
        workers = new ArrayList<>();
        tasks = new ArrayList<>();
        fDb = FirebaseDatabase.getInstance();
        taskList = findViewById(R.id.TaskList);
        selected = (UserInfo) workerSpinner.getSelectedItem();
        hoursTv = findViewById(R.id.hoursTv);

        DatabaseReference dbRef = fDb.getReference("Workers");
        DatabaseReference dbRef2 = fDb.getReference("Tasks");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String name = ds.child("FullName").getValue().toString();
                    String workfield = ds.child("WorkField").getValue().toString();
                    String hours = ds.child("Hours").getValue().toString();

                    UserInfo uf = new UserInfo(name,workfield,ds.getKey(),hours );

                    if(!workfield.equals("operator")) {
                        workers.add(uf);
                        populateSpinner();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        workerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        workers.clear();
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            String name = ds.child("FullName").getValue().toString();
                            String workfield = ds.child("WorkField").getValue().toString();
                            String hours = ds.child("Hours").getValue().toString();

                            UserInfo uf = new UserInfo(name,workfield,ds.getKey(),hours);
                            workers.add(uf);

                        }
                        hoursTv.setText(((UserInfo) workerSpinner.getSelectedItem()).getHours());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                selected = (UserInfo) workerSpinner.getSelectedItem();
                listAdapter = new ListViewAdapter(AssignTasksToWorker.this,tasks,((UserInfo) workerSpinner.getSelectedItem()).getId(),
                        ((UserInfo) workerSpinner.getSelectedItem()).getHours(), ((UserInfo) workerSpinner.getSelectedItem()).getName());
                taskList.setAdapter(listAdapter);
                dbRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        tasks.clear();
                        for (DataSnapshot ds : snapshot.getChildren())
                        {
                            String equipment = ds.child("Equipment").getValue().toString();
                            String duration = ds.child("Duration(h)").getValue().toString();
                            String requirement = ds.child("Requirement").getValue().toString();
                            String description = ds.child("Description").getValue().toString();
                            String scheduled = ds.child("Scheduled").getValue().toString();
                            String solved = ds.child("Solved").getValue().toString();

                            if(requirement.equals(selected.getWorkField()) && ds.child("Solved").getValue().toString().equals("false") && scheduled.equals("false")) {
                                Task newTask = new Task(description,duration,equipment,requirement, scheduled,solved, ds.getKey());
                                tasks.add(newTask);
                            }


                        }
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void populateSpinner(){
        ArrayAdapter<UserInfo> adapter = new ArrayAdapter<UserInfo>(this,android.R.layout.simple_spinner_item, workers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workerSpinner.setAdapter(adapter);
    }

    public void onAssignedClick(View v)
    {
        Intent i = new Intent(AssignTasksToWorker.this,AssignedTasks.class);
        startActivity(i);
    }

}