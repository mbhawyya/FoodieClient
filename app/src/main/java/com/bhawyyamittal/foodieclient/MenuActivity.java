package com.bhawyyamittal.foodieclient;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        recyclerView = findViewById(R.id.recyclerFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
        FirebaseRecyclerAdapter<FoodItem,FoodViewHolder> adapter = new FirebaseRecyclerAdapter<FoodItem, FoodViewHolder>(
                FoodItem.class,
                R.layout.card_row,
                FoodViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, FoodItem model, int position) {
                viewHolder.setName(model.getName());
               // viewHolder.setDesc(model.getDesc());
                 TextView txt = viewHolder.view.findViewById(R.id.foodDesc);
                 txt.setText(model.getDesc());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                final String food_key = getRef(position).getKey();
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent((MenuActivity.this),SingleFoodActivity.class);
                        intent.putExtra("FoodId",food_key);
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }
    public  static class FoodViewHolder extends RecyclerView.ViewHolder{
        View view;
        public FoodViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public  void setName(String name){
            TextView txtName = view.findViewById(R.id.foodName);
            txtName.setText(name);

        }
        public  void setDesc(String desc){
            TextView txtDesc = view.findViewById(R.id.foodDesc);
            txtDesc.setText(desc);
            Log.e("Error","Desc is"+desc);
        }
        public  void setPrice(String price){
            TextView txtPrice = view.findViewById(R.id.foodPrice);
            txtPrice.setText(price);

        }
        public  void setImage(Context ctx, String image){
            ImageView imageView = view.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(imageView);

        }
    }
}
