package com.example_imagine.terryblade.privatereader;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terryblade on 2017/4/9.
 */
public class ActivityCollector  {
    public static List<Activity> activities=new ArrayList<>();

    public static void AddActivity(Activity activity){activities.add(activity);}

    public static void RemoveActivity(Activity activity){activities.remove(activity);}

    public static void FinishAll(){
        for(Activity activity:activities){
            activity.finish();
        }
    }
}
