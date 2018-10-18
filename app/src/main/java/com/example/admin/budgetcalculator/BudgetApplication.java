package com.example.admin.budgetcalculator;

import android.app.Application;

public class BudgetApplication extends Application {
    private static BudgetApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // ApiClient.intialise();
       /* if(isDeve()) {
            ApiClient.setBaseUrl(AppConstants.appBaseUrlDev);
        }
        else
        {
            ApiClient.setBaseUrl(AppConstants.appBaseUrl);

        }*/

    }

    public static synchronized BudgetApplication getInstance() {
        return mInstance;
    }

}