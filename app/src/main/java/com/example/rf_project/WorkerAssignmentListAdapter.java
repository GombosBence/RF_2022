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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WorkerAssignmentListAdapter extends ArrayAdapter<AssignmentInfo> {

    ArrayList<AssignmentInfo> list;
    Context context;
    FirebaseDatabase fDatabase;

    public WorkerAssignmentListAdapter(Context context, ArrayList<AssignmentInfo> items){

        super(context, R.layout.list_row,items);
        this.context = context;
        list = items;
        fDatabase = FirebaseDatabase.getInstance();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.worker_list_row, null);

        TextView workerName = convertView.findViewById(R.id.workerNameTv);
        TextView equipmentName = convertView.findViewById(R.id.equipmentNameTv);
        TextView description = convertView.findViewById(R.id.descriptionTv);
        TextView assignmentState = convertView.findViewById(R.id.assignmentStateTv);
        Button denieBtn = convertView.findViewById(R.id.denieBtn);
        Button acceptBtn = convertView.findViewById(R.id.acceptBtn);
        Button markSolvedBtn = convertView.findViewById(R.id.markSolvedBtn);
        //TextView reason = convertView.findViewById(R.id.deniedReasonTv);

        workerName.setText("Worker's name: " + list.get(position).getWorkerName());
        equipmentName.setText("Equipment: " + list.get(position).getEquipmentName());
        description.setText("Description: " + list.get(position).getDescription());
        assignmentState.setText("Assignment State: " +  list.get(position).getAssignmentState());

        denieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRef = fDatabase.getReference("Assignments");
                dbRef.child(list.get(position).getAssignmentId()).child("Assignment State").setValue("denied");
            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRef = fDatabase.getReference("Assignments");
                dbRef.child(list.get(position).getAssignmentId()).child("Assignment State").setValue("accepted");

            }
        });
        markSolvedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRef = fDatabase.getReference("Assignments");
                dbRef.child(list.get(position).getAssignmentId()).child("Assignment State").setValue("solved");
            }
        });


        return  convertView;
    }

}