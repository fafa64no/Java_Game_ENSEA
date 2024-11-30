package main.utils.data;

import main.game.level.Level;
import main.game.vehicles.tanks.Tank;
import main.utils.containers.SizedTexture;
import main.utils.containers.SizedTextureArray;
import main.utils.containers.SizedTextureMatrix;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataGen {

/*      ################ - Levels, Tanks and Cameras - ################      */

    public static Level[] genLevels(){
        Level[] levels=new Level[1];
        levels[0]=new Level(new IVec2(Config.defaultMapWidth,Config.defaultMapHeight));
        return levels;
    }

    public static Tank[] genTanks(){
        Tank[] tanks=new Tank[5];
        tanks[0]=new Tank(
                new Vec2(-150,100),
                "panzer_IV_white",
                6,
                new IVec2(38,72),
                0.1,
                0.20,
                new Vec2(15,15),
                5,
                100000
        );
        tanks[1]=new Tank(
                new Vec2(0,100),
                "panzer_IV_gray",
                5,
                new IVec2(38,72),
                0.1,
                0.15,
                new Vec2(15,15),
                30,
                400
        );
        tanks[2]=new Tank(
                new Vec2(150,100),
                "panzer_IV_brown",
                3,
                new IVec2(38,72),
                0.1,
                0.05,
                new Vec2(15,15),
                50,
                800
        );
        tanks[3]=new Tank(
                new Vec2(-75,150),
                "panzer_IV_sand",
                6,
                new IVec2(38,72),
                0.1,
                0.10,
                new Vec2(15,15),
                20,
                320
        );
        tanks[4]=new Tank(
                new Vec2(75,150),
                "panzer_IV_green",
                4,
                new IVec2(38,72),
                0.1,
                0.05,
                new Vec2(15,15),
                25,
                600
        );
        return tanks;
    }

/*      ################ - TileSheets - ################      */

    private static boolean needsToGenerateTileMaps = true;

    private static BufferedImage smallTerrainTiles;
    private static BufferedImage largeTerrainTiles;
    private static BufferedImage smallShells;
    private static BufferedImage largeHudTiles;
    private static BufferedImage smallVfxTiles;
    private static BufferedImage largeVfxTiles;
    private static BufferedImage mediumCubes;
    private static BufferedImage largeCubes;

    private static void checkTileSheets() {
        if(needsToGenerateTileMaps) {
            generateTileSheets();
            needsToGenerateTileMaps = false;
        }
    }

    private static void generateTileSheets() {
        try {
            smallTerrainTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));
            largeTerrainTiles = ImageIO.read(new File("assets/textures/level/largeTiles.png"));
            smallShells = ImageIO.read(new File("assets/textures/projectiles/smallShells.png"));
            largeHudTiles = ImageIO.read(new File("assets/textures/hud/largeTiles.png"));
            smallVfxTiles = ImageIO.read(new File("assets/textures/vfx/smallVFX.png"));
            largeVfxTiles = ImageIO.read(new File("assets/textures/vfx/largeVFX.png"));
            mediumCubes = ImageIO.read(new File("assets/textures/tanks/cubes/mediumCubes.png"));
            largeCubes = ImageIO.read(new File("assets/textures/tanks/cubes/largeCubes.png"));
        } catch (IOException e) {throw new RuntimeException(e);}
    }

