package com.bhawyyamittal.foodieclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText email, pass;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emailEd);
        pass = findViewById(R.id.password);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        auth = FirebaseAuth.getInstance();
    }
    public void signUp(View view){

        final String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();
        Toast.makeText(this,"I am invoked"+email_text+ " "+pass_text,Toast.LENGTH_LONG).show();
        if((!TextUtils.isEmpty(email_text)) && (!TextUtils.isEmpty(pass_text))){
            auth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(MainActivity.this,"User creation successful",Toast.LENGTH_LONG).show();
                    if(task.isSuccessful()) {
                        //FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        //Toast.makeText(MainActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       // message.hide();
                        //Log.e("Error","Error : " + e.getMessage());

                        String user_id = auth.getCurrentUser().getUid();
                        DatabaseReference currentUser = databaseReference.child(user_id);
                        currentUser.child("Name").setValue(email_text);
                        Intent login = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(login);
                    }


                }
            });
        }

    }

    public void signIn(View view) {
        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}
