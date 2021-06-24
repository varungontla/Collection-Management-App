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

public class CollectionActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ListView Collection_List;
    private CollectionLVAdapter collectionLVAdapter;

    TextView tvCollectionHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);


        tvCollectionHead = findViewById(R.id.tvCollectionHead);


        Collection_List = (ListView) findViewById(R.id.Collection_List);

        String ShopName = getIntent().getStringExtra("ShopName");
        String BillNo = getIntent().getStringExtra("BillNo");
        String GroupName = getIntent().getStringExtra("GroupName");


        final List<ShopBillHelperClass> helperClasses = new ArrayList<>();

        collectionLVAdapter = new CollectionLVAdapter(this,R.layout.row_collection,helperClasses);
        Collection_List.setAdapter(collectionLVAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(FirebaseAuth.getInstance().getUid()).child("CollectionData").child("DueDetails").child(GroupName).child(ShopName).child(BillNo);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShopBillHelperClass shopBillHelperClass = snapshot.getValue(ShopBillHelperClass.class);

                collectionLVAdapter.add(shopBillHelperClass);
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