/*      ################ - SizedTextures - ################      */

    private static boolean needsToGenerateSizedTextures = true;

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
        if(needsToGenerateSizedTextures) {
            generateAllSizedTextures();
            needsToGenerateSizedTextures = false;
        }
    }

    private static void generateAllSizedTextures() {
        checkTileSheets();

        generateSizedTextures();
        generateSizedTextureArrays();
        generateSizedTextureMatrices();
    }

    private static void generateSizedTextures() {
        blueBarTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                largeHudTiles,
                TextureMapping.blueBarTexturePosition
        );
        yellowBarTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                largeHudTiles,
                TextureMapping.yellowBarTexturePosition
        );
        redBarTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                largeHudTiles,
                TextureMapping.redBarTexturePosition
        );
        whiteBarTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                largeHudTiles,
                TextureMapping.whiteBarTexturePosition
        );

        cubeSpawnTexture = new SizedTexture(
                TextureConfig.smallTileSize,
                smallTerrainTiles,
                TextureMapping.cubeSpawnTexturePosition
        );
        borderTexture = new SizedTexture(
                TextureConfig.smallTileSize,
                smallTerrainTiles,
                TextureMapping.borderTexturePosition
        );

        tankShellTexture = new SizedTexture(
                TextureConfig.smallTileSize,
                smallShells,
                TextureMapping.tankShellTexturePosition
        );
        machineGunBulletTexture = new SizedTexture(
                TextureConfig.smallTileSize,
                smallShells,
                TextureMapping.machineGunBulletTexturePosition
        );

        basicCubeTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.basicCubeTexturePosition
        );
        basicCubeDeadTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.basicCubeDeadTexturePosition
        );

        machineGunCubeTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.machineGunCubeTexturePosition
        );
        machineGunCubeDeadTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.machineGunCubeDeadTexturePosition
        );

        machineGunWheelsCubeTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.machineGunWheelsCubeTexturePosition
        );
        machineGunWheelsCubeDeadTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.machineGunWheelsCubeDeadTexturePosition
        );

        beaconCubeTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.beaconCubeTexturePosition
        );
        beaconCubeDeadTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.beaconCubeDeadTexturePosition
        );

        fighterCubeTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                mediumCubes,
                TextureMapping.fighterCubeTexturePosition
        );
        fighterCubeDeadTexture = new SizedTexture(
                TextureConfig.largeTileSize,
                largeCubes,
                TextureMapping.fighterCubeDeadTexturePosition
        );

        artilleryCubeTexture = new SizedTexture(
                TextureConfig.veryLargeTileSize,
                largeCubes,
                TextureMapping.artilleryCubeTexturePosition
        );
        artilleryCubeDeadTexture = new SizedTexture(
                TextureConfig.veryLargeTileSize,
                largeCubes,
                TextureMapping.artilleryCubeDeadTexturePosition
        );

    }

    private static void generateSizedTextureArrays() {
        piercingMetalVfxTextures = new SizedTextureArray(
                TextureConfig.smallTileSize,
                TextureConfig.piercingMetalVfxFrames,
                smallVfxTiles,
                TextureMapping.piercingMetalVfxTexturePositions
        );
        electricVfxTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.electricVfxFrames,
                largeVfxTiles,
                TextureMapping.electricVfxTexturePositions
        );
        explosionVfxTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.explosionVfxFrames,
                largeVfxTiles,
                TextureMapping.explosionVfxTexturePositions
        );

        artilleryShellTextures = new SizedTextureArray(
                TextureConfig.smallTileSize,
                TextureConfig.artilleryShellFrames,
                smallShells,
                TextureMapping.artilleryShellTexturePositions
        );

        machineGunCubeDeploymentTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunCubeDeployingFrames,
                mediumCubes,
                TextureMapping.machineGunCubeDeploymentTexturePositions
        );
        machineGunCubeRetractionTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunCubeRetractingFrames,
                mediumCubes,
                TextureMapping.machineGunCubeRetractionTexturePositions
        );
        machineGunCubeAttackTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunCubeAttackingFrames,
                mediumCubes,
                TextureMapping.machineGunCubeAttackTexturePositions
        );
        machineGunWheelsCubeDeploymentTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunWheelsCubeDeployingFrames,
                mediumCubes,
                TextureMapping.machineGunWheelsCubeDeploymentTexturePositions
        );
        machineGunWheelsCubeRetractionTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunWheelsCubeRetractingFrames,
                mediumCubes,
                TextureMapping.machineGunWheelsCubeRetractionTexturePositions
        );
        machineGunWheelsCubeAttackTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.machineGunWheelsCubeAttackingFrames,
                mediumCubes,
                TextureMapping.machineGunWheelsCubeAttackTexturePositions
        );
        beaconCubeDeploymentTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.beaconCubeDeployingFrames,
                mediumCubes,
                TextureMapping.beaconCubeDeploymentTexturePositions
        );
        beaconCubeRetractionTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.beaconCubeRetractingFrames,
                mediumCubes,
                TextureMapping.beaconCubeRetractionTexturePositions
        );
        beaconCubeAttackTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.beaconCubeAttackingFrames,
                mediumCubes,
                TextureMapping.beaconCubeAttackTexturePositions
        );
        fighterCubeDeploymentTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.fighterCubeDeployingFrames,
                mediumCubes,
                TextureMapping.fighterCubeDeploymentTexturePositions
        );
        fighterCubeRetractionTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.fighterCubeRetractingFrames,
                mediumCubes,
                TextureMapping.fighterCubeRetractionTexturePositions
        );
        fighterCubeAttackTextures = new SizedTextureArray(
                TextureConfig.largeTileSize,
                TextureConfig.fighterCubeAttackingFrames,
                mediumCubes,
                TextureMapping.fighterCubeAttackTexturePositions
        );
        artilleryCubeDeploymentTextures = new SizedTextureArray(
                TextureConfig.veryLargeTileSize,
                TextureConfig.artilleryCubeDeployingFrames,
                largeCubes,
                TextureMapping.artilleryCubeDeploymentTexturePositions
        );
        artilleryCubeRetractionTextures = new SizedTextureArray(
                TextureConfig.veryLargeTileSize,
                TextureConfig.artilleryCubeRetractingFrames,
                largeCubes,
                TextureMapping.artilleryCubeRetractionTexturePositions
        );
        artilleryCubeAttackTextures = new SizedTextureArray(
                TextureConfig.veryLargeTileSize,
                TextureConfig.artilleryCubeAttackingFrames,
                largeCubes,
                TextureMapping.artilleryCubeAttackTexturePositions
        );
    }

    private static void generateSizedTextureMatrices() {
        grassTextures = new SizedTextureMatrix(
                TextureConfig.smallTileSize,
                new IVec2(TextureConfig.grassColorsNb, TextureConfig.grassVariantsNb),
                smallTerrainTiles,
                TextureMapping.grassTexturePositions
        );
        stoneTextures = new SizedTextureMatrix(
                TextureConfig.smallTileSize,
                new IVec2(TextureConfig.stoneColorsNb, TextureConfig.stoneVariantsNb),
                smallTerrainTiles,
                TextureMapping.stoneTexturePositions
        );
        treeTextures = new SizedTextureMatrix(
                TextureConfig.smallTileSize,
                new IVec2(TextureConfig.treeColorsNb, TextureConfig.treeVariantsNb),
                smallTerrainTiles,
                TextureMapping.treeTexturePositions
        );
        pathTextures = new SizedTextureMatrix(
                TextureConfig.smallTileSize,
                new IVec2(TextureConfig.pathColorsNb, TextureConfig.pathVariantsNb),
                smallTerrainTiles,
                TextureMapping.pathTexturesPositions
        );
        leavesTextures = new SizedTextureMatrix(
                TextureConfig.largeTileSize,
                new IVec2(TextureConfig.leavesColorsNb, TextureConfig.leavesVariantsNb),
                largeTerrainTiles,
                TextureMapping.leavesTexturePositions
        );
    }

/*      ################ - Getters - ################      */

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

    public static SizedTextureArray getArtilleryShellTextures() {
        checkSizedTextures();
        return artilleryShellTextures;
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
