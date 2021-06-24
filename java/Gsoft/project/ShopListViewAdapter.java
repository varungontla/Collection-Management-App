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

public class ShopListViewAdapter extends ArrayAdapter<ShopHelperClass> {

    private String Key;
    private String ShopName;
    private String Address;
    private String GroupName;

    public ShopListViewAdapter(Context context, int resource, List<ShopHelperClass> shops){
        super(context,resource,shops);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_layout,parent,false);

        TextView RLShopName = (TextView) convertView.findViewById(R.id.RLShopName);


        final ShopHelperClass helperClass = getItem(position);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!InternetConnection.isConnected((Activity) getContext())){
                    InternetConnection.showCustomDailog(getContext(), (Activity) getContext());
                }else
                Key = helperClass.getShopKey();
                ShopName = helperClass.getShopName();
                Address = helperClass.getAddress();
                GroupName = helperClass.getGroupName();

                Intent intent = new Intent(getContext(), ShopBills.class);
                intent.putExtra("Key",Key);
                intent.putExtra("ShopName",ShopName);
                intent.putExtra("Address",Address);
                intent.putExtra("GroupName",GroupName);

                getContext().startActivity(intent);

            }
        });

        RLShopName.setText(helperClass.getShopName());

        return convertView;

    }
}
