package main.utils.noise;

import main.utils.data.Config;

public class PseudoRandom {
    private static final NoiseGenerator noiseGenerator=new NoiseGenerator(Config.noiseSeed);
    public static int getRandomBetween(int minX, int maxX, double a, double b, int size){
        double randFactor= 0.5+(noiseGenerator.noise(a,b,0,size)/2);
        return Math.clamp(Math.round(randFactor*(maxX-minX))+minX,minX,maxX);
    }

    public static boolean isRandomAbove(double min,double a,double b,int size){
        return (noiseGenerator.noise(a,b,0,size)>min);
    }

    public static boolean isRandomBetween(double min,double max,double a,double b,int size){
        double randFactor=noiseGenerator.noise(a,b,0,size);
        return (randFactor>min&&randFactor<max);
    }
}
