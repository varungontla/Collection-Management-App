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


public class DayWiseCollectionAdapter extends ArrayAdapter<String> {

    List<String> getCollectionDate;

    public DayWiseCollectionAdapter(Context context, int resouce, List<String> collection){
        super(context,resouce,collection);
        getCollectionDate = collection;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.row_day,parent,false);

        TextView day_Date = convertView.findViewById(R.id.day_Date);

       final String date= getCollectionDate.get(position);

       Integer stackNo = position+1;

        day_Date.setText(stackNo+". "+date);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InternetConnection.isConnected((Activity) getContext())){
                    InternetConnection.showCustomDailog(getContext(), (Activity) getContext());
                }else {
                    Intent intent = new Intent(getContext(), DateWiseActivity.class);
                    intent.putExtra("Date", date);
                    getContext().startActivity(intent);
                }
            }
        });

        return convertView;
    }
}
