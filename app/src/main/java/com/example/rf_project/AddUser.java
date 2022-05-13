package com.example.rf_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.DiskLruCache;

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
    TextView errorMsg;
    boolean exists;


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
        errorMsg = findViewById(R.id.errorMsg);

    }

    public void onClick(View v){

     /*   if (checkField(nameEt) && checkField(emailEt) && checkField(passwordEt) && checkField(workFieldEt) && exists)
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

*/

        DatabaseReference dbWfRef = fDatabase.getReference("WorkFields");
        dbWfRef.child(workFieldEt.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
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
                                        userInfo.put("Hours", "0");

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
                if(!snapshot.exists())
                {
                    errorMsg.setText("no such workfield in the database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
    };




