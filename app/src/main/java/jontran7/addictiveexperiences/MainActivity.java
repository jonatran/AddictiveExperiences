package jontran7.addictiveexperiences;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
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
import java.util.TreeMap;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        position = 1;
        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        myCheckBox = (CheckBox)findViewById(R.id.checkbox_button);
        packageTxt = (TextView) findViewById(R.id.txtView);

        verticalList=new ArrayList<>();
        for (int i = 480; i < 1440; i+=30) { // Replace 480 and 1440 with time at some point (and += 30 with variable: interval)
            verticalList.add(textLabelForMinuteOfTheDay(i));
        }
        //PopulateUsageBlockList();



        // Access the default SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // The SharedPreferences editor - must use commit() to submit changes
        SharedPreferences.Editor editor = preferences.edit();
        printForegroundTask(); // have this method return a string
        // Edit the saved preferences
        editor.putString("UserName", "JaneDoe"); //print string here
        editor.putInt("UserAge", 22);
        editor.commit();


        // front end start

        //instantiate custom adapter
        VerticalAdapter adapter = new VerticalAdapter(verticalList, this, myUsageBlockList);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);


        packageNameList=new ArrayList<>();
        RelativeLayout parentForVerticalList = (RelativeLayout) findViewById(R.id.baseLayout);


        for (int i = 0; i < myUsageBlockList.size(); i++) {
                packageNameList.add( myUsageBlockList.get( i ).getPackageName() );
        }
        horizontalAdapter=new HorizontalAdapter(packageNameList);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        horizontal_recycler_view.setAdapter(horizontalAdapter);
        //CreateUsageBlock( myUsageBlockList );
    }

    public void CreateUsageBlock(ArrayList<UsageBlock> blockList) {

        //Create child of parent
        RelativeLayout parent = (RelativeLayout) findViewById(R.id.dummyParent);

        //array containing all children ids
        ArrayList<Integer> children = new ArrayList<>();

        //adding 10 children to the parent

        RelativeLayout child = new RelativeLayout( this ); //new child
        //child.setId(i); //setting an id for the child
        //children.add(i); //adding the child's id to the list
        child.setBackgroundColor( Color.RED  );
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(20, 20);
        rlp.addRule( RelativeLayout.CENTER_IN_PARENT );
        parent.addView(child);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getCurrentApp() {
        //get app for everyone second
        //track duration
        //once app changes, write to a file
        float startTime = 0;
        int duration = 1000; //in milliseconds, starts at one second
        String currentName = "NULL";
        String lastName = "NULL";
        // Get the current foreground application for current second
        UsageStatsManager usm = (UsageStatsManager)this.getSystemService(USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        long pastTime = System.currentTimeMillis() - 1000;
        //Get the latest app foreground app.
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  currentTime - 1000*1000, pastTime); //Currently gets last thousand seconds to pastTime (one second ago) (interval, begin, end)
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats); //sorts by lastTimeUsed
                //should i query again but one second later?
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                lastName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
            }
        }

        //get the current app
        //does this work the way im thinking? (will just update the list?) or do i need to make a new list
        appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  currentTime - 1000*1000, currentTime);
        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
        for (UsageStats usageStats : appList) {
            mySortedMap.put(usageStats.getLastTimeUsed(), usageStats); //sorts by lastTimeUsed
            //should i query again but one second later?
        }
        if (mySortedMap != null && !mySortedMap.isEmpty()) {
            currentName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
            startTime = mySortedMap.get( mySortedMap.lastKey() ).getFirstTimeStamp();
        }

        if (currentName.equals(lastName)) {

        } else {
            duration+=1000;
        }

        //get current foreground app
        //get foreground app used 1 second ago
        //if they match, add to duration
        //else, print pastApp, duration, and firstTimeStamp to SharedPreferences file.
    }

    public void CheckLastAppToCurrent() {
        int duration = 0;
        String lastName = "NULL";
        float startTime = 0;
        String currentName = "NULL";

    }

    private String printForegroundTask() {
        String lastApp = "NULL";
        String currentApp = "NULL";
        float pastTime = System.currentTimeMillis() - 1000; //back one second
        float currentTime = System.currentTimeMillis();

        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
        currentApp = tasks.get(0).processName;
        Log.e("adapter", "Current App in foreground is: " + currentApp);
        return currentApp;

        // set currentapp
        //in one second check if current app is equal to new app
        //if not equal, then add to duration
        //else, print duration, name, and first time stamp on SharedPreferences
    }



    /*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void PopulateUsageBlockList() {
        UsageStatsManager myUsageStatsManager = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        // Pre-9/23

        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        long startTime = calendar.getTimeInMillis();
        //use loop to go backwards 24 hrs use small query intervals to add up the "chunks" to form one usage block
        List<UsageStats> myUses = myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);

        Calendar calendarSeconds = Calendar.getInstance();
        long startTimeSeconds = calendarSeconds.getTimeInMillis(); // in milliseconds
        long endTimeSeconds = calendarSeconds.getTimeInMillis() + 1000;

        // Query usage stats for intervals of seconds, then use a loop to traverse the list and build blocks? but can't get intervals of seconds...
        List<UsageStats> mySecondIntervalUses = myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,startTime,endTime);

        int timeStamp = (int)mySecondIntervalUses.get(0).getFirstTimeStamp();
        int instanceDuration = 0;
        String currentName = mySecondIntervalUses.get( 0 ).getPackageName();
        String nextName = "";
        calendarSeconds.setTimeInMillis(timeStamp);
        String firstTime = DateFormat.format("dd-MM-yyyy hh:mm:ss", calendarSeconds).toString(); // showing what the timestamp is
        int lastTimeStamp = (int) mySecondIntervalUses.get( 0 ).getLastTimeStamp();
        calendarSeconds.setTimeInMillis( lastTimeStamp );
        String secondTime =  DateFormat.format("dd-MM-yyyy hh:mm:ss", calendarSeconds).toString();

        // 9.23 work start
        //use loop to go backwards 24 hrs, use small query intervals (seconds) to add up chunks of usage to form one block.
        for (int secondsOfTheDay = 0; secondsOfTheDay < 86400; secondsOfTheDay++) { // in seconds
            startTimeSeconds = calendarSeconds.getTimeInMillis() * 1000 + secondsOfTheDay; // in seconds
            endTimeSeconds = calendarSeconds.getTimeInMillis() * 1000 + secondsOfTheDay;
            mySecondIntervalUses.addAll(myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,startTime,endTime));
            // if name is different then create a usage block
            if (currentName != nextName) {
            // create a new usage block and add it to myUsageBlockList (see code below)
            }
            // else add the duration
            else {

            }
        }
        List<UsageStats> myUsesSeconds = myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,startTime,endTime );

        myUsageBlockList = new ArrayList<UsageBlock>();

        assert myUses != null;

        for (int i = 0; i < myUses.size(); i++) {
            UsageBlock xBlock = new UsageBlock( myUses.get( i ).getPackageName(), (int) myUses.get( i ).getTotalTimeInForeground() / 1000, (int) myUses.get( i ).getFirstTimeStamp() );
            if (xBlock.getDuration() > 0) { // Only add the app to the list if the duration is > 0 (only if it's been used.)
                myUsageBlockList.add( xBlock );
            }
        }
    }
    */
    //query small intervals for last 24 hours (returns list)
    //find foreground app for the same interval in the list
    //add duration until name change
    //once foreground app changes create a usageblock and add to myUsageBlockList
    //create relativelayoutblock to represent that usageblock


    // 10/6 Version Control
    /*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String PopulateUsageBlockList() {
        Calendar calendar = Calendar.getInstance();
        long startTimeSeconds = calendar.getTimeInMillis(); // in milliseconds
        long endTimeSeconds = calendar.getTimeInMillis() + 1000;

        String currentApp = "NULL";
        String nextApp = "NULL";
        int duration = 1; // in seconds

        //query one time to set currentApp
        UsageStatsManager usm = (UsageStatsManager)this.getSystemService(USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        // use loop to query one second backwards
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  startTimeSeconds, endTimeSeconds); // query one second

        // this chunk will get the last app used in the last second of phone activity.
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
            }
        }

        // Loop back 24 hours (in seconds)
        for (int currentSecond = 1; currentSecond < 86400; currentSecond++) {
            float currentTime = time - 1000*currentSecond; // for debugging
            appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * (currentSecond + 1), time - currentSecond * 1000); // begin time, end time
            // this chunk will get the last app used in the last second of phone activity.
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    nextApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName(); //get the packageName of the last unique key's value (UsageStats)
                    // if current app name is same as nextName then add duration
                    if (nextApp.equals(currentApp)) {
                        duration++;
                    } else {
                        UsageBlock xBlock = new UsageBlock(currentApp,duration, (int) mySortedMap.get(mySortedMap.lastKey()).getFirstTimeStamp());
                        currentApp = nextApp;
                    }
                    // else create a new usageblock, add it to the list of myUsageBlocks
                    // set currentApp equal to nextApp
                    // separate backend and frontend separately so create relativelayout blocks somewhere else
                }
            }
        }


        // use loop to query +second X
        Log.e("adapter", "Current App in foreground is: " + currentApp); // how do i use the log (where do i see this messgage?)
        Log.e("adapter", "Current App in foreground is: " + nextApp);
        return currentApp;
    }
    // End 10/6 Version Control
    */
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
