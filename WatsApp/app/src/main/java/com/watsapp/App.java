package com.watsapp;

import android.app.Application;

import com.watsapp.sequential.Machine;
import com.watsapp.sequential.machine3;

import java.util.ArrayList;

/**
 * Created by yuriy on 12/11/2017.
 */

public class App extends Application {

    public static Machine machine;

    public static ArrayList<Integer> users;

    @Override
    public void onCreate(){
        super.onCreate();
        machine = new Machine();
        users = new ArrayList<>();
    }
}
