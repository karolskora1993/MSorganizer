package com.karolskora.msorgranizer.java;

import android.content.Context;

import com.karolskora.msorgranizer.models.Injection;


public class PointFinder {

    public static int[] findPoint(Context activity){
        Injection lastInjection=DatabaseQueries.getLatestInjection(activity);

        int lastArea=lastInjection.getArea();
        int lastPoint=lastInjection.getPoint();

        int area, point;

        if(lastInjection==null)
            return new int[]{1,1};

        if(lastInjection.getArea()==8)
        {
            area=1;
            if(lastInjection.getPoint()==6)
                point=1;
            else
                point=lastPoint+1;
            return  new int[]{area, point};
        }

        area=lastArea+1;
        point=lastPoint;
        return new int[]{area,point};
    }
}
