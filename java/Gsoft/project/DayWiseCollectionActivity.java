package Gsoft.project;

import android.os.Bundle;
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

public class DayWiseCollectionActivity extends AppCompatActivity {

    private DayWiseCollectionAdapter mListAdapter;

    TextView headText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_wise_collection);

        headText = findViewById(R.id.Day_headtext);

        if(!InternetConnection.isConnected(DayWiseCollectionActivity.this)){
            InternetConnection.showCustomDailog(DayWiseCollectionActivity.this, DayWiseCollectionActivity.this);
        }else
        getDate();


    }
    @SuppressWarnings("unchecked")
    private void getDate() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("DayWiseCollection");

        ListView LV_dayWise = findViewById(R.id.LV_dayWise);
        final List<String> helperclass = new ArrayList<>();

        mListAdapter = new DayWiseCollectionAdapter(this,R.layout.row_day,helperclass);
        LV_dayWise.setAdapter(mListAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String DateKey = snapshot.getKey();

                mListAdapter.add(DateKey);
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