package utils.noise;

import utils.data.Cfg;

public class PseudoRandom {
    private static final NoiseGenerator noiseGenerator=new NoiseGenerator(Cfg.getNoiseSeed());
    public static int getRandomBetween(int minX, int maxX, int a, int b){
        double randFactor= 0.5+(noiseGenerator.noise(a,b,0,200)/2);
        return Math.clamp(Math.round(randFactor*(maxX-minX))+minX,minX,maxX);
    }
}
