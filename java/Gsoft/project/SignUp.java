package Gsoft.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout Name,Email,PhoneNo;
    Button Register;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        PhoneNo = findViewById(R.id.ContactNo);
        Register = findViewById(R.id.Register);


        userSignedIn();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.isConnected(SignUp.this)){
                    InternetConnection.showCustomDailog(SignUp.this, SignUp.this);
                }else
                RegisterButtonClicked();
            }
        });


    }

    private void RegisterButtonClicked() {

        if (Name.getEditText().getText().toString().isEmpty()||
                Email.getEditText().getText().toString().isEmpty()||
                PhoneNo.getEditText().getText().toString().isEmpty()){
            Toast.makeText(SignUp.this,"Enter all the fields", Toast.LENGTH_SHORT).show();
        }
        else {
            String Sname = Name.getEditText().getText().toString().trim();
            String Semail = Email.getEditText().getText().toString().trim();
            String SphoneNo = PhoneNo.getEditText().getText().toString().trim();

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference().child("UsersProfile");

            UserHelperClass helperClass = new UserHelperClass(Sname, Semail, SphoneNo);

            reference.child(SphoneNo).setValue(helperClass);

            Intent intent = new Intent(SignUp.this,PhoneNoVerify.class);
            intent.putExtra("PhoneNo",SphoneNo);
            startActivity(intent);
            SignUp.this.finish();
        }
    }

    private void userSignedIn() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
            finish();
        }
    }
}