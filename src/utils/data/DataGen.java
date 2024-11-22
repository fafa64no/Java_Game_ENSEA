package utils.data;

import game.level.Level;
import game.characters.vehicles.tank.Tank;
import physics.BoxCollider;
import utils.vectors.IVec2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataGen {
    public static Level[] genLevels(){
        Level[] levels=new Level[1];
        //levels[0]=new Level(new IVec2(16,9),"assets/data/level1.txt");
        //levels[1]=new Level(new IVec2(16,9),"assets/data/level2.txt");
        //levels[2]=new Level(new IVec2(16,9),"assets/data/level3.txt");
        levels[0]=new Level(new IVec2(146,74),"assets/data/level4.txt");
        return levels;
    }

    public static Tank[] genTanks(){
        Tank[] tanks=new Tank[2];
        tanks[0]=new Tank(
                new IVec2(320,120),
                "./assets/textures/characters/tanks/test/base.png",
                "./assets/textures/characters/tanks/test/turret.png",
                10,
                new IVec2(64,64),
                1,
                0.1,
                0.05
        );
        tanks[1]=new Tank(
                new IVec2(0,0),
                "./assets/textures/characters/tanks/panzer_IV/base.png",
                "./assets/textures/characters/tanks/panzer_IV/turret.png",
                5,
                new IVec2(38,72),
                1,
                0.1,
                0.05
        );
        return tanks;
    }

    private static BufferedImage tileSheet_1 =null;

    public static BufferedImage[][] getGrassTextures(){
        if(tileSheet_1 ==null) {
            try {tileSheet_1 = ImageIO.read(new File("assets/textures/level/tileSheet_1.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] grassTextures = new BufferedImage[Cfg.getGrassColorsNb()][Cfg.getGrassVariantsNb()];
        for (int i=0;i<Cfg.getGrassColorsNb();i++){
            for (int j=0;j<Cfg.getGrassVariantsNb();j++){
                grassTextures[i][j]=tileSheet_1.getSubimage((i*Cfg.getGrassVariantsNb()+j)* Cfg.getTileSize(),0,Cfg.getTileSize(),Cfg.getTileSize());
            }
        }
        return grassTextures;
    }

    public static BufferedImage[][] getStoneTextures(){
        if(tileSheet_1 ==null) {
            try {tileSheet_1 = ImageIO.read(new File("assets/textures/level/tileSheet_1.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[][] stoneTextures = new BufferedImage[Cfg.getStoneColorsNb()][Cfg.getStoneVariantsNb()];
        for (int i=0;i<Cfg.getStoneColorsNb();i++){
            for (int j=0;j<Cfg.getStoneVariantsNb();j++){
                stoneTextures[i][j]=tileSheet_1.getSubimage((i*Cfg.getStoneVariantsNb()+j)* Cfg.getTileSize(),Cfg.getTileSize(),Cfg.getTileSize(),Cfg.getTileSize());
            }
        }
        return stoneTextures;
    }
}
