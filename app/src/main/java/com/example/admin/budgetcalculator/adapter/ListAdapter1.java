package com.example.admin.budgetcalculator.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ListAdapter1 extends ArrayAdapter<DetailsModel> {


    //the list values in the List of type hero
    List<DetailsModel> heroList;
    String msg = null;

    //activity context
    Context context;
    DetailsModel detailsModel;
    AlertDialog alertDialog = null;

    //the layout resource file for the list items
    int resource;
    DBManager dbManager;
    //constructor initializing the values
    public ListAdapter1(Context context, int resource, List<DetailsModel> heroList) {
        super(context, resource, heroList);
        this.context = context;
        this.resource = resource;
        this.heroList = heroList;
        dbManager = new DBManager(this.context);
    }
    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View itemView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(R.layout.rv_row_item, null, false);
        TextView textViewDate, textViewTotal;
        TextView textViewDetails;
        TextView textViewRemarks;
        EditText editTextAmount;
        ImageView imageViewDown, imageViewEdit, imageViewNext, drcr;
        textViewDate = view.findViewById(R.id.tv_date);
        editTextAmount = view.findViewById(R.id.et_amount);
        textViewRemarks = view.findViewById(R.id.tv_memo);
        imageViewDown = view.findViewById(R.id.iv_down);
        // imageViewUp = view.findViewById(R.id.iv_down);
        imageViewNext = view.findViewById(R.id.iv_next);
        drcr = view.findViewById(R.id.debit_credit);
        textViewTotal = view.findViewById(R.id.tv_total);
        //imageViewEdit = view.findViewById(R.id.iv_edit);
        //getting the hero of the specified position
        final DetailsModel detailsModel = heroList.get(position);

        textViewRemarks.setText(detailsModel.getRemarks());
        textViewDate.setText(detailsModel.getDate()+"-"+getMonthString(Integer.parseInt(detailsModel.getMonth())));
        editTextAmount.setText(detailsModel.getDetails());
        imageViewNext.setImageResource(R.drawable.ic_right_arrow);
        imageViewDown.setImageResource(R.drawable.ic_down_arrow);
        //imageViewEdit.setImageResource(R.drawable.ic_edit);
        if(detailsModel.getDebit_credit()!=null) {
            if (detailsModel.getDebit_credit().contains("credit")) {
                drcr.setImageResource(R.drawable.ic_plus);
            } else {
                drcr.setImageResource(R.drawable.ic_minus);
            }
        }



      /* drcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("vbvcbv:"+detailsModel.getDetails());
            }
        });
*/
        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                dbManager.insert2(detailsModel);
                dbManager.delete(detailsModel.getID());
                ((BudgetActivity)context).recreate();
            }
        });
        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                dbManager.update(detailsModel.getID(), nextMonth(), "");
                ((BudgetActivity)context).recreate();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                updateDetails(detailsModel);
                return true;
            }
        });

        drcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                if(detailsModel.getDebit_credit().contains("credit")) {
                    dbManager.update(detailsModel.getID(), "debit");
                }
                else {
                    dbManager.update(detailsModel.getID(), "credit");
                }

                ((BudgetActivity)context).recreate();
            }
        });


        /*//adding a click listener to the button to remove item from the list
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removeHero(position);
            }
        });*/

        //finally returning the view
        return view;
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


    private void updateDetails(final DetailsModel detailsModel)
    {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view2 = inflater.inflate(R.layout.update_pop_up, null);
        alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view2);
        final EditText day, month, year, amount, reamrks;
        final RadioGroup radioGroup;
        RadioButton debit, credit;

        Button update;
        day = view2.findViewById(R.id.et_day);
        month = view2.findViewById(R.id.et_month);
        year = view2.findViewById(R.id.et_year);
        amount = view2.findViewById(R.id.et_amount);
        reamrks = view2.findViewById(R.id.et_remarks);
        update = view2.findViewById(R.id.btn_update);
        radioGroup = view2.findViewById(R.id.rg_fixed_variable);
        debit = view2.findViewById(R.id.debit);
        credit = view2.findViewById(R.id.credit);


        if(detailsModel.getDebit_credit().contains("credit"))
        {
         credit.setChecked(true);
        }
        else {
           debit.setChecked(true);
        }
        msg = detailsModel.getDebit_credit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.credit)
                {
                    msg = "credit";
                }
                else if(i==R.id.debit)
                {
                    msg = "debit";
                }
            }
        });

        day.setText(detailsModel.getDate());
        month.setText(detailsModel.getMonth());
        year.setText(detailsModel.getYear());
        amount.setText(detailsModel.getDetails());
        reamrks.setText(detailsModel.getRemarks());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.open();
                dbManager.update(detailsModel.getID(), month.getText().toString(), day.getText().toString(),year.getText().toString(), amount.getText().toString(), reamrks.getText().toString(), msg);
                ( (BudgetActivity)context).recreate();
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    //this method will remove the item from the list
    private void removeHero(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                heroList.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
