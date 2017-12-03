package com.example.asaxena.smarttreeapplication;

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

/**
 * Created by asaxena on 9/18/2017.
 */

public class Route {

    public Distance distance;
    public TimeDuration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;
    public List<LatLng> points;
}
