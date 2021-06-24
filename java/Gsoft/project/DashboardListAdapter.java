package Gsoft.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DashboardListAdapter extends ArrayAdapter<UserHelperClass> {

    String GroupKey,GroupName;

    public DashboardListAdapter(Context context, int resource, List<UserHelperClass> Categories){
        super(context,resource,Categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_dashboard_list,parent,false);

        TextView tv_GroupName = (TextView) convertView.findViewById(R.id.tv_GroupName);
        TextView tv_NoOfBills = (TextView) convertView.findViewById(R.id.tv_NoOfBills);


        final UserHelperClass helperClass = getItem(position);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected((Activity) getContext())){
                    InternetConnection.showCustomDailog(getContext(), (Activity) getContext());
                }else{
                GroupKey = helperClass.getCategoryKey();
                GroupName = helperClass.getGroupName();

                Intent intent = new Intent(getContext(), ShopDetails.class);
                intent.putExtra("GroupKey",GroupKey);
                intent.putExtra("GroupName",GroupName);
                getContext().startActivity(intent);
            }}
        });

        tv_GroupName.setText(helperClass.getGroupName());
        tv_NoOfBills.setText(helperClass.getNoOfBills());

        return convertView;
    }
}
