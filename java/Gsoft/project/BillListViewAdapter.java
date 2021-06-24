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

public class BillListViewAdapter extends ArrayAdapter<ShopBillHelperClass> {

    public BillListViewAdapter(Context context, int resource, List<ShopBillHelperClass> Bills){
        super(context,resource,Bills);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_bill_layout,parent,false);

        TextView BL_BillNO = (TextView) convertView.findViewById(R.id.BL_BillNo);

        final ShopBillHelperClass helperClass = getItem(position);

        Integer BillStack = position+1;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!InternetConnection.isConnected((Activity) getContext())) {
                    InternetConnection.showCustomDailog(getContext(), (Activity) getContext());
                } else {
                    final String BillKey = helperClass.getBillKey();
                    final String ShopName = helperClass.getShopName();
                    final String BillNo = helperClass.getBillNo();
                    final String GroupName = helperClass.getGroupName();

                    Intent intent = new Intent(getContext(), Gsoft.project.BillEdit.class);
                    intent.putExtra("BillKey", BillKey);
                    intent.putExtra("ShopName", ShopName);
                    intent.putExtra("BillNo", BillNo);
                    intent.putExtra("GroupName2", GroupName);
                    getContext().startActivity(intent);
                }
            }
        });

        BL_BillNO.setText(BillStack+". "+helperClass.getBillNo());

        return convertView;
    }
}
