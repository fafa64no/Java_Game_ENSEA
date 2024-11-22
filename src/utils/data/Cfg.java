package utils.data;

public class Cfg {
    private final static int tileSize=16;
    private final static int delayBetweenFrames=20;
    private final static double noiseSeed=1.23456789;

    public static int getTileSize() {
        return tileSize;
    }

    public static int getDelayBetweenFrames() {
        return delayBetweenFrames;
    }

    public static double getNoiseSeed(){return noiseSeed;}
}
