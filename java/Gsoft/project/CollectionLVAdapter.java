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

public class CollectionLVAdapter extends ArrayAdapter<ShopBillHelperClass> {


public CollectionLVAdapter(Context context, int resource, List<ShopBillHelperClass> Bills){
        super(context,resource,Bills);
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_collection,parent,false);

    TextView RLAmount = convertView.findViewById(R.id.RLAmount);
    TextView RLDueAmount = convertView.findViewById(R.id.RLDueAmount);
    TextView RLDate = convertView.findViewById(R.id.RLDate);

        ShopBillHelperClass helperClass = getItem(position);

        RLAmount.setText("Amount recieved: "+helperClass.getCollectedAmount());
        RLDate.setText("Recieved date: "+helperClass.getCollectionDate());
        RLDueAmount.setText("DueAmount: "+helperClass.getDueAmount());

        return convertView;

        }
}