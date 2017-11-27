package com.bhawyyamittal.foodieclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private  EditText userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = findViewById(R.id.emailLogin);
        userPassword = findViewById(R.id.passLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void login(View view) {
        final String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        if((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.e("Error", "Signed In" + email);
                        checkUserInDatabase();
                    }

                }
            });
        }
    }

    private void checkUserInDatabase() {
        final String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userId)){
                    Log.e("Error","User is in database"+userId);
                   Intent menuIntent = new Intent(LoginActivity.this,MenuActivity.class);
                   startActivity(menuIntent);
                }
                else{
                    Log.e("Error","Can't find user in database User ID is :"+userId);



                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
