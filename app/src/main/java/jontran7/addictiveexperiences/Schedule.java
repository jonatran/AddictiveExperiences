package jontran7.addictiveexperiences;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Original MainActivity.java
public class Schedule extends AppCompatActivity {

    private ArrayList<UsageBlock> myUsageBlockList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ListView mListView = (ListView) findViewById(R.id.listView);
        PopulateUsageBlockList();
        UsageBlockAdapter adapter = new UsageBlockAdapter(this, R.layout.adapter_view_layout, myUsageBlockList);
        //mListView.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void PopulateUsageBlockList() {
        UsageStatsManager myUsageStatsManager = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        long startTime = calendar.getTimeInMillis();
        List<UsageStats> myUses = myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);

        myUsageBlockList = new ArrayList<UsageBlock>();

        assert myUses != null;

        for (int i = 0; i < myUses.size(); i++) {
            UsageBlock xBlock = new UsageBlock(myUses.get(i).getPackageName(), (int)myUses.get(i).getTotalTimeInForeground() / 1000, (int)myUses.get(i).getFirstTimeStamp());
            myUsageBlockList.add(xBlock);
        }
    }
}


/* MainActivity on 9/7 before deletion

package jontran7.addictiveexperiences;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<UsageBlock> myUsageBlockList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView mListView = (ListView) findViewById(R.id.listView); // Find out what this is and where the R.id.listView comes from

        PopulateUsageBlockList();

        //CreateUsageBlock();

        UsageBlockAdapter adapter = new UsageBlockAdapter(this, R.layout.adapter_view_layout, myUsageBlockList);
        mListView.setAdapter(adapter);
    }

    public void CreateUsageBlock() {

        // Create new RelativeLayout block
        RelativeLayout phoneUsageBlock = new RelativeLayout(this);
        // Defining RelativeLayout block parameters
        int width = 20; // Variable dependent on duration of app usage.
        RelativeLayout.LayoutParams pubParams = new RelativeLayout.LayoutParams(width, 20);
        phoneUsageBlock.setBackgroundColor(0);

        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mainLayout.addView(phoneUsageBlock);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void PopulateUsageBlockList() {
        UsageStatsManager myUsageStatsManager = (UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, -1);
        long startTime = calendar.getTimeInMillis();
        List<UsageStats> myUses = myUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_MONTHLY,startTime,endTime);

        myUsageBlockList = new ArrayList<UsageBlock>();

        assert myUses != null;
        for (int i = 0; i < myUses.size(); i++) {
            UsageBlock xBlock = new UsageBlock(myUses.get(i).getPackageName(), (int)myUses.get(i).getTotalTimeInForeground() / 1000, (int)myUses.get(i).getFirstTimeStamp());
            if (xBlock.getDuration() > 0) { // Only add the app to the list if the duration is > 0 (i.e.; only if it's been used.)
                myUsageBlockList.add(xBlock);
            }
        }
    }
}

 */