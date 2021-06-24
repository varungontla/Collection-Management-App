package Gsoft.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBill extends AppCompatActivity {

    TextView AB_HeadText;
    TextInputLayout AB_BillNo,AB_Amount;
    Button AB_Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        AB_HeadText = findViewById(R.id.AB_HeadText);
        AB_BillNo = findViewById(R.id.AB_BillNo);
        AB_Amount = findViewById(R.id.AB_Amount);
        AB_Add = findViewById(R.id.AB_Add);


        AB_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected(AddBill.this)){
                    InternetConnection.showCustomDailog(AddBill.this, AddBill.this);
                }else if (AB_BillNo.getEditText().toString().isEmpty()||AB_Amount.getEditText().toString().isEmpty()){
                             Toast.makeText(AddBill.this,"Enter all fields", Toast.LENGTH_SHORT).show();
                            } else addBill();
            }
        });

    }

    private void addBill() {

        String BillNo = AB_BillNo.getEditText().getText().toString().trim();
        Integer Amount = Integer.valueOf(AB_Amount.getEditText().getText().toString().trim());

        String ShopName = getIntent().getStringExtra("ShopName1");
        String GroupName = getIntent().getStringExtra("GroupName1");

        Integer DueAmount = Amount;

        ShopBillHelperClass helperClass = new ShopBillHelperClass(Amount,BillNo );

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("ShopBills").child(GroupName);
        databaseReference.child(ShopName).push().setValue(helperClass);

        ShopBillHelperClass helperClass1 = new ShopBillHelperClass(DueAmount);
        DatabaseReference dueDetails = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("BillDue").child(GroupName);
        dueDetails.child(ShopName).child(BillNo).setValue(helperClass1);

        AddBill.this.finish();


    }
}