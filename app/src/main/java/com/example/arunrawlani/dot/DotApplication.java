package com.example.arunrawlani.dot;

import android.app.Application;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;


/**
 * Created by Arun Rawlani on 18/06/2015.
 */
public class DotApplication extends Application {


    @Override
    public void onCreate(){
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "H8vrAHBzdiNmSx7JZCEO57SSDUSzkucoDNKRUOXs", "fyPSdL6O2xsodCpilecVjkPUxQNGPmh6qe9tf9z0");
    }
}
