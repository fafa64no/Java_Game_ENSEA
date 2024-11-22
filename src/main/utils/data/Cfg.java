package main.utils.data;

public class Cfg {
    private final static int delayBetweenFrames=20;

    private final static int tileSize=16;
    private final static int grassColorsNb=4;
    private final static int grassVariantsNb=8;
    private final static int stoneColorsNb=4;
    private final static int stoneVariantsNb=8;

    private final static double noiseSeed=1.23456789;
    private final static int noiseSizeTerrainColor=512;
    private final static int noiseSizeTerrainVariant=8;

    public static int getDelayBetweenFrames() {return delayBetweenFrames;}

    public static int getTileSize() {
        return tileSize;
    }
    public static int getGrassColorsNb() {return grassColorsNb;}
    public static int getGrassVariantsNb() {return grassVariantsNb;}
    public static int getStoneColorsNb() {return stoneColorsNb;}
    public static int getStoneVariantsNb() {return stoneVariantsNb;}

    public static double getNoiseSeed(){return noiseSeed;}
    public static int getNoiseSizeTerrainColor() {return noiseSizeTerrainColor;}
    public static int getNoiseSizeTerrainVariant() {return noiseSizeTerrainVariant;}
}
