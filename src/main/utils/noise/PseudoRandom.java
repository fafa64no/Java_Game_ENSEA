package main.utils.noise;

import main.utils.data.Cfg;

public class PseudoRandom {
    private static final NoiseGenerator noiseGenerator=new NoiseGenerator(Cfg.noiseSeed);
    public static int getRandomBetween(int minX, int maxX, double a, double b, int size){
        double randFactor= 0.5+(noiseGenerator.noise(a,b,0,size)/2);
        return Math.clamp(Math.round(randFactor*(maxX-minX))+minX,minX,maxX);
    }
}
