package com.example_imagine.terryblade.privatereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Terryblade on 2017/5/10.
 */
public class ChileMenuPageAdapter extends ArrayAdapter<ChildMenuPage> {
    private int resourceId;
    public ChileMenuPageAdapter(Context context, int textViewResourceId, List<ChildMenuPage>obj){
        super(context,textViewResourceId,obj);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChildMenuPage childMenuPage=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.image_page1);
        imageView.setImageResource(childMenuPage.getImageId());
        TextView textView=(TextView)view.findViewById(R.id.text_name1);
        textView.setText(childMenuPage.getName());
        return view;
    }
}
