package main.utils.data.datagen;

import main.utils.containers.SizedTexture;
import main.utils.containers.SizedTextureArray;
import main.utils.containers.SizedTextureMatrix;
import main.utils.data.TextureMapping;

public class TextureGen {
    private static boolean didTexturesGetGenerated = false;

    private static SizedTexture blueBarTexture;
    private static SizedTexture yellowBarTexture;
    private static SizedTexture redBarTexture;
    private static SizedTexture whiteBarTexture;

    private static SizedTexture cubeSpawnTexture;
    private static SizedTexture borderTexture;

    private static SizedTexture tankShellTexture;
    private static SizedTexture machineGunBulletTexture;

    private static SizedTexture basicCubeTexture;
    private static SizedTexture basicCubeDeadTexture;
    private static SizedTexture machineGunCubeTexture;
    private static SizedTexture machineGunCubeDeadTexture;
    private static SizedTexture machineGunWheelsCubeTexture;
    private static SizedTexture machineGunWheelsCubeDeadTexture;
    private static SizedTexture beaconCubeTexture;
    private static SizedTexture beaconCubeDeadTexture;
    private static SizedTexture fighterCubeTexture;
    private static SizedTexture fighterCubeDeadTexture;
    private static SizedTexture artilleryCubeTexture;
    private static SizedTexture artilleryCubeDeadTexture;

    private static SizedTextureArray piercingMetalVfxTextures;
    private static SizedTextureArray electricVfxTextures;
    private static SizedTextureArray explosionVfxTextures;

    private static SizedTextureArray artilleryShellTextures;

    private static SizedTextureArray machineGunCubeDeploymentTextures;
    private static SizedTextureArray machineGunCubeRetractionTextures;
    private static SizedTextureArray machineGunCubeAttackTextures;
    private static SizedTextureArray machineGunWheelsCubeDeploymentTextures;
    private static SizedTextureArray machineGunWheelsCubeRetractionTextures;
    private static SizedTextureArray machineGunWheelsCubeAttackTextures;
    private static SizedTextureArray beaconCubeDeploymentTextures;
    private static SizedTextureArray beaconCubeRetractionTextures;
    private static SizedTextureArray beaconCubeAttackTextures;
    private static SizedTextureArray fighterCubeDeploymentTextures;
    private static SizedTextureArray fighterCubeRetractionTextures;
    private static SizedTextureArray fighterCubeAttackTextures;
    private static SizedTextureArray artilleryCubeDeploymentTextures;
    private static SizedTextureArray artilleryCubeRetractionTextures;
    private static SizedTextureArray artilleryCubeAttackTextures;

    private static SizedTextureMatrix grassTextures;
    private static SizedTextureMatrix stoneTextures;
    private static SizedTextureMatrix treeTextures;
    private static SizedTextureMatrix pathTextures;
    private static SizedTextureMatrix leavesTextures;

    private static void checkSizedTextures() {
        if(didTexturesGetGenerated) return;
        generateAllSizedTextures();
        didTexturesGetGenerated = true;
    }

    private static void generateAllSizedTextures() {
        generateSizedTextures();
        generateSizedTextureArrays();
        generateSizedTextureMatrices();
    }

