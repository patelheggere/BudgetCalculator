package com.example.admin.budgetcalculator.activity;

import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.admin.budgetcalculator.R;
import com.example.admin.budgetcalculator.adapter.ItemAdapter;
import com.example.admin.budgetcalculator.adapter.ItemAdapter2;
import com.example.admin.budgetcalculator.adapter.ListAdapter1;
import com.example.admin.budgetcalculator.adapter.ListAdapter2;
import com.example.admin.budgetcalculator.dbhelper.DBManager;
import com.example.admin.budgetcalculator.dbhelper.DatabaseHelper;
import com.example.admin.budgetcalculator.model.DetailsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ConstraintLayout linearLayoutMainContent;
    private PopupWindow popupWindow;
    private AlertDialog alertDialog;
    private EditText phone, remarks;
    private boolean isCredit = true;
    private DBManager dbManager;
    private boolean isFixed = true;
    private RadioButton crde, debt, mont, var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        linearLayoutMainContent = findViewById(R.id.main_content);

        mViewPager.setCurrentItem(Integer.parseInt(getMonth())-1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if(isFirstDayOfTheMonth(new Date())) {
            dbManager = new DBManager(BudgetActivity.this);
            dbManager.open();
            Cursor cursor = dbManager.fetch();
            if (cursor.moveToFirst()) {
                do {
                    DetailsModel ob = new DetailsModel();
                    dbManager.updateAmount(cursor.getColumnIndex(DatabaseHelper._ID), nextMonth(), "");
                    /*ob.setRemarks(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMARKS)));
                    ob.setDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DETAILS)));
                    ob.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE)));
                    ob.setDebit_credit(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DEBIT_CREDIT)));
                    ob.setMonth(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MONTH)));
                    ob.setYear(cursor.getString(cursor.getColumnIndex(DatabaseHelper.YEAR)));
                    ob.setID(cursor.getLong(cursor.getColumnIndex(DatabaseHelper._ID)));
                    //System.out.println("onCreateView: "+ob.getMonth());
                    list.add(ob);*/
                    // dbManager.delete(mCursor.getLong(mCursor.getColumnIndex(DatabaseHelper._ID)));
                } while (cursor.moveToNext());
            }
           // dbManager.update(detailsModel.getID(), nextMonth(), "");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(BudgetActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view2 = inflater.inflate(R.layout.popup_window, null);
                builder.setView(view2);

                //TextView textViewTitle = view.findViewById(R.id.tv_cant_cashout);
                RadioGroup radioGroup = view2.findViewById(R.id.rg);
                RadioGroup radioGroup1 = view2.findViewById(R.id.rg_fixed_variable);

                crde = view2.findViewById(R.id.credit);
                debt = view2.findViewById(R.id.debit);
                crde.setChecked(true);

                mont = view2.findViewById(R.id.fixed);
                var = view2.findViewById(R.id.variable);
                mont.setChecked(true);

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i==R.id.fixed)
                        {
                         isFixed = true;
                        }
                        else if(i==R.id.variable) {
                            isFixed = false;
                        }
                    }
                });

                Button btn = view2.findViewById(R.id.submit);
                phone  = view2.findViewById(R.id.pop_amount);
                remarks = view2.findViewById(R.id.pop_remarks);
                dbManager = new DBManager(BudgetActivity.this);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i==R.id.credit)
                        {
                            isCredit = true;
                        }
                        else if(i==R.id.debit)
                        {
                            isCredit = false;
                        }
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(phone.getText().toString());
                        alertDialog.dismiss();
                        dbManager.open();
                        DetailsModel detailsModel = new DetailsModel();
                        detailsModel.setDate(getDate());
                        detailsModel.setYear(getYear());
                        detailsModel.setMonth(getMonth());
                        detailsModel.setDetails(phone.getText().toString());
                        detailsModel.setRemarks(remarks.getText().toString());
                        if(isCredit)
                        {
                            detailsModel.setDebit_credit("credit");
                        }
                        else {
                            detailsModel.setDebit_credit("debit");
                        }

                        if(isFixed)
                        {
                            dbManager.insert(detailsModel);
                        }
                        else {
                            dbManager.insert2(detailsModel);
                        }

                        recreate();
                    }
                });

                alertDialog = builder.create();
               // alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }
        });

    }
    public static boolean isFirstDayOfTheMonth(Date dateToday){
        Calendar c = new GregorianCalendar();
        c.setTime(dateToday );

        if (c.get(Calendar.DAY_OF_MONTH) == 1)
            return true;
        else
        return false;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getYear()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecyclerView recyclerViewFixed, recyclerViewVariable;
        private List<DetailsModel> list, list2;
        private ItemAdapter itemAdapter;
        private ItemAdapter2 itemAdapter2;
        private DBManager dbManager;
        private Cursor mCursor, mCursor2;
        private int total, total2;
        private TextView textViewTotal, textViewTotal2;
        private LinearLayout linearLayoutTotal, linearLayoutTotal2;
        private ListView listView1, listView2;
        private ListAdapter1 adapter;
        private ListAdapter2 adapter2;
        private AlertDialog alertDialog;

        public PlaceholderFragment() {
        }

        private void initViews() {
        }

        private String getMonth()
        {
            DateFormat dateFormat = new SimpleDateFormat("MM");
            Date date = new Date();
            return dateFormat.format(date);
        }


        public PlaceholderFragment newInstance(String sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, sectionNumber);
            System.out.println("Section number:"+sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String months[];
            months = new String[13];
            months[0] = null ;
            months[1] = "January";
            months[2] = "February";
            months[3] = "March";
            months[4] = "April";
            months[5] = "May";
            months[6] = "June";
            months[7] = "July";
            months[8] = "August";
            months[9] = "September";
            months[10] = "October";
            months[11] = "November";
            months[12] = "December";
            View rootView = inflater.inflate(R.layout.fragment_budget, container, false);
            recyclerViewFixed = rootView.findViewById(R.id.rv_fixed);
            recyclerViewVariable = rootView.findViewById(R.id.rv_variable);
            linearLayoutTotal = rootView.findViewById(R.id.fixed_total_ll);
            linearLayoutTotal2 = rootView.findViewById(R.id.fixed_total_ll2);


            listView1 = rootView.findViewById(R.id.lv_fixed);

            listView2 = rootView.findViewById(R.id.lv_variable);

            textViewTotal = rootView.findViewById(R.id.tv_total);
            textViewTotal2 = rootView.findViewById(R.id.tv_total2);
            TextView textViewNoFixed = rootView.findViewById(R.id.tv_no_data_fixed);
            TextView textViewNoVariable = rootView.findViewById(R.id.tv_no_data_var);
            dbManager = new DBManager(getContext());
            dbManager.open();
            Bundle bundle=getArguments();
            mCursor = dbManager.fetch(bundle.getString(ARG_SECTION_NUMBER));
            mCursor2 = dbManager.fetch2(bundle.getString(ARG_SECTION_NUMBER));
            list = new ArrayList<>();
            list2 = new ArrayList<>();

            if (mCursor.moveToFirst()) {
                do {
                    DetailsModel ob = new DetailsModel();
                    ob.setRemarks(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.REMARKS)));
                    ob.setDetails(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.DETAILS)));
                    ob.setDate(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.DATE)));
                    ob.setDebit_credit(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.DEBIT_CREDIT)));
                    ob.setMonth(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.MONTH)));
                    ob.setYear(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.YEAR)));
                    ob.setID(mCursor.getLong(mCursor.getColumnIndex(DatabaseHelper._ID)));
                    //System.out.println("onCreateView: "+ob.getMonth());
                    list.add(ob);
                    // dbManager.delete(mCursor.getLong(mCursor.getColumnIndex(DatabaseHelper._ID)));
                } while (mCursor.moveToNext());
            }

            if(list.size()==0)
            {
                recyclerViewFixed.setVisibility(View.GONE);
                textViewNoFixed.setVisibility(View.VISIBLE);
            }
            else {
                recyclerViewFixed.setVisibility(View.VISIBLE);
                textViewNoFixed.setVisibility(View.GONE);
            }



            if (mCursor2.moveToFirst()) {
                do {
                    DetailsModel ob = new DetailsModel();
                    ob.setRemarks(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.REMARKS)));
                    ob.setDetails(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.DETAILS)));
                    ob.setDate(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.DATE)));
                    ob.setDebit_credit(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.DEBIT_CREDIT)));
                    ob.setMonth(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.MONTH)));
                    ob.setYear(mCursor2.getString(mCursor2.getColumnIndex(DatabaseHelper.YEAR)));
                    ob.setID(mCursor2.getLong(mCursor2.getColumnIndex(DatabaseHelper._ID)));
                    list2.add(ob);
                   // System.out.println("onCreateView: 2"+ob.getMonth());
                    // dbManager.delete(mCursor2.getLong(mCursor2.getColumnIndex(DatabaseHelper._ID)));
                } while (mCursor2.moveToNext());
            }

            for (int i = 0; i<list.size(); i++)
            {
                if(list.get(i).getDebit_credit().contains("credit")) {
                    total += Integer.parseInt(this.list.get(i).getDetails());
                }
                else {
                    total-=Integer.parseInt(this.list.get(i).getDetails());
                }
            }
            if(total==0)
            {
                linearLayoutTotal.setVisibility(View.INVISIBLE);
            }
            else {
                linearLayoutTotal.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i<list2.size(); i++)
            {
                if(list2.get(i).getDebit_credit().contains("credit")) {
                    total2 += Integer.parseInt(this.list2.get(i).getDetails());
                }
                else {
                    total2-=Integer.parseInt(this.list2.get(i).getDetails());
                }
            }

            if(total2==0)
            {
                linearLayoutTotal2.setVisibility(View.INVISIBLE);
            }
            else {
                linearLayoutTotal2.setVisibility(View.VISIBLE);
            }
            textViewTotal.setText(""+total);
            textViewTotal2.setText(""+total2);

            System.out.println("dfg:"+list2.size());
            if(list2.size()==0)
            {
                recyclerViewVariable.setVisibility(View.GONE);
                textViewNoVariable.setVisibility(View.VISIBLE);
            }
            else {
                recyclerViewVariable.setVisibility(View.VISIBLE);
                textViewNoVariable.setVisibility(View.GONE);
            }
           /* itemAdapter = new ItemAdapter(getContext(), list);
            itemAdapter2 = new ItemAdapter2(getContext(), list2);

            recyclerViewVariable.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerViewVariable.setAdapter(itemAdapter2);

            recyclerViewFixed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerViewFixed.setAdapter(itemAdapter);

            recyclerViewVariable.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            recyclerViewFixed.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));*/

            recyclerViewFixed.setVisibility(View.GONE);
            recyclerViewVariable.setVisibility(View.GONE);

            //creating the adapter
            adapter = new ListAdapter1(getContext(), R.layout.rv_row_item, list);
            adapter2 = new ListAdapter2(getContext(), R.layout.rv_row_item, list2);


            //attaching adapter to the listview
            listView1.setAdapter(adapter);
            listView2.setAdapter(adapter2);

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("ddgg"+list.get(i).getDetails());
                }
            });

            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //update(2);
                }
            });

            return rootView;
        }

        private void update(int i)
        {
            System.out.println("dfgdfg:"+i);
            LayoutInflater inflater = getLayoutInflater();
            View view2 = inflater.inflate(R.layout.update_pop_up, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view2);
            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            position = position+1;
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new PlaceholderFragment().newInstance(""+position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 12;
        }
    }
}
