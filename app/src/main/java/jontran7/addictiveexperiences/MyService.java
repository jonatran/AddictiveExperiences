package jontran7.addictiveexperiences;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private long packageDuration;
    private String packageName = "NULL";
    private long packageStartTime;
    private ArrayList<App> foundAppList;
    private final String appNameKey = "NameValue";
    private String lastAppName = "";
    private SharedPreferences preferences;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        //runnable here
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                getCurrentApp();
                int x = 0;
                x++;
            }
        }, 0, 1, TimeUnit.SECONDS);

        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getCurrentApp() {
        long foundAppStartTime = 0;
        // Get the current foreground application for current second
        UsageStatsManager usm = (UsageStatsManager)this.getSystemService(USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        long pastTime = System.currentTimeMillis() - 1000;

        //Get the latest app foreground app (one second ago)
        //List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,pastTime,currentTime); //Currently gets last thousand seconds to pastTime (one second ago) (interval, begin, end) This is returning null?
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 100 * 1000, currentTime);
        if (appList != null && appList.size() > 0) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats); //sorts by lastTimeUsed
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                packageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                foundAppStartTime = mySortedMap.get( mySortedMap.lastKey()).getFirstTimeStamp();
            }
        }
        //Things for 11/5
        // have it add only for new apps
        // add timestamp / duration
        // delimiters
        // Relative layout blocks


        //compare the two apps
        if (packageName.equals(lastAppName)) {

            packageStartTime = foundAppStartTime;
            //App latestApp = new App( packageName, packageStartTime, packageDuration );
            //foundAppList.add(latestApp);
        }
        else {
            long foundAppEndTime = System.currentTimeMillis();
            buildSharedPrefWrite();
            packageDuration = foundAppEndTime - foundAppStartTime;
            preferences.edit().putString(appNameKey, preferences.getString( "NameValue", "" ) + buildSharedPrefWrite()).commit();
            //preferences.edit().putString(appNameKey, preferences.getString( "NameValue", "" ) + packageName).commit();
            //Reset the class variables.
            lastAppName = packageName;
            packageDuration = 0;
            packageStartTime = 0;
            //return name, duration, and firstTimeStamp or maybe just have no else
        }
    }

    private String buildSharedPrefWrite() {
        String encodedString = "#" + lastAppName + "&" + packageDuration + "&" + packageStartTime + "\n";
        return encodedString;
    }

    @Override
    public void onDestroy ()
    {
        int x =0;
        x++;
    }

}