    private static void generateSizedTextures() {
        blueBarTexture      = new SizedTexture(TileMapGen.getLargeHudTiles(), TextureMapping.blueBarTexturePosition);
        yellowBarTexture    = new SizedTexture(TileMapGen.getLargeHudTiles(), TextureMapping.yellowBarTexturePosition);
        redBarTexture       = new SizedTexture(TileMapGen.getLargeHudTiles(), TextureMapping.redBarTexturePosition);
        whiteBarTexture     = new SizedTexture(TileMapGen.getLargeHudTiles(), TextureMapping.whiteBarTexturePosition);

        cubeSpawnTexture    = new SizedTexture(TileMapGen.getSmallTerrainTiles(), TextureMapping.cubeSpawnTexturePosition);
        borderTexture       = new SizedTexture(TileMapGen.getSmallTerrainTiles(), TextureMapping.borderTexturePosition);

        tankShellTexture        = new SizedTexture(TileMapGen.getSmallShells(), TextureMapping.tankShellTexturePosition);
        machineGunBulletTexture = new SizedTexture(TileMapGen.getSmallShells(), TextureMapping.machineGunBulletTexturePosition);

        basicCubeTexture                = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.basicCubeTexturePosition);
        basicCubeDeadTexture            = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.basicCubeDeadTexturePosition);
        machineGunCubeTexture           = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.machineGunCubeTexturePosition);
        machineGunCubeDeadTexture       = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.machineGunCubeDeadTexturePosition);
        machineGunWheelsCubeTexture     = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.machineGunWheelsCubeTexturePosition);
        machineGunWheelsCubeDeadTexture = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.machineGunWheelsCubeDeadTexturePosition);
        beaconCubeTexture               = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.beaconCubeTexturePosition);
        beaconCubeDeadTexture           = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.beaconCubeDeadTexturePosition);
        fighterCubeTexture              = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.fighterCubeTexturePosition);
        fighterCubeDeadTexture          = new SizedTexture(TileMapGen.getMediumCubes(), TextureMapping.fighterCubeDeadTexturePosition);

        artilleryCubeTexture        = new SizedTexture(TileMapGen.getLargeCubes(), TextureMapping.artilleryCubeTexturePosition);
        artilleryCubeDeadTexture    = new SizedTexture(TileMapGen.getLargeCubes(), TextureMapping.artilleryCubeDeadTexturePosition);
    }

    private static void generateSizedTextureArrays() {
        piercingMetalVfxTextures    = new SizedTextureArray(TileMapGen.getSmallVfxTiles(), TextureMapping.piercingMetalVfxTexturePositions);
        electricVfxTextures         = new SizedTextureArray(TileMapGen.getLargeVfxTiles(), TextureMapping.electricVfxTexturePositions);
        explosionVfxTextures        = new SizedTextureArray(TileMapGen.getLargeVfxTiles(), TextureMapping.explosionVfxTexturePositions);

        artilleryShellTextures = new SizedTextureArray(TileMapGen.getSmallShells(), TextureMapping.artilleryShellTexturePositions);

        machineGunCubeDeploymentTextures        = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunCubeDeploymentTexturePositions);
        machineGunCubeRetractionTextures        = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunCubeRetractionTexturePositions);
        machineGunCubeAttackTextures            = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunCubeAttackTexturePositions);
        machineGunWheelsCubeDeploymentTextures  = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunWheelsCubeDeploymentTexturePositions);
        machineGunWheelsCubeRetractionTextures  = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunWheelsCubeRetractionTexturePositions);
        machineGunWheelsCubeAttackTextures      = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.machineGunWheelsCubeAttackTexturePositions);
        beaconCubeDeploymentTextures            = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.beaconCubeDeploymentTexturePositions);
        beaconCubeRetractionTextures            = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.beaconCubeRetractionTexturePositions);
        beaconCubeAttackTextures                = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.beaconCubeAttackTexturePositions);
        fighterCubeDeploymentTextures           = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.fighterCubeDeploymentTexturePositions);
        fighterCubeRetractionTextures           = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.fighterCubeRetractionTexturePositions);
        fighterCubeAttackTextures               = new SizedTextureArray(TileMapGen.getMediumCubes(), TextureMapping.fighterCubeAttackTexturePositions);

        artilleryCubeDeploymentTextures     = new SizedTextureArray(TileMapGen.getLargeCubes(), TextureMapping.artilleryCubeDeploymentTexturePositions);
        artilleryCubeRetractionTextures     = new SizedTextureArray(TileMapGen.getLargeCubes(), TextureMapping.artilleryCubeRetractionTexturePositions);
        artilleryCubeAttackTextures         = new SizedTextureArray(TileMapGen.getLargeCubes(), TextureMapping.artilleryCubeAttackTexturePositions);
    }

    private static void generateSizedTextureMatrices() {
        grassTextures  = new SizedTextureMatrix(TileMapGen.getSmallTerrainTiles(), TextureMapping.grassTexturePositions);
        stoneTextures  = new SizedTextureMatrix(TileMapGen.getSmallTerrainTiles(), TextureMapping.stoneTexturePositions);
        treeTextures   = new SizedTextureMatrix(TileMapGen.getSmallTerrainTiles(), TextureMapping.treeTexturePositions);
        pathTextures   = new SizedTextureMatrix(TileMapGen.getSmallTerrainTiles(), TextureMapping.pathTexturesPositions);
        leavesTextures = new SizedTextureMatrix(TileMapGen.getLargeTerrainTiles(), TextureMapping.leavesTexturePositions);
    }

    /*      #############################################      */
    /*                                                         */
    /*      ###         -      Getters      -         ###      */
    /*                                                         */
    /*      #############################################      */

    public static SizedTexture getBlueBarTexture() {
        checkSizedTextures();
        return blueBarTexture;
    }

    public static SizedTexture getYellowBarTexture() {
        checkSizedTextures();
        return yellowBarTexture;
    }

    public static SizedTexture getRedBarTexture() {
        checkSizedTextures();
        return redBarTexture;
    }

    public static SizedTexture getWhiteBarTexture() {
        checkSizedTextures();
        return whiteBarTexture;
    }

    public static SizedTexture getCubeSpawnTexture() {
        checkSizedTextures();
        return cubeSpawnTexture;
    }

    public static SizedTexture getBorderTexture() {
        checkSizedTextures();
        return borderTexture;
    }

    public static SizedTexture getTankShellTexture() {
        checkSizedTextures();
        return tankShellTexture;
    }

    public static SizedTexture getMachineGunBulletTexture() {
        checkSizedTextures();
        return machineGunBulletTexture;
    }

    public static SizedTexture getBasicCubeTexture() {
        checkSizedTextures();
        return basicCubeTexture;
    }

    public static SizedTexture getBasicCubeDeadTexture() {
        checkSizedTextures();
        return basicCubeDeadTexture;
    }

    public static SizedTexture getMachineGunCubeTexture() {
        checkSizedTextures();
        return machineGunCubeTexture;
    }

    public static SizedTexture getMachineGunCubeDeadTexture() {
        checkSizedTextures();
        return machineGunCubeDeadTexture;
    }

    public static SizedTexture getMachineGunWheelsCubeTexture() {
        checkSizedTextures();
        return machineGunWheelsCubeTexture;
    }

    public static SizedTexture getMachineGunWheelsCubeDeadTexture() {
        checkSizedTextures();
        return machineGunWheelsCubeDeadTexture;
    }

    public static SizedTexture getBeaconCubeTexture() {
        checkSizedTextures();
        return beaconCubeTexture;
    }

    public static SizedTexture getBeaconCubeDeadTexture() {
        checkSizedTextures();
        return beaconCubeDeadTexture;
    }

    public static SizedTexture getFighterCubeTexture() {
        checkSizedTextures();
        return fighterCubeTexture;
    }

    public static SizedTexture getFighterCubeDeadTexture() {
        checkSizedTextures();
        return fighterCubeDeadTexture;
    }

    public static SizedTexture getArtilleryCubeTexture() {
        checkSizedTextures();
        return artilleryCubeTexture;
    }

    public static SizedTexture getArtilleryCubeDeadTexture() {
        checkSizedTextures();
        return artilleryCubeDeadTexture;
    }

    public static SizedTextureArray getPiercingMetalVfxTextures() {
        checkSizedTextures();
        return piercingMetalVfxTextures;
    }

    public static SizedTextureArray getElectricVfxTextures() {
        checkSizedTextures();
        return electricVfxTextures;
    }

    public static SizedTextureArray getExplosionVfxTextures() {
        checkSizedTextures();
        return explosionVfxTextures;
    }

    public static SizedTextureArray getArtilleryShellTextures() {
        checkSizedTextures();
        return artilleryShellTextures;
    }

    public static SizedTextureArray getMachineGunCubeDeploymentTextures() {
        checkSizedTextures();
        return machineGunCubeDeploymentTextures;
    }

    public static SizedTextureArray getMachineGunCubeRetractionTextures() {
        checkSizedTextures();
        return machineGunCubeRetractionTextures;
    }

    public static SizedTextureArray getMachineGunCubeAttackTextures() {
        checkSizedTextures();
        return machineGunCubeAttackTextures;
    }

    public static SizedTextureArray getMachineGunWheelsCubeDeploymentTextures() {
        checkSizedTextures();
        return machineGunWheelsCubeDeploymentTextures;
    }

    public static SizedTextureArray getMachineGunWheelsCubeRetractionTextures() {
        checkSizedTextures();
        return machineGunWheelsCubeRetractionTextures;
    }

    public static SizedTextureArray getMachineGunWheelsCubeAttackTextures() {
        checkSizedTextures();
        return machineGunWheelsCubeAttackTextures;
    }

    public static SizedTextureArray getBeaconCubeDeploymentTextures() {
        checkSizedTextures();
        return beaconCubeDeploymentTextures;
    }

    public static SizedTextureArray getBeaconCubeRetractionTextures() {
        checkSizedTextures();
        return beaconCubeRetractionTextures;
    }

    public static SizedTextureArray getBeaconCubeAttackTextures() {
        checkSizedTextures();
        return beaconCubeAttackTextures;
    }

    public static SizedTextureArray getFighterCubeDeploymentTextures() {
        checkSizedTextures();
        return fighterCubeDeploymentTextures;
    }

    public static SizedTextureArray getFighterCubeRetractionTextures() {
        checkSizedTextures();
        return fighterCubeRetractionTextures;
    }

    public static SizedTextureArray getFighterCubeAttackTextures() {
        checkSizedTextures();
        return fighterCubeAttackTextures;
    }

    public static SizedTextureArray getArtilleryCubeDeploymentTextures() {
        checkSizedTextures();
        return artilleryCubeDeploymentTextures;
    }

    public static SizedTextureArray getArtilleryCubeRetractionTextures() {
        checkSizedTextures();
        return artilleryCubeRetractionTextures;
    }

    public static SizedTextureArray getArtilleryCubeAttackTextures() {
        checkSizedTextures();
        return artilleryCubeAttackTextures;
    }

    public static SizedTextureMatrix getGrassTextures() {
        checkSizedTextures();
        return grassTextures;
    }

    public static SizedTextureMatrix getStoneTextures() {
        checkSizedTextures();
        return stoneTextures;
    }

    public static SizedTextureMatrix getTreeTextures() {
        checkSizedTextures();
        return treeTextures;
    }

    public static SizedTextureMatrix getPathTextures() {
        checkSizedTextures();
        return pathTextures;
    }

    public static SizedTextureMatrix getLeavesTextures() {
        checkSizedTextures();
        return leavesTextures;
    }
}
