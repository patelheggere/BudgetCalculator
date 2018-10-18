package com.example.admin.budgetcalculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.budgetcalculator.R;
import com.example.admin.budgetcalculator.activity.BudgetActivity;
import com.example.admin.budgetcalculator.dbhelper.DBManager;
import com.example.admin.budgetcalculator.model.DetailsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemviewAdapter> {

    private static final String TAG = "ItemAdapter";
    List<DetailsModel> list;
    DBManager dbManager;
    Context mContext;
    DetailsModel detailsModel;
    int total = 0;

    public ItemAdapter(Context mContext, List<DetailsModel> list) {
        this.list = list;
        this.mContext = mContext;
        this.dbManager = new DBManager(mContext);

    }
    @NonNull
    @Override
    public ItemviewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_row_item, viewGroup, false);

        return new ItemviewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemviewAdapter itemviewAdapter, int i) {
            detailsModel = list.get(i);
            itemviewAdapter.textViewRemarks.setText(detailsModel.getRemarks());
            itemviewAdapter.textViewDate.setText(detailsModel.getDate()+"-"+getMonthString(Integer.parseInt(detailsModel.getMonth())));
            itemviewAdapter.editTextAmount.setText(detailsModel.getDetails());
            itemviewAdapter.imageViewNext.setImageResource(R.drawable.ic_right_arrow);
            itemviewAdapter.imageViewDown.setImageResource(R.drawable.ic_down_arrow);

            if(detailsModel.getDebit_credit()!=null) {
                if (detailsModel.getDebit_credit().contains("credit")) {
                    itemviewAdapter.drcr.setImageResource(R.drawable.ic_plus);
                } else {
                    itemviewAdapter.drcr.setImageResource(R.drawable.ic_minus);
                }
            }




      //  dbManager.open();
      //  dbManager.updateAmount(detailsModel.getID(), nu, "");


            itemviewAdapter.imageViewDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbManager.open();
                    dbManager.insert2(detailsModel);
                    dbManager.delete(detailsModel.getID());
                    Log.d(TAG, "onClick1: "+detailsModel.getMonth()+" "+detailsModel.getYear()+" "+detailsModel.getDate());
                    ((BudgetActivity)mContext).recreate();
                }
            });

          /*  itemviewAdapter.imageViewNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbManager.open();
                    dbManager.update(detailsModel.getID(), nextMonth(), "");
                    ((BudgetActivity)mContext).recreate();
                }
            });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private String nextMonth()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }
    private String getMonthString(int num)
    {
        String months[];
        months = new String[13];
        months[0] = null ;
        months[1] = "Jan";
        months[2] = "Feb";
        months[3] = "Mar";
        months[4] = "Apr";
        months[5] = "May";
        months[6] = "Jun";
        months[7] = "Jul";
        months[8] = "Aug";
        months[9] = "Sep";
        months[10] = "Oct";
        months[11] = "Nov";
        months[12] = "Dec";
        return months[num];
    }
    private String getMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public class ItemviewAdapter extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewTotal;
        TextView textViewDetails;
        TextView textViewRemarks;
        EditText editTextAmount;
        ImageView imageViewDown, imageViewUp, imageViewNext, drcr;

        public ItemviewAdapter(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.tv_date);
            editTextAmount = itemView.findViewById(R.id.et_amount);
            textViewRemarks = itemView.findViewById(R.id.tv_memo);
            imageViewDown = itemView.findViewById(R.id.iv_down);
           // imageViewUp = itemView.findViewById(R.id.iv_down);
            imageViewNext = itemView.findViewById(R.id.iv_next);
            drcr = itemView.findViewById(R.id.debit_credit);
            textViewTotal = itemView.findViewById(R.id.tv_total);

        }
    }
}
