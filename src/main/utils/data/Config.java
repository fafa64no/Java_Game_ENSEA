package main.utils.data;

public class Config {
    public final static int delayBetweenFrames=20;
    public final static double minimumVectorSize=0.000000001;

    public final static int smallTileSize=16;
    public final static int largeTileSize=64;

    public final static int grassColorsNb=8;
    public final static int grassVariantsNb=4;
    public final static int stoneColorsNb=8;
    public final static int stoneVariantsNb=4;
    public final static int treeColorsNb=8;
    public final static int treeVariantsNb=1;
    public final static int pathColorsNb=8;
    public final static int pathVariantsNb=4;
    public final static int leavesColorsNb=1;
    public final static int leavesVariantsNb=4;

    public final static double treeHitBoxSize=0.3;

    public final static double pathSpeedModifier=1.2;

    public final static int mapVerticalWallThickness=18;
    public final static int mapHorizontalWallThickness=32;

    public final static int tilemapColliderRange=4;

    public final static double noiseSeed=6.6260755;
    public final static int noiseSizeTerrainColor=32;
    public final static int noiseSizeTerrainVariant=2;
    public final static int noiseSizeTerrainPath=128;
    public final static int noiseSizeTerrainStones=64;
    public final static int noiseSizeTerrainTrees=1;
    public final static int noiseSizeTerrainTraps=8;

    public final static int maxProjectilesPerProjectileHandler=256;

    public final static double cubeCollisionRange=32;

    public final static long startTime=System.currentTimeMillis();
}
