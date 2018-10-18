package com.example.admin.budgetcalculator.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.budgetcalculator.helper.SharedPrefsHelper;
import com.example.admin.budgetcalculator.model.DetailsModel;
import com.example.admin.budgetcalculator.R;
import com.example.admin.budgetcalculator.dbhelper.DBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DBManager dbManager;
    private EditText mDate, mmSalary;
    private Button buttonNext;
    private TextView monthName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(MainActivity.this);
        dbManager = dbManager.open();
        if(SharedPrefsHelper.getInstance().get("FIRST_TIME", true)) {
            initViews();
        }
        else {
            startActivity(new Intent(MainActivity.this, BudgetActivity.class));
            finish();
        }
    }

    private void initViews() {
        mDate = findViewById(R.id.et_salary);
        mmSalary = findViewById(R.id.et_salary_date);
        buttonNext = findViewById(R.id.btn_next);
        monthName = findViewById(R.id.tv_month_name);
        monthName.setText(getMonthFullName());

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date =  mDate.getText().toString();
                String sal = mmSalary.getText().toString();
                if(date!=null && sal!=null) {
                    DetailsModel detailsModel = new DetailsModel();
                    detailsModel.setDate(date);
                    detailsModel.setDebit_credit("credit");
                    detailsModel.setDetails(sal);
                    detailsModel.setYear(getYear());
                    detailsModel.setMonth(getMonth());
                    detailsModel.setRemarks("Salary");
                    dbManager.insert(detailsModel);
                    SharedPrefsHelper.getInstance().save("FIRST_TIME", false);
                    startActivity(new Intent(MainActivity.this, BudgetActivity.class));
                    finish();
                }
            }
        });
    }

    private String getYear()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getMonthFullName()
    {
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
