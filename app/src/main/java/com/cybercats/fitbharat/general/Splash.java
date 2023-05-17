package com.cybercats.fitbharat.general;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import com.anjlab.android.iab.p004v3.BillingProcessor;
//import com.anjlab.android.iab.p004v3.BillingProcessor.IBillingHandler;
//import com.anjlab.android.iab.p004v3.TransactionDetails;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import com.cybercats.fitbharat.MainActivity;
import com.cybercats.fitbharat.R;
import com.cybercats.fitbharat.utils.GlobalFunction;
import com.cybercats.fitbharat.utils.SharedPreferenceManager;

public class Splash extends Activity implements BillingProcessor.IBillingHandler {
    String TAG = getClass().getSimpleName();
    BillingProcessor billingProcessor;
    GlobalFunction globalFunction;
    SharedPreferenceManager sharedPreferenceManager;

    public void onBillingError(int i, Throwable th) {
    }

    public void onProductPurchased(String str, TransactionDetails transactionDetails) {
    }

    public void onPurchaseHistoryRestored() {
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);

        setContentView(R.layout.splash);
        if (this.globalFunction.isConnectingToInternet()) {
            this.billingProcessor = new BillingProcessor(this, getString(R.string.base64), this);
        }
        this.globalFunction.set_locale_language();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Splash.this.startActivity(new Intent(Splash.this.getApplicationContext(), MainActivity.class));
                Splash.this.finish();
            }
        }, (long)2000);
    }

    public void onBillingInitialized() {
        if (!this.billingProcessor.loadOwnedPurchasesFromGoogle()) {
            return;
        }
        if (this.billingProcessor.isPurchased("remove_ad")) {
            this.sharedPreferenceManager.set_Remove_Ad(Boolean.valueOf(true));
        } else {
            this.sharedPreferenceManager.set_Remove_Ad(Boolean.valueOf(false));
        }
    }
}
