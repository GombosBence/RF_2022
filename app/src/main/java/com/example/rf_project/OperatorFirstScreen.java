package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class OperatorFirstScreen extends AppCompatActivity {

    FirebaseDatabase fDb;
    String currentDate;
    String nextMaintance;
    Date cDate;
    Date nMainDate;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_first_screen);

        fDb = FirebaseDatabase.getInstance();

    }

    public void onAddClick(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,AddUser.class);
        startActivity(i);
    }

    public void onLogOut(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,Login.class);
        startActivity(i);
    }

    public void onAddEqClick(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,AddEquipment.class);
        startActivity(i);
    }

    public void onAddWfClick(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,AddWorkField.class);
        startActivity(i);
    }

    public void onAddTaClick(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,AddTask.class);
        startActivity(i);
    }

    public void automaticTasks(View v)
    {
        DatabaseReference dbRef = fDb.getReference("Equipments");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                    for(DataSnapshot ds : snapshot.getChildren())
                    {
                        currentDate = getDate();
                        nextMaintance = ds.child("Next Maintance").getValue().toString();

                        try {
                            cDate = format.parse(currentDate);
                            nMainDate = format.parse(nextMaintance);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(cDate.after(nMainDate) || cDate.compareTo(nMainDate) == 0)
                        {
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(Calendar.DATE, Integer.parseInt(ds.child("Maintance Period(d)").getValue().toString()));
                            dbRef.child(ds.getKey()).child("Next Maintance").setValue(format.format(c.getTime()));



                            DatabaseReference dbTaskRef = fDb.getReference("Tasks");
                            String uniqueID = UUID.randomUUID().toString();
                            Map<String, Object> taskInfo = new HashMap<>();
                            taskInfo.put("Equipment", ds.getKey());
                            taskInfo.put("Duration(h)", ds.child("Normal Time(h)").getValue());
                            taskInfo.put("Description", "Maintance");
                            taskInfo.put("Requirement", ds.child("Equipment Requirement").getValue());
                            taskInfo.put("Scheduled", "false");
                            taskInfo.put("Solved", "false");

                            dbTaskRef.child(uniqueID).setValue(taskInfo);

                        }

                    }
                }
            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public String getDate()
    {
        return new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(new Date());
    }
}