package main.utils.data;

import main.game.level.Level;
import main.game.characters.vehicles.tank.Tank;
import main.rendering.Camera;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataGen {

/*      ################ - Levels and Tanks - ################      */

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
                1000000
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
                100
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
                200
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
                80
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
                150
        );
        return tanks;
    }

/*      ################ - Textures - ################      */

    private static BufferedImage smallTiles = null;
    private static BufferedImage largeTiles = null;
    private static BufferedImage smallShells = null;
    private static BufferedImage bars = null;

    public static BufferedImage[][] getGrassTextures(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] grassTextures = new BufferedImage[Config.grassColorsNb][Config.grassVariantsNb];
        for (int i = 0; i< Config.grassColorsNb; i++){
            for (int j = 0; j< Config.grassVariantsNb; j++){
                grassTextures[i][j]= smallTiles.getSubimage((i* Config.grassVariantsNb+j)* Config.smallTileSize,0, Config.smallTileSize, Config.smallTileSize);
            }
        }
        return grassTextures;
    }

    public static BufferedImage[][] getStoneTextures(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] stoneTextures = new BufferedImage[Config.stoneColorsNb][Config.stoneVariantsNb];
        for (int i = 0; i< Config.stoneColorsNb; i++){
            for (int j = 0; j< Config.stoneVariantsNb; j++){
                stoneTextures[i][j]= smallTiles.getSubimage((i* Config.stoneVariantsNb+j)* Config.smallTileSize, Config.smallTileSize, Config.smallTileSize, Config.smallTileSize);
            }
        }
        return stoneTextures;
    }

    public static BufferedImage[][] getTreeTextures(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] treeTextures = new BufferedImage[Config.treeColorsNb][Config.treeVariantsNb];
        for (int i = 0; i< Config.treeColorsNb; i++){
            for (int j = 0; j< Config.treeVariantsNb; j++){
                treeTextures[i][j]= smallTiles.getSubimage((i* Config.treeVariantsNb+j)* Config.smallTileSize,2* Config.smallTileSize, Config.smallTileSize, Config.smallTileSize);
            }
        }
        return treeTextures;
    }

    public static BufferedImage[][] getPathTextures(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] pathTextures = new BufferedImage[Config.pathColorsNb][Config.pathVariantsNb];
        for (int i = 0; i< Config.pathColorsNb; i++){
            for (int j = 0; j< Config.pathVariantsNb; j++){
                pathTextures[i][j]= smallTiles.getSubimage((i* Config.pathVariantsNb+j)* Config.smallTileSize,3* Config.smallTileSize, Config.smallTileSize, Config.smallTileSize);
            }
        }
        return pathTextures;
    }

    public static BufferedImage getTrapTexture(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return smallTiles.getSubimage(Config.smallTileSize,4* Config.smallTileSize, Config.smallTileSize, Config.smallTileSize);
    }

    public static BufferedImage getBarrierTexture(){
        if(smallTiles == null) {
            try {
                smallTiles = ImageIO.read(new File("assets/textures/level/smallTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return smallTiles.getSubimage(0,4* Config.smallTileSize, Config.smallTileSize, Config.smallTileSize);
    }

    public static BufferedImage[][] getLeavesTextures(){
        if(largeTiles == null) {
            try {
                largeTiles = ImageIO.read(new File("assets/textures/level/largeTiles.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] leavesTextures = new BufferedImage[Config.leavesColorsNb][Config.leavesVariantsNb];
        for (int i = 0; i< Config.leavesColorsNb; i++){
            for (int j = 0; j< Config.leavesVariantsNb; j++){
                leavesTextures[i][j]= largeTiles.getSubimage((i* Config.leavesVariantsNb+j)* Config.largeTileSize,0, Config.largeTileSize, Config.largeTileSize);
            }
        }
        return leavesTextures;
    }

    public static BufferedImage getTankShellTexture(){
        if(smallShells == null) {
            try {
                smallShells = ImageIO.read(new File("assets/textures/projectiles/smallShells.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return smallShells.getSubimage(0,0, Config.smallTileSize, Config.smallTileSize);
    }

    public static BufferedImage getMachineGunBulletShellTexture(){
        if(smallShells == null) {
            try {
                smallShells = ImageIO.read(new File("assets/textures/projectiles/smallShells.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return smallShells.getSubimage(Config.smallTileSize,0, Config.smallTileSize, Config.smallTileSize);
    }

    public static BufferedImage getBlueBarTexture(){
        if(bars == null) {
            try {
                bars = ImageIO.read(new File("assets/textures/hud/bars.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return bars.getSubimage(0, 0, Config.largeTileSize, Config.largeTileSize);
    }

    public static BufferedImage getYellowBarTexture(){
        if(bars == null) {
            try {
                bars = ImageIO.read(new File("assets/textures/hud/bars.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return bars.getSubimage(0, Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
    }

    public static BufferedImage getRedBarTexture(){
        if(bars == null) {
            try {
                bars = ImageIO.read(new File("assets/textures/hud/bars.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return bars.getSubimage(0, 2*Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
    }

    public static BufferedImage getWhiteBarTexture(){
        if(bars == null) {
            try {
                bars = ImageIO.read(new File("assets/textures/hud/bars.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return bars.getSubimage(0, 3*Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
    }

/*      ################ - VFX - ################      */

    private static BufferedImage smallVfxTiles = null;
    private static BufferedImage largeVfxTiles = null;
    private static BufferedImage[] piercingTextures = null;
    private static BufferedImage[] electricTextures = null;
    private static BufferedImage[] explosionTextures = null;

    public static BufferedImage[] getPiercingTextures(){
        if(smallVfxTiles == null) {
            try {
                smallVfxTiles = ImageIO.read(new File("assets/textures/vfx/smallVFX.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if(piercingTextures == null){
            piercingTextures = new BufferedImage[4];
            piercingTextures[0] = smallVfxTiles.getSubimage(0, 0, Config.smallTileSize, Config.smallTileSize);
            piercingTextures[1] = smallVfxTiles.getSubimage(Config.smallTileSize, 0, Config.smallTileSize, Config.smallTileSize);
            piercingTextures[2] = smallVfxTiles.getSubimage(2*Config.smallTileSize, 0, Config.smallTileSize, Config.smallTileSize);
            piercingTextures[3] = smallVfxTiles.getSubimage(3*Config.smallTileSize, 0, Config.smallTileSize, Config.smallTileSize);
        }
        return piercingTextures;
    }

    public static BufferedImage[] getElectricTextures(){
        if(largeVfxTiles == null) {
            try {
                largeVfxTiles = ImageIO.read(new File("assets/textures/vfx/largeVFX.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if(electricTextures == null){
            electricTextures = new BufferedImage[4];
            electricTextures[0] = largeVfxTiles.getSubimage(0, 0, Config.largeTileSize, Config.largeTileSize);
            electricTextures[1] = largeVfxTiles.getSubimage(Config.largeTileSize, 0, Config.largeTileSize, Config.largeTileSize);
            electricTextures[2] = largeVfxTiles.getSubimage(2*Config.largeTileSize, 0, Config.largeTileSize, Config.largeTileSize);
            electricTextures[3] = largeVfxTiles.getSubimage(3*Config.largeTileSize, 0, Config.largeTileSize, Config.largeTileSize);
        }
        return electricTextures;
    }

    public static BufferedImage[] getExplosionTextures(){
        if(largeVfxTiles == null) {
            try {
                largeVfxTiles = ImageIO.read(new File("assets/textures/vfx/largeVFX.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if(explosionTextures == null){
            explosionTextures = new BufferedImage[4];
            explosionTextures[0] = largeVfxTiles.getSubimage(0, Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
            explosionTextures[1] = largeVfxTiles.getSubimage(Config.largeTileSize, Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
            explosionTextures[2] = largeVfxTiles.getSubimage(2*Config.largeTileSize, Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
            explosionTextures[3] = largeVfxTiles.getSubimage(3*Config.largeTileSize, Config.largeTileSize, Config.largeTileSize, Config.largeTileSize);
        }
        return explosionTextures;
    }

/*      ################ - Cubes - ################      */

    private static BufferedImage mediumCubes = null;
    private static BufferedImage largeCubes = null;

    private static BufferedImage basicCubeTexture = null;
    private static BufferedImage basicCubeDeadTexture = null;
    private static BufferedImage rangedCubeTexture = null;
    private static BufferedImage rangedCubeDeadTexture = null;
    private static BufferedImage[] rangedCubeDeploymentTextures = null;
    private static BufferedImage[] rangedCubeRetractionTextures = null;
    private static BufferedImage[] rangedCubeAttackTextures = null;
    private static BufferedImage movingCubeTexture = null;
    private static BufferedImage movingCubeDeadTexture = null;
    private static BufferedImage[] movingCubeDeploymentTextures = null;
    private static BufferedImage[] movingCubeRetractionTextures = null;
    private static BufferedImage[] movingCubeAttackTextures = null;
    private static BufferedImage artilleryCubeTexture = null;
    private static BufferedImage artilleryCubeDeadTexture = null;
    private static BufferedImage[] artilleryCubeDeploymentTextures = null;
    private static BufferedImage[] artilleryCubeRetractionTextures = null;
    private static BufferedImage[] artilleryCubeAttackTextures = null;

    public static BufferedImage getBasicCubeTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (basicCubeTexture == null)basicCubeTexture=mediumCubes.getSubimage(0,0,Config.largeTileSize,Config.largeTileSize);
        return basicCubeTexture;
    }

    public static BufferedImage getBasicCubeDeadTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (basicCubeDeadTexture == null)basicCubeDeadTexture = mediumCubes.getSubimage(Config.largeTileSize,0,Config.largeTileSize,Config.largeTileSize);
        return basicCubeDeadTexture;
    }

    public static BufferedImage getRangedCubeTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeTexture == null) rangedCubeTexture =mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        return rangedCubeTexture;
    }

    public static BufferedImage getRangedCubeDeadTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeDeadTexture == null) rangedCubeDeadTexture = mediumCubes.getSubimage(7*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        return rangedCubeDeadTexture;
    }

    public static BufferedImage[] getRangedCubeDeploymentTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeDeploymentTextures == null){
            rangedCubeDeploymentTextures=new BufferedImage[6];
            rangedCubeDeploymentTextures[0]=mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[1]=mediumCubes.getSubimage(Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[2]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[3]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[4]=mediumCubes.getSubimage(4*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[5]=mediumCubes.getSubimage(5*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return rangedCubeDeploymentTextures;
    }

    public static BufferedImage[] getRangedCubeRetractionTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeRetractionTextures == null){
            rangedCubeRetractionTextures=new BufferedImage[6];
            rangedCubeRetractionTextures[5]=mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[4]=mediumCubes.getSubimage(Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[3]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[2]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[1]=mediumCubes.getSubimage(4*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[0]=mediumCubes.getSubimage(5*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return rangedCubeRetractionTextures;
    }

    public static BufferedImage[] getRangedCubeAttackTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeAttackTextures == null){
            rangedCubeAttackTextures=new BufferedImage[6];
            rangedCubeAttackTextures[0]=mediumCubes.getSubimage(6*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[1]=mediumCubes.getSubimage(5*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[2]=mediumCubes.getSubimage(6*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[3]=mediumCubes.getSubimage(5*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[4]=mediumCubes.getSubimage(6*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[5]=mediumCubes.getSubimage(5*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return rangedCubeAttackTextures;
    }

    public static BufferedImage getMovingCubeTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (movingCubeTexture == null) movingCubeTexture =mediumCubes.getSubimage(0,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        return movingCubeTexture;
    }

    public static BufferedImage getMovingCubeDeadTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (movingCubeDeadTexture == null) movingCubeDeadTexture = mediumCubes.getSubimage(7*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        return movingCubeDeadTexture;
    }

    public static BufferedImage[] getMovingCubeDeploymentTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (movingCubeDeploymentTextures == null){
            movingCubeDeploymentTextures=new BufferedImage[6];
            movingCubeDeploymentTextures[0]=mediumCubes.getSubimage(0,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeDeploymentTextures[1]=mediumCubes.getSubimage(Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeDeploymentTextures[2]=mediumCubes.getSubimage(2*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeDeploymentTextures[3]=mediumCubes.getSubimage(3*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeDeploymentTextures[4]=mediumCubes.getSubimage(4*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeDeploymentTextures[5]=mediumCubes.getSubimage(5*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return movingCubeDeploymentTextures;
    }

    public static BufferedImage[] getMovingCubeRetractionTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (movingCubeRetractionTextures == null){
            movingCubeRetractionTextures=new BufferedImage[6];
            movingCubeRetractionTextures[5]=mediumCubes.getSubimage(0,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeRetractionTextures[4]=mediumCubes.getSubimage(Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeRetractionTextures[3]=mediumCubes.getSubimage(2*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeRetractionTextures[2]=mediumCubes.getSubimage(3*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeRetractionTextures[1]=mediumCubes.getSubimage(4*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeRetractionTextures[0]=mediumCubes.getSubimage(5*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return movingCubeRetractionTextures;
    }

    public static BufferedImage[] getMovingCubeAttackTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (movingCubeAttackTextures == null){
            movingCubeAttackTextures=new BufferedImage[6];
            movingCubeAttackTextures[0]=mediumCubes.getSubimage(6*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeAttackTextures[1]=mediumCubes.getSubimage(5*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeAttackTextures[2]=mediumCubes.getSubimage(6*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeAttackTextures[3]=mediumCubes.getSubimage(5*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeAttackTextures[4]=mediumCubes.getSubimage(6*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            movingCubeAttackTextures[5]=mediumCubes.getSubimage(5*Config.largeTileSize,2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return movingCubeAttackTextures;
    }

    public static BufferedImage getArtilleryCubeTexture(){
        if(largeCubes == null) {
            try {
                largeCubes = ImageIO.read(new File("assets/textures/characters/cubes/largeCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (artilleryCubeTexture == null){
            artilleryCubeTexture=largeCubes.getSubimage(0, 0,Config.veryLargeTileSize,Config.veryLargeTileSize);
        }
        return artilleryCubeTexture;
    }
    public static BufferedImage getArtilleryCubeDeadTexture(){
        if(largeCubes == null) {
            try {
                largeCubes = ImageIO.read(new File("assets/textures/characters/cubes/largeCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (artilleryCubeDeadTexture == null){
            artilleryCubeDeadTexture=largeCubes.getSubimage(3*Config.veryLargeTileSize,3*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
        }
        return artilleryCubeDeadTexture;
    }

    public static BufferedImage[] getArtilleryCubeDeploymentTextures(){
        if(largeCubes == null) {
            try {
                largeCubes = ImageIO.read(new File("assets/textures/characters/cubes/largeCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (artilleryCubeDeploymentTextures == null){
            artilleryCubeDeploymentTextures=new BufferedImage[8];
            artilleryCubeDeploymentTextures[0]=largeCubes.getSubimage(1*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[1]=largeCubes.getSubimage(2*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[2]=largeCubes.getSubimage(3*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[3]=largeCubes.getSubimage(0*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[4]=largeCubes.getSubimage(1*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[5]=largeCubes.getSubimage(2*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[6]=largeCubes.getSubimage(3*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeDeploymentTextures[7]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
        }
        return artilleryCubeDeploymentTextures;
    }

    public static BufferedImage[] getArtilleryCubeRetractionTextures(){
        if(largeCubes == null) {
            try {
                largeCubes = ImageIO.read(new File("assets/textures/characters/cubes/largeCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (artilleryCubeRetractionTextures == null){
            artilleryCubeRetractionTextures=new BufferedImage[8];
            artilleryCubeRetractionTextures[7]=largeCubes.getSubimage(1*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[6]=largeCubes.getSubimage(2*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[5]=largeCubes.getSubimage(3*Config.veryLargeTileSize,0*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[4]=largeCubes.getSubimage(0*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[3]=largeCubes.getSubimage(1*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[2]=largeCubes.getSubimage(2*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[1]=largeCubes.getSubimage(3*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeRetractionTextures[0]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
        }
        return artilleryCubeRetractionTextures;
    }

    public static BufferedImage[] getArtilleryCubeAttackTextures(){
        if(largeCubes == null) {
            try {
                largeCubes = ImageIO.read(new File("assets/textures/characters/cubes/largeCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (artilleryCubeAttackTextures == null){
            artilleryCubeAttackTextures=new BufferedImage[8];
            artilleryCubeAttackTextures[0]=largeCubes.getSubimage(2*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[1]=largeCubes.getSubimage(3*Config.veryLargeTileSize,1*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[2]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[3]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[4]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[5]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[6]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
            artilleryCubeAttackTextures[7]=largeCubes.getSubimage(0*Config.veryLargeTileSize,2*Config.veryLargeTileSize,Config.veryLargeTileSize,Config.veryLargeTileSize);
        }
        return artilleryCubeAttackTextures;
    }

    /*      ################ - Cameras - ################      */

    public static Camera[] genCameras(){
        Camera[] output=new Camera[5];
        output[0]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        3,
                        3)
        );
        output[1]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        3,
                        3)
        );
        output[2]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        3,
                        3)
        );
        output[3]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        3,
                        3)
        );
        output[4]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        3,
                        3)
        );
        return output;
    }
}
