package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ListViewAdapter extends ArrayAdapter<Task> {
    ArrayList<Task> list;
    Context context;
    FirebaseDatabase fDatabase;
    String selectedUserId;
    String selectedUserHours;
    String selectedUserName;

    public ListViewAdapter(Context context, ArrayList<Task> items, String selectedid, String hours, String name){


        super(context, R.layout.list_row,items);
        this.context = context;
        list = items;
        fDatabase = FirebaseDatabase.getInstance();
        selectedUserId = selectedid;
        selectedUserHours = hours;
        selectedUserName = name;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.list_row, null);

        TextView equipmentName = convertView.findViewById(R.id.equipmentTv);
        TextView duration = convertView.findViewById(R.id.durationTv);
        TextView requirement = convertView.findViewById(R.id.requirementTv);
        TextView description = convertView.findViewById(R.id.descriptionTv);
        Button assign = convertView.findViewById(R.id.button);
        String id;

        equipmentName.setText("Equipment: " + list.get(position).getEquipment());
        duration.setText("Duration: " +list.get(position).getDuration());
        requirement.setText("Requirement: " + list.get(position).getRequirement());
        description.setText("Description: " + list.get(position).getDescription());
        id = list.get(position).getId();



        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(selectedUserHours) + Integer.parseInt(list.get(position).getDuration()) > 8)
                {
                    Toast.makeText(context.getApplicationContext(), "This action would exceed the 8 hour limit", Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference dbRef = fDatabase.getReference("Assignments");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        DatabaseReference dbref2 = fDatabase.getReference("Tasks");
                        dbref2.child(id).child("Scheduled").setValue("true");

                        DatabaseReference dbref3 = fDatabase.getReference("Workers");
                        int selectedH = Integer.parseInt(selectedUserHours);
                        int taskDur = Integer.parseInt(list.get(position).getDuration());
                        int newHours = selectedH + taskDur;
                        selectedUserHours=String.valueOf(newHours);
                        dbref3.child(selectedUserId).child("Hours").setValue(String.valueOf(newHours));


                        String uniqueID = UUID.randomUUID().toString();
                        Map<String, Object> assignmentInfo = new HashMap<>();
                        assignmentInfo.put("Worker_ID", selectedUserId);
                        assignmentInfo.put("Task_ID", id);
                        assignmentInfo.put("Assignment State", "pending");
                        assignmentInfo.put("Worker's name", selectedUserName);
                        assignmentInfo.put("Description", list.get(position).getDescription());
                        assignmentInfo.put("Equipment", list.get(position).getEquipment());
                        assignmentInfo.put("Duration", list.get(position).getDuration());

                        dbRef.child(uniqueID).setValue(assignmentInfo);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        return convertView;
    }
}