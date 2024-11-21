package utils;

public class PseudoRandom {
    public static int getRandomBetween(int minX, int maxX, int a, int b){
        double randFactor=(maxX-minX)*Math.abs(((15*a+6*b+12*a*b+10000)%1000)/1000.0);
        return (int)randFactor+minX;
    }
}
