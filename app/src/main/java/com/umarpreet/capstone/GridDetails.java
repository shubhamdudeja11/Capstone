package com.umarpreet.capstone;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by umarpreet on 15/4/18.
 */

public class GridDetails {
    ArrayList<LatLng> markPoints;
    LatLng takeoff;
    int xOverlap;
    int yOverlap;
    int noofmarkers;
    public GridDetails(ArrayList<LatLng> markers, LatLng toff,int xOver,int yOver)
    {
        markPoints=markers;
        noofmarkers=markers.size();
        takeoff=toff;
        xOverlap=xOver;
        yOverlap=yOver;
    }
    public double ConvertToDistance(LatLng p1,LatLng p2)
    {
        double R=6378137;
        double dLat=Math.toRadians(p1.latitude-p2.latitude);
        double dLong=Math.toRadians(p1.longitude-p2.longitude);
        double a=Math.sin(dLat/2)*Math.sin(dLat/2)+Math.cos(Math.toRadians(p1.latitude)) * Math.cos(Math.toRadians(p2.latitude)) *
                Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }
    public void PointConversion()
    {
        double[][] matrix =new double[noofmarkers-2][2];
        double refdist=ConvertToDistance(markPoints.get(0),markPoints.get(1));
        int count=0;
        for(int i=2;i<noofmarkers;i++)
        {
            double fromorigin=ConvertToDistance(markPoints.get(0),markPoints.get(i));
            double fromref=ConvertToDistance(markPoints.get(1),markPoints.get(i));
            if(fromorigin>fromref)
            {
                double horidif=(refdist*fromref)/(fromorigin-fromref);
                double horizontal=refdist+horidif;
                double vertical=Math.sqrt(Math.pow(fromref,2)+Math.pow(horidif,2));
                matrix[count][0]=horizontal;
                matrix[count][1]=vertical;
                count++;
            }
            else {
                double horidif = (refdist * fromorigin) / (fromref - fromorigin);
                double horizontal = refdist + horidif;
                double vertical = Math.sqrt(Math.pow(fromorigin, 2) + Math.pow(horidif, 2));
                matrix[count][0] = horizontal;
                matrix[count][1] = vertical;
                count++;
            }
        }
        for(int i=0;i<noofmarkers-2;i++)
        {
            for (int j=0;j<2;j++)
            {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
    
}


