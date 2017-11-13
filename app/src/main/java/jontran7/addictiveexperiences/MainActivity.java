package jontran7.addictiveexperiences;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private RecyclerView vertical_recycler_view,horizontal_recycler_view;
    private ArrayList<String> horizontalList,verticalList;
    private HorizontalAdapter horizontalAdapter;
    //private VerticalAdapter verticalAdapter;


    private ArrayList<UsageBlock> myUsageBlockList;
    private ArrayList<String> packageNameList; //Replace with ArrayList of UsedApp
    private CheckBox myCheckBox;
    private TextView packageTxt;
    private int position;

    private ArrayList<App> foundAppList;

    private float packageDuration;
    private String packageName = "NULL";
    private float packageStartTime;
    private int count;

    private SharedPreferences preferences;
    private final String appNameKey = "NameValue";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = 1;
        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        myCheckBox = (CheckBox)findViewById(R.id.checkbox_button);
        packageTxt = (TextView) findViewById(R.id.txtView);

        startService( new Intent(this, MyService.class ) );

        verticalList=new ArrayList<>();
        for (int i = 480; i < 1440; i+=30) { // Replace 480 and 1440 with time at some point (and += 30 with variable: interval)
            verticalList.add(textLabelForMinuteOfTheDay(i));
        }

        //PopulateUsageBlockList();
        // have class variables

        // use method to update
        // needs a running thing to loop
        // then if app changes
        // then update SharedPreferences file needs a delimiter (separating characters)
        // be able to read back into program
        // use toast to double check
//

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate( new TimerTask() {
//            @Override
//            public void run() {
//                getCurrentApp();
////                count++;
////                changeCount( count );
//            }
//        }, 0, 1000);
        //getCurrentApp();
        preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showAppClick(View v) {
        String longList = "Hello";
        if (foundAppList != null && foundAppList.size() > 0) {
            for (int i = 0; i < foundAppList.size(); i++) {
                longList += foundAppList.get( i ).getName();
            }
        }
        TextView tvAppString = (TextView)findViewById(R.id.textApps);
        tvAppString.setText(longList );
    }

    public void readClick(View v) {

        String appNameOne = preferences.getString( appNameKey, "Not Found" );
        TextView tvPreferences = (TextView)findViewById( R.id.sharedPreferences );
        tvPreferences.setText( appNameOne );
    }

    public void clearShared(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }



//    public String toastMsg() {
//        //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences sharedPref = this.getSharedPreferences("general_settings", Context.MODE_PRIVATE);
//
//        String key = "4Keys";
//        String value = "Values";
//        String delimiterSeparatePair = "&";
//        String delimiterInsidePair = "#";
//
//        String incompleteString = "";
//        ArrayList<String> appList = new ArrayList<String>();
//        appList.add( "Instagram" );
//        appList.add( "Twitter" );
//        appList.add( "Facebook" );
//
//        for (int i = 0; i < appList.size(); i++) {
//            int uniqueKey = i;
//            incompleteString += + uniqueKey + delimiterInsidePair + appList.get(i) + delimiterSeparatePair;
//        }
//
//
//        //Probably a class ArrayList and then use iterate through list printing to shared
//        String fullMessage = incompleteString + key + delimiterInsidePair + value + delimiterSeparatePair;
////
////        Toast.makeText(getApplicationContext(), "This is my Toast message!",
////                Toast.LENGTH_LONG).show();
//
//        return fullMessage;
//    }

    // query every second
    // sort by lastTimeStamp
    // if it changes return name, duration, and firsttimestamp

    // query once and have a class variable
    // need runnable to check over again runnable needs to call itself to loop forever use it oncreate

    // make the query method return UsageStats object, then use the usagestats to create a list of app names  then build the big string to be written to the sharedpreferences
    // i can make a custom class that will hold the information needed fromm usage stats (name, duration, etc)..

    //objective for 10/23
    //

    public void CreateUsageBlock(ArrayList<UsageBlock> blockList) {

        //Create child of parent
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.dummyParent);

        //array containing all children ids
        ArrayList<Integer> children = new ArrayList<>();

        //adding 10 children to the parent

        RelativeLayout child = new RelativeLayout( this ); //new child
        //child.setId(i); //setting an id for the child
        //children.add(i); //adding the child's id to the list
        child.setBackgroundColor(Color.RED);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(20, 20);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT );
        parent.addView(child);
    }



    private String textLabelForMinuteOfTheDay(int minuteOfDay) {
        String startTime = "00:00 AM";

        String hourLabel;
        String minLabel;

        int hourCalc = minuteOfDay / 60 + Integer.parseInt( startTime.substring( 0,1 ));
        hourLabel = "" + hourCalc;
        if (hourCalc < 10) {
            hourLabel = "0"+hourCalc;
        }

        int minCalc = minuteOfDay % 60 + Integer.parseInt( startTime.substring( 3,4 ));
        minLabel = "" + minCalc;
        if (minCalc == 30) {
            minLabel = "30";
        } else if (minCalc == 0){
            minLabel = "00";
        }
        String newTime = hourLabel + " : " + minLabel;
        return newTime;

    }

    // End
}
