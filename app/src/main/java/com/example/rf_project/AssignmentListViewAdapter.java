package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssignmentListViewAdapter extends ArrayAdapter<AssignmentInfo> {

    ArrayList<AssignmentInfo> list;
    Context context;
    FirebaseDatabase fDatabase;
    int newDur;
    int currDur;
    int dur;

    public AssignmentListViewAdapter(Context context, ArrayList<AssignmentInfo> items){

        super(context, R.layout.list_row,items);
        this.context = context;
        list = items;
        fDatabase = FirebaseDatabase.getInstance();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.assignment_list_row, null);

        TextView workerName = convertView.findViewById(R.id.workerNameTv);
        TextView equipmentName = convertView.findViewById(R.id.equipmentNameTv);
        TextView description = convertView.findViewById(R.id.descriptionTv);
        TextView assignmentState = convertView.findViewById(R.id.assignmentStateTv);
        Button deleteBtn = convertView.findViewById(R.id.deleteBtn);
        //TextView reason = convertView.findViewById(R.id.deniedReasonTv);

        workerName.setText("Worker's name: " + list.get(position).getWorkerName());
        equipmentName.setText("Equipment: " + list.get(position).getEquipmentName());
        description.setText("Description: " + list.get(position).getDescription());
        assignmentState.setText("Assignment State: " +  list.get(position).getAssignmentState());
        //reason.setText();

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseReference dbRef2 = fDatabase.getReference("Workers");
                dbRef2.child(list.get(position).getWorkerId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                         dur = Integer.parseInt(list.get(position).getDuration());
                         currDur = Integer.parseInt(snapshot.child("Hours").getValue().toString());
                         newDur = currDur - dur;

                        DatabaseReference dbRef = fDatabase.getReference("Assignments");
                        dbRef.child(list.get(position).getAssignmentId()).removeValue();

                        DatabaseReference dbRef3 = fDatabase.getReference("Tasks");
                        dbRef3.child(list.get(position).getTaskId()).child("Scheduled").setValue("false");
                        if(list.get(position).getAssignmentState().equals("solved"))
                        {
                            dbRef3.child(list.get(position).getTaskId()).removeValue();
                        }


                        dbRef2.child(list.get(position).getWorkerId()).child("Hours").setValue(String.valueOf(newDur));
                        list.remove(position);
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