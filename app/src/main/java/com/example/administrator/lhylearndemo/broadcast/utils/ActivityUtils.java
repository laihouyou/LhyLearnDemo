package com.example.administrator.lhylearndemo.broadcast.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
    private static List<Activity> activityList=new ArrayList<>();

    public static void  AddActivity(Activity activity){
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    public  static void RemoveActivity(Activity activity){
        if (activityList.contains(activity)){
            activityList.remove(activity);
        }
    }

    public static void  RemoverAllActivity(){
        if (activityList.size()>0){
            for (int i = 0; i < activityList.size(); i++) {
                if (!activityList.get(i).isFinishing()){
                    activityList.get(i).finish();
                }
            }
        }
    }
}
