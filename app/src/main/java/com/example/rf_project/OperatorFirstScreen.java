package com.example.rf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OperatorFirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_first_screen);

    }

    public void onAddClick(View v)
    {
        Intent i = new Intent(OperatorFirstScreen.this,AddUser.class);
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

}