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

import java.util.HashMap;
import java.util.Map;

public class AddEquipment extends AppCompatActivity {

    EditText eqNameEt;
    EditText eqReqEt;
    Button addEquipment;
    FirebaseDatabase fDatabase;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        eqNameEt = findViewById(R.id.eqNameEt);
        eqReqEt = findViewById(R.id.eqReqEt);
        addEquipment = findViewById(R.id.addEquipmentBt);
        fDatabase = FirebaseDatabase.getInstance();

        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eqNameEt.getText().toString().isEmpty() || eqReqEt.getText().toString().isEmpty())
                {
                    return;
                }


                DatabaseReference dbRef = fDatabase.getReference("Equipments");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        id = (int) snapshot.getChildrenCount() + 1;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Map<String, Object> equipmentInfo = new HashMap<>();
                equipmentInfo.put("Equipment Name", eqNameEt.getText().toString());
                equipmentInfo.put("Equipment Requirement", eqReqEt.getText().toString());
                String stringId = String.valueOf(id);

                dbRef.child(stringId).setValue(equipmentInfo);

            }
        });
    }
}