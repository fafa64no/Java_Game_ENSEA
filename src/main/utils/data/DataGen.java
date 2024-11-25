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
        Level[] levels=new Level[5];
        levels[0]=new Level(new IVec2(146,74),"assets/data/level4.txt");
        levels[1]=new Level(new IVec2(768,512));
        levels[2]=new Level(new IVec2(16,9),"assets/data/level1.txt");
        levels[3]=new Level(new IVec2(16,9),"assets/data/level2.txt");
        levels[4]=new Level(new IVec2(16,9),"assets/data/level3.txt");
        return levels;
    }

    public static Tank[] genTanks(){
        Tank[] tanks=new Tank[3];
        tanks[0]=new Tank(
                new Vec2(),
                "./assets/textures/characters/tanks/test/base.png",
                "./assets/textures/characters/tanks/test/turret.png",
                15,
                new IVec2(64,64),
                1,
                0.2,
                0.25,
                new Vec2(0.2,0.2),
                new Vec2(1,1)
        );
        tanks[1]=new Tank(
                new Vec2(),
                "./assets/textures/characters/tanks/panzer_IV_gray/base.png",
                "./assets/textures/characters/tanks/panzer_IV_gray/turret.png",
                5,
                new IVec2(38,72),
                1,
                0.1,
                0.05,
                new Vec2(15,15)
        );
        tanks[2]=new Tank(
                new Vec2(),
                "./assets/textures/characters/tanks/panzer_IV_brown/base.png",
                "./assets/textures/characters/tanks/panzer_IV_brown/turret.png",
                6,
                new IVec2(38,72),
                1,
                0.1,
                0.05,
                15
        );
        return tanks;
    }

/*      ################ - Textures - ################      */

    private static BufferedImage smallTiles = null;
    private static BufferedImage largeTiles = null;
    private static BufferedImage smallShells = null;
    private static BufferedImage mediumCubes = null;

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

    public static BufferedImage getBasicCubeTexture(){
        if(mediumCubes == null) {
            try {
                mediumCubes = ImageIO.read(new File("assets/textures/characters/cubes/mediumCubes.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        return mediumCubes.getSubimage(0,0,Config.largeTileSize,Config.largeTileSize);
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
