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
        Tank[] tanks=new Tank[3];
        tanks[0]=new Tank(
                new Vec2(-64,0),
                "./assets/textures/characters/tanks/test/base.png",
                "./assets/textures/characters/tanks/test/turret.png",
                15,
                new IVec2(64,64),
                0.2,
                0.25,
                new Vec2(0.2,0.2),
                new Vec2(1,1)
        );
        tanks[1]=new Tank(
                new Vec2(),
                "./assets/textures/characters/tanks/panzer_IV_gray/base.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/turret.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/deadBase.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/deadTurret.png",
                5,
                new IVec2(38,72),
                0.1,
                0.05,
                new Vec2(15,15)
        );
        tanks[2]=new Tank(
                new Vec2(64,0),
                "./assets/textures/characters/tanks/panzer_IV_brown/base.png",
                "./assets/textures/characters/tanks/panzer_IV_brown/turret.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/deadBase.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/deadTurret.png",
                8,
                new IVec2(38,72),
                0.1,
                0.15,
                new Vec2(15,15)
        );
        return tanks;
    }

/*      ################ - Textures - ################      */

    private static BufferedImage smallTiles = null;
    private static BufferedImage largeTiles = null;
    private static BufferedImage smallShells = null;

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

/*      ################ - Cubes - ################      */

    private static BufferedImage mediumCubes = null;

    private static BufferedImage basicCubeTexture = null;
    private static BufferedImage basicCubeDeadTexture = null;
    private static BufferedImage rangedCubeTexture = null;
    private static BufferedImage rangedCubeDeadTexture = null;
    private static BufferedImage[] rangedCubeDeploymentTextures = null;
    private static BufferedImage[] rangedCubeRetractionTextures = null;
    private static BufferedImage[] rangedCubeAttackTextures = null;

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

    public static BufferedImage getFollowerCubeTextureCubeTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeTexture == null) rangedCubeTexture =mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        return rangedCubeTexture;
    }

    public static BufferedImage getFollowerCubeDeadTextureCubeDeadTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeDeadTexture == null) rangedCubeDeadTexture = mediumCubes.getSubimage(Config.largeTileSize,0,Config.largeTileSize,Config.largeTileSize);
        return rangedCubeDeadTexture;
    }

    public static BufferedImage[] getRangedCubeDeploymentTextures(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        if (rangedCubeDeploymentTextures == null){
            rangedCubeDeploymentTextures=new BufferedImage[4];
            rangedCubeDeploymentTextures[0]=mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[1]=mediumCubes.getSubimage(Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[2]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeDeploymentTextures[3]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
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
            rangedCubeRetractionTextures=new BufferedImage[4];
            rangedCubeRetractionTextures[0]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[1]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[2]=mediumCubes.getSubimage(Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeRetractionTextures[3]=mediumCubes.getSubimage(0,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
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
            rangedCubeAttackTextures=new BufferedImage[4];
            rangedCubeAttackTextures[0]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[1]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[2]=mediumCubes.getSubimage(2*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
            rangedCubeAttackTextures[3]=mediumCubes.getSubimage(3*Config.largeTileSize,Config.largeTileSize,Config.largeTileSize,Config.largeTileSize);
        }
        return rangedCubeAttackTextures;
    }

    /*      ################ - Cameras - ################      */

    public static Camera[] genCameras(){
        Camera[] output=new Camera[3];
        output[0]=new Camera(
                new Vec2(0,0),
                new Vec2(
                        2,
                        2)
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
        return output;
    }
}
