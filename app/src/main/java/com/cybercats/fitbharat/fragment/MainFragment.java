package com.cybercats.fitbharat.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cybercats.fitbharat.DayActivity;
import com.cybercats.fitbharat.MainActivity;
import com.cybercats.fitbharat.R;
import com.cybercats.fitbharat.SlidingModel;
import com.cybercats.fitbharat.adapters.WorkoutData;
import com.cybercats.fitbharat.database.DatabaseOperations;

import com.cybercats.fitbharat.utils.AppUtilss;
import com.cybercats.fitbharat.utils.Constants;

import java.util.ArrayList;

import java.util.List;

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<SlidingModel> imageModelArrayList;
    private ProgressBar progressBar, progressBar1;
    private TextView txtProgress, percentScore1, dayleft;
    public double k = 0.0d;
    public DatabaseOperations databaseOperations;
    public SharedPreferences launchDataPreferences;
    public ArrayList<String> arr;
    private int pStatus = 0;
    RelativeLayout rel_one, rel_two, rel_three;


    private int[] myImageList = new int[]{R.drawable.banner_1, R.mipmap.banner_calculator, R.mipmap.banner_3, R.mipmap.img_reminder};


    private SharedPreferences sharedPref;


    private float totalPercentage;
    me.itangqi.waveloadingview.WaveLoadingView waterLevelView;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;
    public int daysCompletionConter = 0;
    String str = "";

    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtilss.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) workoutDataList.get(workoutPosition)).setProgress((float) doubleExtra);
                MainFragment Workout = MainFragment.this;
                k = 0.0d;
                daysCompletionConter = 0;
                for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
                    MainFragment Workout2 = MainFragment.this;
                    double d = Workout2.k;
                    double progress = (double) ((WorkoutData) Workout2.workoutDataList.get(i)).getProgress();
                    Double.isNaN(progress);
                    Workout2.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
                    if (((WorkoutData) MainFragment.this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                        MainFragment.this.daysCompletionConter = MainFragment.this.daysCompletionConter + 1;
                    }
                }
                MainFragment Workout3 = MainFragment.this;
                Workout3.daysCompletionConter = Workout3.daysCompletionConter + (MainFragment.this.daysCompletionConter / 3);
                MainFragment.this.progressBar1.setProgress((int) MainFragment.this.k);
                TextView g = MainFragment.this.percentScore1;
                StringBuilder sb = new StringBuilder();
                sb.append((int) MainFragment.this.k);
                sb.append("%");
                g.setText(sb.toString());
                TextView h = MainFragment.this.dayleft;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Constants.TOTAL_DAYS - MainFragment.this.daysCompletionConter);
                sb2.append(" Days left");
                h.setText(sb2.toString());
