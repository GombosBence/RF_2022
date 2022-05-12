package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTask extends AppCompatActivity {

    EditText taDescEt;
    EditText taEqEt;
    EditText taDurEt;
    EditText taRqEt;
    EditText taSchedEt;
    Button addTaskBtn;
    FirebaseDatabase fDatabase;
    TextView errorTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taDescEt = findViewById(R.id.taDescEt);
        taDurEt = findViewById(R.id.taDurEt);
        taEqEt = findViewById(R.id.taEqEt);
        taRqEt = findViewById(R.id.taRqEt);
        taSchedEt = findViewById(R.id.taSchedEt);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        errorTv = findViewById(R.id.errorTv);
        fDatabase = FirebaseDatabase.getInstance();

        addTaskBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(taDescEt.getText().toString().isEmpty() || taDurEt.getText().toString().isEmpty() || taRqEt.getText().toString().isEmpty()
                        || taEqEt.getText().toString().isEmpty() || taSchedEt.getText().toString().isEmpty())
                {
                    return;
                }
                DatabaseReference dbWfRef = fDatabase.getReference("WorkFields");
                dbWfRef.child(taRqEt.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            errorTv.setText("");
                            DatabaseReference dbRef = fDatabase.getReference("Tasks");
                            String uniqueID = UUID.randomUUID().toString();
                            Map<String, Object> taskInfo = new HashMap<>();
                            taskInfo.put("Description", taDescEt.getText().toString());
                            taskInfo.put("Duration(h)", taDurEt.getText().toString());
                            taskInfo.put("Equipment", taEqEt.getText().toString());
                            taskInfo.put("Requirement", taRqEt.getText().toString());
                            taskInfo.put("Scheduled", taSchedEt.getText().toString());
                            taskInfo.put("Solved", "false");

                            dbRef.child(uniqueID).setValue(taskInfo);


                        }
                        if(!snapshot.exists())
                        {
                            errorTv.setText("Workfield does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}