package com.karolskora.msorgranizer.java;

import android.content.Context;
import android.util.Log;

import com.karolskora.msorgranizer.models.Injection;


public class PointFinder {

    public static int[] findPoint(Context activity){
        Injection lastInjection=DatabaseQueries.getLatestInjection(activity);

        int area, point;
        if(lastInjection==null) {
            area = 1;
            point = 1;
        }
        else
        {
            int lastArea=lastInjection.getArea();
            int lastPoint=lastInjection.getPoint();
            if(lastArea==8) {
                area=1;
                if(lastPoint==6)
                    point=1;
                else
                    point=lastPoint+1;
            }
            else {
            area = lastArea + 1;
            point = lastPoint;
            }
        }

        Log.d(PointFinder.class.toString(), "Miejsce zastrzyku: obszar: " +area+" punkt w obszarze: "+point);
        return new int[]{area,point};
    }
}
