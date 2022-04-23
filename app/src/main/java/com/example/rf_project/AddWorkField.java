package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddWorkField extends AppCompatActivity {

    EditText workfieldNameEt;
    Button addWorkfield;
    FirebaseDatabase fDatabase;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_field);

        workfieldNameEt = findViewById(R.id.workfieldName);
        addWorkfield = findViewById(R.id.addWorkfieldBt);
        fDatabase = FirebaseDatabase.getInstance();

        addWorkfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workfieldNameEt.getText().toString().isEmpty())
                {
                    return;
                }
                DatabaseReference dbRef = fDatabase.getReference("WorkFields");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        id = (int) snapshot.getChildrenCount() + 1;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Map<String, Object> workfieldInfo = new HashMap<>();
                workfieldInfo.put("workfield name", workfieldNameEt.getText().toString());

                String stringId = String.valueOf(id);
                dbRef.child(stringId).setValue(workfieldInfo);

            }
        });
    }
}