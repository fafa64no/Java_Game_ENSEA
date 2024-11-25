package main.utils.debug;

import main.utils.data.Config;

public class Debug {
    private static long lastTimeRecorded = Config.startTime;

    public static void printTimeSinceLast(String text){
        if(text!=null)System.out.println("-- Debug | "+text+" : "+(System.currentTimeMillis()-lastTimeRecorded)+" ms");
        lastTimeRecorded = System.currentTimeMillis();
    }

    public static void printTimeSinceLast(){
        printTimeSinceLast("Unnamed");
    }

    public static void printTime(String text){
        System.out.println("-- Debug | "+text+" : "+(System.currentTimeMillis()-Config.startTime)+" ms");
    }

    public static void printTime(){
        printTime("Unnamed");
    }
}
