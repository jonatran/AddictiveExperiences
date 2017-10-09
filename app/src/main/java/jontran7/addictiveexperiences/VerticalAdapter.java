package jontran7.addictiveexperiences;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class VerticalAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private ArrayList<UsageBlock> myUsageBlocks;

    private static final String TAG = "VerticalAdapter";

    public VerticalAdapter(ArrayList<String> list, Context context, ArrayList<UsageBlock> usageBlocks) {
        this.list = list;
        this.context = context;
        this.myUsageBlocks = usageBlocks;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int count = 0;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.vertical_item_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        // have to do something where each single usageblock is not linked to a single row of the vertical list.

        CreateUsageBlock(position, view);

        /*
        if (count < myUsageBlocks.size()) {
            CreateUsageBlock( position, view );
            //count++;
        }
        */
        //End

        return view;
    }

    private void CreateUsageBlock(int position, View view) {
        // Find parent
        RelativeLayout relativeLayoutParent = (RelativeLayout) view.findViewById( R.id.dummyParent );
        RelativeLayout phoneUsageBlock = new RelativeLayout(context);


        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        int width = (myUsageBlocks.get(position).getDuration());
        //int width = 20 * (position + 1);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(width, 50);
        rlp.addRule( RelativeLayout.ALIGN_LEFT );


        TextView tv = new TextView(context);

        //addContentView(phoneUsageBlock,pubParams );
        relativeLayoutParent.addView(phoneUsageBlock, rlp);

        phoneUsageBlock.setBackgroundColor( Color.GRAY );

        tv.setText(myUsageBlocks.get( position ).getPackageName() );
        phoneUsageBlock.addView( tv );

        ((Button)view.findViewById(R.id.delete_btn)).setText( "hello!" ); // lol debugging
    }
}