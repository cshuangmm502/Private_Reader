package com.example_imagine.terryblade.privatereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Terryblade on 2017/4/29.
 */
public class NewsAdapter extends ArrayAdapter<News.NewslistBean> {
    private int resourceId;
    public NewsAdapter(Context context, int textViewResourceId, List<News.NewslistBean> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        News.NewslistBean newslistBean=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.image);
        TextView textView1=(TextView)view.findViewById(R.id.title);
        TextView textView2=(TextView)view.findViewById(R.id.description);
        imageView.setImageResource(newslistBean.getImageId());
        textView1.setText(newslistBean.getTitle());
        textView2.setText(newslistBean.getDescription());

        return view;
    }
}
