package Gsoft.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DateWiseActivity extends AppCompatActivity {

    TextView Date_headtext;
    private DateWiseAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_wise);

        String date = getIntent().getStringExtra("Date");

        Date_headtext = findViewById(R.id.Date_headtext);

        Date_headtext.setText("Collection date : "+date);
        if(!InternetConnection.isConnected(DateWiseActivity.this)){
            InternetConnection.showCustomDailog(DateWiseActivity.this, DateWiseActivity.this);
        }else
        getData(date);

    }

    private void getData(String date) {
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("DayWiseCollection").child(date);

        ListView LV_DateWise = findViewById(R.id.LV_dateWise);
        final List<ShopBillHelperClass> helperClasses = new ArrayList<>();

        mListAdapter = new DateWiseAdapter(this,R.layout.row_date_wise,helperClasses);
        LV_DateWise.setAdapter(mListAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ShopBillHelperClass helperClass = snapshot.getValue(ShopBillHelperClass.class);

                mListAdapter.add(helperClass);
                mListAdapter.notifyDataSetChanged();

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