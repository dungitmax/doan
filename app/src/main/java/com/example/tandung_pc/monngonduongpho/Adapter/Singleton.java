package com.example.tandung_pc.monngonduongpho.Adapter;

/**
 * Created by TANDUNG-PC on 2/10/2018.
 */

public class Singleton {
     public static Singleton instance;

    public Singleton() {

    }

    public static Singleton newInstance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}
