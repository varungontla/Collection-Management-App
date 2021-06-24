package Gsoft.project;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DateWiseAdapter extends ArrayAdapter<ShopBillHelperClass> {

    public DateWiseAdapter(Context context, int resource, List<ShopBillHelperClass> DateCollections){
        super(context,resource,DateCollections);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_date_wise,parent,false);

        TextView date_BillNo = convertView.findViewById(R.id.date_BillNo);
        TextView date_ShopName = convertView.findViewById(R.id.date_ShopName);
        TextView date_Amount = convertView.findViewById(R.id.date_Amount);
        TextView date_StacKNo = convertView.findViewById(R.id.date_StackNo);

        ShopBillHelperClass helperClass = getItem(position);

        Integer stackNo = position+1;

        date_StacKNo.setText(stackNo+"");
        date_ShopName.setText("Shopname : "+helperClass.getShopName());
        date_BillNo.setText("Bill no : "+helperClass.getBillNo());
        date_Amount.setText("Amount Recieved : "+helperClass.getCollectedAmount());


        return convertView;
    }
}
