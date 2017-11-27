package com.bhawyyamittal.foodieclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {
    String food_key,food_Name,food_Desc,food_Price,food_Image;
    private TextView foodName, foodDesc, foodPrice;
    private DatabaseReference databaseReference, userRef, orderRef;
    private ImageView foodImage;
    private Button button;
    private FirebaseAuth auth;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        food_key = getIntent().getStringExtra("FoodId");
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
        foodName = findViewById(R.id.nameOrder);
        foodDesc =findViewById(R.id.descOrder);
        foodPrice = findViewById(R.id.priceOrder);
        foodImage = findViewById(R.id.imageOrder);
        current_user = auth.getCurrentUser();
        String uid_curruser =auth.getCurrentUser().getUid();
        Log.e("UID", "The UID of the current user is "+ uid_curruser);
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid_curruser);
         orderRef = FirebaseDatabase.getInstance().getReference().child("orders");
        databaseReference.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           food_Name = (String) dataSnapshot.child("name").getValue();
                 food_Desc = (String) dataSnapshot.child("description").getValue();
                food_Price = (String) dataSnapshot.child("price").getValue();
                 food_Image = (String) dataSnapshot.child("image").getValue();
                foodName.setText(food_Name);
                foodDesc.setText(food_Desc);
                foodPrice.setText(food_Price);
                Picasso.with(SingleFoodActivity.this).load(food_Image).into(foodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void orderFood(View view){
        final DatabaseReference newOrder = orderRef.push();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newOrder.child("itemname").setValue(food_Name);
                newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(SingleFoodActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }
                });
                newOrder.child("itemimage").setValue(food_Image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
