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

public class AddEquipment extends AppCompatActivity {

    EditText eqNameEt;
    EditText normalTimeEt;
    EditText maintanceEt;
    EditText instructionEt;
    EditText eqReqEt;
    Button addEquipment;
    FirebaseDatabase fDatabase;
    TextView errorMsgEt;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);

        eqNameEt = findViewById(R.id.eqNameEt);
        eqReqEt = findViewById(R.id.eqReqEt);
        normalTimeEt = findViewById(R.id.normalTimeEt);
        maintanceEt = findViewById(R.id.maintanceEt);
        instructionEt = findViewById(R.id.instructionEt);
        addEquipment = findViewById(R.id.addEquipmentBt);
        fDatabase = FirebaseDatabase.getInstance();
        errorMsgEt = findViewById(R.id.errorEt);

        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eqNameEt.getText().toString().isEmpty() || eqReqEt.getText().toString().isEmpty() || normalTimeEt.getText().toString().isEmpty()
                || maintanceEt.getText().toString().isEmpty() || instructionEt.getText().toString().isEmpty())
                {
                    return;
                }
                DatabaseReference dbWfRef = fDatabase.getReference("WorkFields");
                dbWfRef.child(eqReqEt.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                            errorMsgEt.setText("");
                            DatabaseReference dbRef = fDatabase.getReference("Equipments");
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(Calendar.DATE, Integer.parseInt(maintanceEt.getText().toString()));

                            Map<String, Object> equipmentInfo = new HashMap<>();
                            equipmentInfo.put("Equipment Requirement", eqReqEt.getText().toString());
                            equipmentInfo.put("Normal Time(h)", normalTimeEt.getText().toString());
                            equipmentInfo.put("Maintance Period(d)", maintanceEt.getText().toString());
                            equipmentInfo.put("Instruction", instructionEt.getText().toString());
                            equipmentInfo.put("Next Maintance", format.format(c.getTime()));

                            dbRef.child(eqNameEt.getText().toString()).setValue(equipmentInfo);


                        }
                        if(!snapshot.exists())
                        {
                            errorMsgEt.setText("No such workfield in the database");
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