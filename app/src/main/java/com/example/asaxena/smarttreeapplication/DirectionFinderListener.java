package com.example.asaxena.smarttreeapplication;
import java.util.List;

/**
 * Created by asaxena on 9/18/2017.
 */

//interface declared to call the functions from DirectionFinderListener
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
