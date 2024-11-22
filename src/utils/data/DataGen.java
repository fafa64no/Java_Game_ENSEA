package utils.data;

import game.level.Level;
import game.characters.vehicles.tank.Tank;
import physics.Collider;
import utils.vectors.IVec2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataGen {
    public static Level[] genLevels(){
        Level[] levels=new Level[4];
        levels[0]=new Level(new IVec2(16,9),"assets/data/level1.txt");
        levels[1]=new Level(new IVec2(16,9),"assets/data/level2.txt");
        levels[2]=new Level(new IVec2(16,9),"assets/data/level3.txt");
        levels[3]=new Level(new IVec2(114,74),"assets/data/level4.txt");
        return levels;
    }

    public static Tank[] genTanks(){
        Tank[] tanks=new Tank[2];
        tanks[0]=new Tank(
                new IVec2(320,120),
                new Collider(-16,-16,16,16),
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
                new Collider(-16,-16,16,16),
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

    public static BufferedImage[] getGrassTextures(){
        if(tileSheet_1 ==null) {
            try {tileSheet_1 = ImageIO.read(new File("assets/textures/level/tileSheet_1.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        int grassNb=32;
        BufferedImage[] grassTextures = new BufferedImage[grassNb];
        for (int i=0;i<grassNb;i++){
            grassTextures[i]=tileSheet_1.getSubimage(i* Cfg.getTileSize(),0,Cfg.getTileSize(),Cfg.getTileSize());
        }
        return grassTextures;
    }

    public static BufferedImage[] getStoneTextures(){
        if(tileSheet_1 ==null) {
            try {tileSheet_1 = ImageIO.read(new File("assets/textures/level/tileSheet_1.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        int stoneNb=32;
        BufferedImage[] stoneTextures = new BufferedImage[stoneNb];
        for (int i=0;i<stoneNb;i++){
            stoneTextures[i]=tileSheet_1.getSubimage(i*Cfg.getTileSize(),Cfg.getTileSize(),Cfg.getTileSize(),Cfg.getTileSize());
        }
        return stoneTextures;
    }
}
