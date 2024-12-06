package main.utils.noise;

import main.utils.data.Config;

import java.util.Random;

public class PseudoRandom {
    private static final NoiseGenerator noiseGenerator = new NoiseGenerator(Config.noiseSeed);

    private static double[] preComputedRandomPattern = null;
    private static int preComputedRandomPatternPointer = 0;

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
    public static int getNotReallyRandomBelow(int maxValue, int a, int b){
        return (a*7 + b+a*b*b) % (maxValue);
    }

    public static double getRandomDouble(double a,double b,int size){
        return noiseGenerator.noise(a,b,0,size);
    }

    public static double getRandomSpread(){
        if(preComputedRandomPattern == null){
            preComputedRandomPattern = new double[Config.preComputedRandomPatternSize];
            for(int i = 0; i < Config.preComputedRandomPatternSize; i++){
                Random random = new Random();
                preComputedRandomPattern[i] = random.nextDouble() - 0.5;
            }
        }

        preComputedRandomPatternPointer = (preComputedRandomPatternPointer +1)%Config.preComputedRandomPatternSize;
        return preComputedRandomPattern[preComputedRandomPatternPointer];
    }
}
