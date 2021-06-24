package Gsoft.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference databaseReference,navReference;
    private FirebaseDatabase database1,database2;
    FloatingActionButton fab_add_category;

    private ListView dashboard_List;
    private Gsoft.project.DashboardListAdapter mListAdapter;

    TextView Dashboard_Title, Dashboard_discription;

    MenuItem dayWiseCollection;

    //DrawerMenu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Dashboard_Title = findViewById(R.id.Dashboard_Title);
        Dashboard_discription = findViewById(R.id.Dashboard_discription);
        fab_add_category = findViewById(R.id.fab_add_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        dayWiseCollection = findViewById(R.id.day_wise_collection);

       // navigationDrawer();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fab_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected(Gsoft.project.UserDashboard.this)){
                    InternetConnection.showCustomDailog(Gsoft.project.UserDashboard.this, Gsoft.project.UserDashboard.this);
                }else{
                startActivity(new Intent(Gsoft.project.UserDashboard.this, AddCategory.class));}
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        if(!InternetConnection.isConnected(Gsoft.project.UserDashboard.this)){
            InternetConnection.showCustomDailog(Gsoft.project.UserDashboard.this, Gsoft.project.UserDashboard.this);
        }else{

            gettingCategories(); }
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
        }
    }

    private void gettingCategories() {
        database1 = FirebaseDatabase.getInstance();
        databaseReference = database1.getReference().child(FirebaseAuth.getInstance().getUid()).child("UserCategories");

        dashboard_List = findViewById(R.id.dashboard_List);
        final List<UserHelperClass> helperClass = new ArrayList<>();

        mListAdapter = new Gsoft.project.DashboardListAdapter(this, R.layout.row_dashboard_list, helperClass);
        dashboard_List.setAdapter(mListAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                UserHelperClass helperClass1 = snapshot.getValue(UserHelperClass.class);

                String KeyCategory = String.valueOf(snapshot.getKey());
                helperClass1.setCategoryKey(KeyCategory);
                mListAdapter.add(helperClass1);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(!InternetConnection.isConnected(Gsoft.project.UserDashboard.this)){
            InternetConnection.showCustomDailog(Gsoft.project.UserDashboard.this, Gsoft.project.UserDashboard.this);
        }else

        switch (item.getItemId()){

            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.day_wise_collection:
                Intent intent = new Intent(this,DayWiseCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(Gsoft.project.UserDashboard.this,SignUp.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;
        }

        return true;
    }


}