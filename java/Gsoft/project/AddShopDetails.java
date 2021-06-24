package Gsoft.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShopDetails extends AppCompatActivity {

     private TextInputLayout etShopName,etAddress,Phone_No;


    String GroupName;

     DatabaseReference ShopReferencedb;

     FirebaseDatabase Root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etShopName =  findViewById(R.id.etShopName);
        etAddress =  findViewById(R.id.etAddress);
        Button btAdd = findViewById(R.id.btAdd);
        Phone_No = findViewById(R.id.Phone_No);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InternetConnection.isConnected(AddShopDetails.this)) {
                    InternetConnection.showCustomDailog(AddShopDetails.this, AddShopDetails.this);
                } else if (etShopName.getEditText().getText().toString().isEmpty() ||
                            etAddress.getEditText().getText().toString().isEmpty() ||
                            Phone_No.getEditText().getText().toString().isEmpty()) {
                        Toast.makeText(AddShopDetails.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    } else insertShop();
            }
        });

    }
    private void insertShop() {
        String ShopName = etShopName.getEditText().getText().toString().trim();
        String ShopAddress = etAddress.getEditText().getText().toString().trim();
        String PhoneNo = Phone_No.getEditText().getText().toString().trim();


        GroupName = getIntent().getStringExtra("GroupName1");


        ShopHelperClass shopHelperClass = new ShopHelperClass(ShopName,ShopAddress,PhoneNo,GroupName);
        final String uid = FirebaseAuth.getInstance().getUid();
        Root = FirebaseDatabase.getInstance();
        ShopReferencedb = Root.getReference(uid).child("ShopsData").child(GroupName);

        ShopReferencedb.push().setValue(shopHelperClass);
        AddShopDetails.this.finish();

    }
}