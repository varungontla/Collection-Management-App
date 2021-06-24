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

public class ShopBills extends AppCompatActivity {

   private TextView SB_ShopName,SB_ShopAdress,tvHeadText;
   private FloatingActionButton fab_AddBills;
   private ListView SB_List_View;

    private BillListViewAdapter billListViewAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_bills);

        SB_ShopName = findViewById(R.id.SB_ShopName);
        SB_ShopAdress = findViewById(R.id.SB_ShopAdress);
        tvHeadText = findViewById(R.id.tvHeadText);
        fab_AddBills = findViewById(R.id.fab_AddBills);
        SB_List_View = findViewById(R.id.SB_List_View);

        String Key = getIntent().getStringExtra("Key");
        final String ShopName = getIntent().getStringExtra("ShopName");
        String Address = getIntent().getStringExtra("Address");
        final String GroupName = getIntent().getStringExtra("GroupName");


        SB_ShopName.setText(ShopName);
        SB_ShopAdress.setText(Address);


        fab_AddBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.isConnected(ShopBills.this)){
                    InternetConnection.showCustomDailog(ShopBills.this, ShopBills.this);
                }else {
                    Intent intent = new Intent(ShopBills.this, AddBill.class);
                    intent.putExtra("ShopName1", ShopName);
                    intent.putExtra("GroupName1", GroupName);
                    startActivity(intent);
                }
            }
        });
        if(!InternetConnection.isConnected(ShopBills.this)){
            InternetConnection.showCustomDailog(ShopBills.this, ShopBills.this);
        }else GetBills(ShopName, GroupName);

    }

    private void GetBills(final String shopName, final String groupName) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("ShopBills").child(groupName).child(shopName);

        SB_List_View = findViewById(R.id.SB_List_View);

        final List<ShopBillHelperClass> helperClassList = new ArrayList<>();

        billListViewAdapter = new BillListViewAdapter(this,R.layout.row_bill_layout,helperClassList);
        SB_List_View.setAdapter(billListViewAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ShopBillHelperClass helperClass = snapshot.getValue(ShopBillHelperClass.class);

                String BillKey = String.valueOf(snapshot.getKey());
                helperClass.setBillKey(BillKey);
                helperClass.setShopName(shopName);
                helperClass.setGroupName(groupName);
                billListViewAdapter.add(helperClass);
                billListViewAdapter.notifyDataSetChanged();
                Toast.makeText(ShopBills.this,"Bills updated", Toast.LENGTH_SHORT).show();

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