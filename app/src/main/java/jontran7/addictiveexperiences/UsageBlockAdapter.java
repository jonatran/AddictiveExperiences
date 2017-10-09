package jontran7.addictiveexperiences;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 8/23/2017.
 */

public class UsageBlockAdapter extends ArrayAdapter<UsageBlock> {

    private static final String TAG = "UsageBlockAdapter";

    private Context mContext;
    int mResource;

    public UsageBlockAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<UsageBlock> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int duration = getItem(position).getDuration();
        String packageName = getItem(position).getPackageName();
        int lastUsed = getItem(position).getLastUsed();

        UsageBlock block = new UsageBlock(packageName, duration, lastUsed);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPackageName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvLastUsed = (TextView) convertView.findViewById(R.id.textView3);

        tvPackageName.setText(packageName);
        tvDuration.setText(Integer.toString(duration));
        tvLastUsed.setText(Integer.toString(lastUsed));

        return convertView;
    }
}

/* Orignal UsageBlockAdapter
package jontran7.addictiveexperiences;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 8/23/2017.


public class UsageBlockAdapter extends ArrayAdapter<UsageBlock> {

    private static final String TAG = "UsageBlockAdapter";

    private Context mContext;
    int mResource;

    public UsageBlockAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<UsageBlock> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int duration = getItem(position).getDuration();
        String packageName = getItem(position).getPackageName();
        int lastUsed = getItem(position).getLastUsed();

        UsageBlock block = new UsageBlock(packageName, duration, lastUsed);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPackageName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDuration = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvLastUsed = (TextView) convertView.findViewById(R.id.textView3);

        tvPackageName.setText(packageName);
        tvDuration.setText(Integer.toString(duration));
        tvLastUsed.setText(Integer.toString(lastUsed));

        return convertView;
    }
}
*/

