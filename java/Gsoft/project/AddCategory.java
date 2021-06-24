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

public class AddCategory extends AppCompatActivity {

    private TextInputLayout Group_Name,No_Of_Bills;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Group_Name = findViewById(R.id.Group_Name);
        No_Of_Bills = findViewById(R.id.No_Of_Bills);
        Button category_add = findViewById(R.id.Category_add);

        category_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected(AddCategory.this)){
                    InternetConnection.showCustomDailog(AddCategory.this, AddCategory.this);
                }else
                    addCategory();
            }
        });
    }

    private void addCategory() {

        if (Group_Name.getEditText().toString().isEmpty()||No_Of_Bills.getEditText().toString().isEmpty()){

            Toast.makeText(AddCategory.this,"Enter all field", Toast.LENGTH_SHORT).show();
        }
        else{

        String group_name = Group_Name.getEditText().getText().toString().trim();
        String noofbills = No_Of_Bills.getEditText().getText().toString().trim();

        UserHelperClass helperClass = new UserHelperClass(group_name,noofbills);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(FirebaseAuth.getInstance().getUid()).child("UserCategories");

        databaseReference.push().setValue(helperClass);
        AddCategory.this.finish();

    }
}
}