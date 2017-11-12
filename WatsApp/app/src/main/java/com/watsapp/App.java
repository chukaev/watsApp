package com.watsapp;

import android.app.Application;

import com.watsapp.sequential.machine3;

/**
 * Created by yuriy on 12/11/2017.
 */

public class App extends Application {
    public static machine3 getMachine() {
        return machine;
    }

    private static machine3 machine;

    @Override
    public void onCreate(){
        super.onCreate();
        machine = new machine3();
    }
}