//                MainFragment.this.adapter.notifyDataSetChanged();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(doubleExtra);
                Log.i("progress broadcast", sb3.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public void a(int i, MaterialDialog materialDialog, DialogAction dialogAction) {
        TextView textView = null;
        String str = null;
        try {
            materialDialog.dismiss();
            String str2 = (String) this.arr.get(i);
            if (this.workoutDataList != null) {
                this.workoutDataList.clear();
            }
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
            this.workoutDataList = this.databaseOperations.getAllDaysProgress();

            this.daysCompletionConter--;
            TextView textView2 = this.dayleft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
            sb.append(" Days left");
            textView2.setText(sb.toString());
            if (this.daysCompletionConter > 0) {
                this.progressBar1.setProgress((int) (k - 4.348d));
                textView = this.percentScore1;
                StringBuilder sb2 = new StringBuilder();
                sb2.append((int) (this.k - 4.348d));
                sb2.append("%");
                str = sb2.toString();
            } else {
                if (this.daysCompletionConter == 0) {
                    this.progressBar1.setProgress(0);
                    textView = this.percentScore1;
                    str = "0%";
                }
                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("day", str2);
                intent.putExtra("day_num", i);
                intent.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
                startActivity(intent);
            }
            textView.setText(str);
            Intent intent2 = new Intent(getActivity(), DayActivity.class);
            intent2.putExtra("day", str2);
            intent2.putExtra("day_num", i);
            intent2.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    MainActivity mainAcdsctivity;

    public MainFragment(MainActivity mainActivity) {
        this.mainAcdsctivity = mainActivity;
    }

    public static MainFragment newInstance(String str, String str2, MainActivity mainActivity) {
        MainFragment mainFragment = new MainFragment(mainActivity);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();


    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0) {
            this.databaseOperations.deleteTable();
            SharedPreferences.Editor edit = this.launchDataPreferences.edit();
            String str = "daysInserted";
            edit.putBoolean(str, false);
            edit.apply();
            edit.putBoolean(str, true);
            edit.apply();
            List<WorkoutData> list = this.workoutDataList;
            if (list != null) {
                list.clear();
            }
            this.workoutDataList = this.databaseOperations.getAllDaysProgress();
            this.percentScore1.setText("0%");
            TextView textView = this.dayleft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS);
            sb.append(" Days left");
            textView.setText(sb.toString());
        }
        super.onActivityResult(i, i2, intent);
    }
    RelativeLayout i_plus;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);

        mPager = inflate.findViewById(R.id.pager);

        waterLevelView = (me.itangqi.waveloadingview.WaveLoadingView) inflate.findViewById(R.id.waterLevelView);
        progressBar1 = (ProgressBar) inflate.findViewById(R.id.progress);


        percentScore1 = (TextView) inflate.findViewById(R.id.percentScore);
        dayleft = (TextView) inflate.findViewById(R.id.daysLeft);

        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.databaseOperations = new DatabaseOperations(getActivity());
        final String str = "thirtyday";
        boolean z = this.launchDataPreferences.getBoolean(str, false);
        String str2 = "daysInserted";
        boolean z2 = this.launchDataPreferences.getBoolean(str2, false);

        if (!z2 && this.databaseOperations.CheckDBEmpty() == 0) {
            this.databaseOperations.insertExcALLDayData();
            SharedPreferences.Editor edit = this.launchDataPreferences.edit();
            edit.putBoolean(str2, true);
            edit.apply();
        }



        rel_three = (RelativeLayout) inflate.findViewById(R.id.rel_three);


        rel_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainAcdsctivity.loadFragmentworkout(new Workout());


            }
        });





        {
//            Toast.makeText(getActivity(), (CharSequence) "Empty", 1).show();
        }

        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = databaseOperations.getAllDaysProgress();
        this.progressBar1 = (ProgressBar) inflate.findViewById(R.id.progress);
//        this.progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progressbar_drawable));

        for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
            double d = this.k;
            double progress = (double) ((WorkoutData) this.workoutDataList.get(i)).getProgress();
            Double.isNaN(progress);
            this.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
            if (((WorkoutData) this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                this.daysCompletionConter++;
            }
        }


        int i2 = this.daysCompletionConter;
        this.daysCompletionConter = i2 + (i2 / 3);
        this.progressBar1.setProgress((int) this.k);
        TextView textView = this.percentScore1;
        StringBuilder sb = new StringBuilder();
        sb.append((int) this.k);
        sb.append("%");
        textView.setText(sb.toString());
        TextView textView2 = this.dayleft;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
        sb2.append(" Days left");
        textView2.setText(sb2.toString());


        if (z) {
            SharedPreferences.Editor edit2 = this.launchDataPreferences.edit();
            edit2.putBoolean(str, false);
            edit2.apply();
            restartExcercise();
            this.daysCompletionConter = 0;
        }
        getActivity().registerReceiver(this.progressReceiver, new IntentFilter(AppUtilss.WORKOUT_BROADCAST_FILTER));
        if (this.daysCompletionConter > 4) {


        }


        return inflate;
    }


    private SharedPreferences getSharedPreferences(String users_shared_pref, int private_mode) {
        return getContext().getSharedPreferences(users_shared_pref, private_mode);
    }


//   first view pager

    private ArrayList<SlidingModel> populateList() {
        ArrayList<SlidingModel> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SlidingModel imageModel = new SlidingModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }


    public void restartExcercise() {
        SharedPreferences.Editor edit = this.launchDataPreferences.edit();
        String str = "daysInserted";
        edit.putBoolean(str, false);
        edit.apply();
        for (int i = 0; i < 30; i++) {
            String str2 = (String) this.arr.get(i);
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
        }
        edit.putBoolean(str, true);
        edit.apply();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();

        this.progressBar1.setProgress(0);
        this.percentScore1.setText("0%");
        TextView textView = this.dayleft;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.TOTAL_DAYS);
        sb.append(" Days left");
        textView.setText(sb.toString());
    }

//
}
