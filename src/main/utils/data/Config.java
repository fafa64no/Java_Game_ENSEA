package main.utils.data;

public class Config {
    public final static int delayBetweenFrames=20;
    public final static double minimumVectorSize=0.000000001;

    public final static double treeHitBoxSize=0.3;

    public final static double pathSpeedModifier=1.2;

    public final static int mapVerticalWallThickness=18;
    public final static int mapHorizontalWallThickness=32;

    public final static int defaultMapWidth=1024;
    public final static int defaultMapHeight=768;
    public final static int spawnWidth=38;
    public final static int spawnHeight=28;

    public final static int tilemapColliderRange=4;

    public final static int preComputedRandomPatternSize=256;

    public final static double noiseSeed=6.6260755;
    public final static int noiseSizeTerrainColor=32;
    public final static int noiseSizeTerrainVariant=2;
    public final static int noiseSizeTerrainPath=128;
    public final static int noiseSizeTerrainStones=64;
    public final static int noiseSizeTerrainTrees=1;
    public final static int noiseSizeTerrainCubes =8;
    public final static int noiseSizeTerrainStructures=8;

    public final static int maxProjectilesPerProjectileHandler=1024;
    public final static int maxVfxPerVfxHandler=1024;

    public final static double cubeCollisionRange=32;
    public final static double cubeDetectionRange=128;
    public final static double cubeAttackRange=80;
    public final static double cubeSquareFollowRange=120000;
    public final static double artillerySquareFollowRange=360000;

    public final static double defaultCubeRequiredAccuracy=0.2;

    public final static double basicCubeHealth=100;
    public final static double gatlingCubeHealth=150;
    public final static double artilleryCubeHealth=500;
    public final static double movingCubeHealth=120;
    public final static double beaconCubeHealth=350;
    public final static double fighterCubeHealth=40;

    public final static double defaultMinCameraZoom=0.1;
    public final static double defaultMaxCameraZoom=5;
    public final static double defaultZoomSpeed=0.3;

    public final static long startTime=System.currentTimeMillis();

    public static boolean currentlyDebugging=false;
}
