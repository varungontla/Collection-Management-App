package Gsoft.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShopDetails extends AppCompatActivity
{

    private ShopListViewAdapter mListAdapter;

   private String GroupName;

   TextView tvHeadtext;;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        if(!InternetConnection.isConnected(ShopDetails.this)){
            InternetConnection.showCustomDailog(ShopDetails.this, ShopDetails.this);
        }
        tvHeadtext = findViewById(R.id.tvHeadText);
        FloatingActionButton fabEdit =  findViewById(R.id.fab_Add_Shop);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.isConnected(ShopDetails.this)){
                    InternetConnection.showCustomDailog(ShopDetails.this, ShopDetails.this);
                }else{
                Intent intent = new Intent(ShopDetails.this, AddShopDetails.class);
                intent.putExtra("GroupName1",GroupName);
                startActivity(intent);
                }
            }
        });

        if(!InternetConnection.isConnected(ShopDetails.this)){
            InternetConnection.showCustomDailog(ShopDetails.this, ShopDetails.this);
        }
        GetShopList();

    }

    private void GetShopList() {
        GroupName = getIntent().getStringExtra("GroupName");
        tvHeadtext.setText(GroupName);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("ShopsData").child(GroupName);


        ListView list_View = (ListView) findViewById(R.id.List_View);

        final List<Gsoft.project.ShopHelperClass> helperClasses = new ArrayList<>();

        mListAdapter = new ShopListViewAdapter(this,R.layout.row_layout,helperClasses);
        list_View.setAdapter(mListAdapter);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ShopHelperClass shopHelperClass = snapshot.getValue(ShopHelperClass.class);

                String KeyValue = String.valueOf(snapshot.getKey());
                shopHelperClass.setShopKey(KeyValue);
                mListAdapter.add(shopHelperClass);
                mListAdapter.notifyDataSetChanged();
                Toast.makeText(ShopDetails.this, "List updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}