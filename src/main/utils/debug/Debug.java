package main.utils.debug;

import main.utils.data.Config;

public class Debug {
    private static long lastTimeRecorded = Config.startTime;

    public static void printTimeSinceLast(String text){
        System.out.println("-- Debug | "+text+" : "+(System.currentTimeMillis()-lastTimeRecorded)+" ms");
        lastTimeRecorded = System.currentTimeMillis();
    }

    public static void printTimeSinceLast(){
        System.out.println("-- Debug | Unnamed : "+(System.currentTimeMillis()-lastTimeRecorded)+" ms");
        lastTimeRecorded = System.currentTimeMillis();
    }

    public static void printTime(String text){
        System.out.println("-- Debug | "+text+" : "+(System.currentTimeMillis()-Config.startTime)+" ms");
    }

    public static void printTime(){
        System.out.println("-- Debug | Unnamed : "+(System.currentTimeMillis()-Config.startTime)+" ms");
    }
}
