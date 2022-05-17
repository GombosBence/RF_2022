package com.example.rf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EditTask extends AppCompatActivity {

    TextView id, description, duration, equipment, scheduled;
    RadioButton deniedbtn, acceptedbtn, solvedbtn;
    Button updateBtn, cancelBtn;
    public ArrayList<String> arrayList2 = new ArrayList<>();
    public ArrayAdapter<String> arrayAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        id = findViewById(R.id.id);
        description = findViewById(R.id.description);
        duration = findViewById(R.id.duration);
        equipment = findViewById(R.id.equipment);
        scheduled = findViewById(R.id.scheduled);
        deniedbtn = findViewById(R.id.deniedbtn);
        acceptedbtn = findViewById(R.id.acceptedbtn);
        solvedbtn = findViewById(R.id.solvedbtn);
        updateBtn = findViewById(R.id.updateBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList2);
    }
}