package Gsoft.project;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BillEdit extends AppCompatActivity {

    TextView bill_ShopName,tvBillNo,tvDueAmount,bill_BillNo;
    TextView bill_amount,bill_DueAmount;
    Button bill_MarkCollection,bill_ViewCollection,bill_Done,bill_Mark;
    TextInputLayout bill_CollectedAmount;

    String BillNo;

    Integer amount;
    Integer dueamount;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,CollectionReference,DateWiseCollectionReference,BillDueReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_single_bill_no);

        tvBillNo = findViewById(R.id.tv_billNo);
        bill_BillNo = findViewById(R.id.bill_BillNo);
        bill_ShopName = findViewById(R.id.bill_ShopName);
        tvDueAmount = findViewById(R.id.tvDueAmount);
        bill_amount = findViewById(R.id.bill_amount);
        bill_DueAmount = findViewById(R.id.bill_DueAmount);
        bill_MarkCollection = findViewById(R.id.bill_MarkCollection);
        bill_ViewCollection = findViewById(R.id.bill_ViewCollection);
        bill_CollectedAmount = findViewById(R.id.bill_CollectedAmount);
        bill_Done = findViewById(R.id.bill_Done);
        bill_Mark = findViewById(R.id.bill_Mark);

        bill_CollectedAmount.setVisibility(View.GONE);
        bill_Mark.setVisibility(View.GONE);

        if(!InternetConnection.isConnected(Gsoft.project.BillEdit.this)){
            InternetConnection.showCustomDailog(BillEdit.this, BillEdit.this);
        }else GetBillDetails();

    }

    private void GetBillDetails() {
        final String BillKey = getIntent().getStringExtra("BillKey");
        final String ShopName = getIntent().getStringExtra("ShopName");
        final String billNo = getIntent().getStringExtra("BillNo");
        final String GroupName = getIntent().getStringExtra("GroupName2");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("ShopBills").child(GroupName).child(ShopName).child(BillKey);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ShopBillHelperClass helperClass = snapshot.getValue(ShopBillHelperClass.class);

                BillNo = helperClass.getBillNo();
                Integer Amount = helperClass.getAmount();

                amount = helperClass.getAmount();

                helperClass.setShopName(ShopName);

                String ShopName = helperClass.getShopName();

                bill_ShopName.setText(ShopName);
                bill_BillNo.setText(BillNo+"");
                bill_amount.setText(Amount+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseReference.addListenerForSingleValueEvent(eventListener);

        BillDueReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("BillDue").child(GroupName).child(ShopName).child(billNo);

        ValueEventListener dueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ShopBillHelperClass helperClass = snapshot.getValue(ShopBillHelperClass.class);

                dueamount = helperClass.getDueAmount() ;
                bill_DueAmount.setText(dueamount+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(BillEdit.this,"Error: "+error, Toast.LENGTH_SHORT).show();
            }
        };

        BillDueReference.addListenerForSingleValueEvent(dueEventListener);

        bill_MarkCollection.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!InternetConnection.isConnected(BillEdit.this)){
                     InternetConnection.showCustomDailog(BillEdit.this, BillEdit.this);
                 }else
                 bill_CollectedAmount.setVisibility(View.VISIBLE);
                 bill_Mark.setVisibility(View.VISIBLE);
             }
         });

        bill_Mark.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected(BillEdit.this)){
                    InternetConnection.showCustomDailog(BillEdit.this, BillEdit.this);
                }else if (bill_CollectedAmount.getEditText().toString().isEmpty()){
                    Toast.makeText(BillEdit.this,"Enter the amount", Toast.LENGTH_SHORT).show();
                }
                else {

                    Integer CollectionAmount = Integer.valueOf(bill_CollectedAmount.getEditText().getText().toString().trim());
                    dueamount = dueamount-CollectionAmount;
                    dueamountChanged(dueamount);
                    bill_DueAmount.setText(dueamount+"");

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);

                    String date = formattedDate+"";

                    ShopBillHelperClass helperClass = new ShopBillHelperClass(dueamount,CollectionAmount,date);
                    CollectionReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("DueDetails").child(GroupName).child(ShopName).child(BillNo);
                    CollectionReference.push().setValue(helperClass);

                    bill_DueAmount.setText(dueamount+"");

                    //saving daywise collection to database
                    ShopBillHelperClass  helperClass1 = new ShopBillHelperClass(ShopName,BillNo,CollectionAmount,date);
                    DateWiseCollectionReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("DayWiseCollection").child(date);
                    DateWiseCollectionReference.push().setValue(helperClass1);
                    makeButtonGone();
                }
            }
        });

        bill_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillEdit.this.finish();
            }
        });

        bill_ViewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.isConnected(BillEdit.this)){
                    InternetConnection.showCustomDailog(BillEdit.this, BillEdit.this);
                }else {
                    Intent intent = new Intent(BillEdit.this, CollectionActivity.class);
                    intent.putExtra("ShopName", ShopName);
                    intent.putExtra("BillNo", BillNo);
                    intent.putExtra("Amount", amount);
                    intent.putExtra("GroupName", GroupName);
                    startActivity(intent);
                }
            }
        });
    }

    private void makeButtonGone() {

        bill_CollectedAmount.setVisibility(View.GONE);
        bill_Mark.setVisibility(View.GONE);

    }

    private void dueamountChanged(Integer dueamount) {

        BillDueReference.child("dueAmount").setValue(dueamount);

    }
}