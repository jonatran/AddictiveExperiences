package jontran7.addictiveexperiences;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jontran7.addictiveexperiences.MainActivity;
import jontran7.addictiveexperiences.R;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

    private List<String> horizontalList;
    private boolean checked;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);

        }
    }


    public HorizontalAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item_view, parent, false);

        final CheckBox btnCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox_button);
        final TextView txtView = (TextView)itemView.findViewById( R.id.txtView );
        btnCheckBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = btnCheckBox.isChecked();
                if (checked) {
                    txtView.setTextColor( Color.RED  );

                } else {
                    txtView.setTextColor( Color.GRAY );
                }
            }
        } );


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtView.setText(horizontalList.get(position));

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HorizontalAdapter.this, holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}