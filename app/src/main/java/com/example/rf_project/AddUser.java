package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity {

    EditText nameEt;
    EditText emailEt;
    EditText passwordEt;
    EditText workFieldEt;
    RadioButton adminRbtn;
    Button addUser;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseDatabase fDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        nameEt = findViewById(R.id.NameEt);
        emailEt = findViewById(R.id.EmailEt);
        passwordEt = findViewById(R.id.PasswordEt);
        workFieldEt = findViewById(R.id.workField);
        adminRbtn = findViewById(R.id.radioButton);
        addUser = findViewById(R.id.addBtn);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkField(nameEt) && checkField(emailEt) && checkField(passwordEt) && checkField(workFieldEt))
                {
                    fAuth.createUserWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    FirebaseUser user = fAuth.getCurrentUser();
                                    Toast.makeText(AddUser.this, "Account created", Toast.LENGTH_SHORT).show();
                                    DatabaseReference dbRef = fDatabase.getReference("Workers");

                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("FullName", nameEt.getText().toString());
                                    userInfo.put("Email", emailEt.getText().toString());
                                    userInfo.put("WorkField", workFieldEt.getText().toString());

                                    if (adminRbtn.isChecked()) {
                                        userInfo.put("Admin", "1");
                                    }else
                                    {
                                        userInfo.put("Admin", "0");
                                    }

                                    dbRef.child(user.getUid()).setValue(userInfo);

                                }
                            });
                }


            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty())
        {
            return false;
        }
        return true;
    }
}