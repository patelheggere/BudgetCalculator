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

public class ItemAdapter2 extends RecyclerView.Adapter<ItemAdapter2.ItemviewAdapter> {

    private static final String TAG = "ItemAdapter2";
    List<DetailsModel> list;
    Context mContext;
    DBManager dbManager;
    DetailsModel detailsModel;

    public ItemAdapter2(Context mContext, List<DetailsModel> list) {
        this.list = list;
        this.mContext = mContext;
        this.dbManager = new DBManager(mContext);
        for (int i = 0; i<list.size(); i++)
        {
            Log.d(TAG, "ItemAdapter2: "+list.get(i).getMonth());
        }
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
            itemviewAdapter.imageViewDown.setImageResource(R.drawable.ic_up_arrow);
            if(detailsModel.getDebit_credit()!=null) {
            if (detailsModel.getDebit_credit().contains("credit")) {
                itemviewAdapter.drcr.setImageResource(R.drawable.ic_plus);
            } else {
                itemviewAdapter.drcr.setImageResource(R.drawable.ic_minus);
            }
        }
            itemviewAdapter.imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                dbManager.insert(detailsModel);
                dbManager.delete2(detailsModel.getID());
                ((BudgetActivity)mContext).recreate();
            }
        });

        itemviewAdapter.editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nu = editable.toString();
                dbManager.open();
                dbManager.update2Amount(detailsModel.getID(), nu, "");
                //((BudgetActivity)mContext).recreate();
            }
        });

        itemviewAdapter.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                dbManager.update2(detailsModel.getID(), nextMonth(), "");
                ((BudgetActivity)mContext).recreate();
            }
        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemviewAdapter extends RecyclerView.ViewHolder {
        TextView textViewDate;
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
            //imageViewUp = itemView.findViewById(R.id.iv_down);
            imageViewNext = itemView.findViewById(R.id.iv_next);
            drcr = itemView.findViewById(R.id.debit_credit);


        }
    }
}
