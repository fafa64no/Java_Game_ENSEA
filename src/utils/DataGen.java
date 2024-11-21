package utils;

import game.level.Level;
import game.characters.vehicles.tank.Tank;
import physics.Collider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataGen {
    public static Level[] genLevels(){
        Level[] levels=new Level[4];
        levels[0]=new Level(new IVec2(16,9),"./data/level1.txt");
        levels[1]=new Level(new IVec2(16,9),"./data/level2.txt");
        levels[2]=new Level(new IVec2(16,9),"./data/level3.txt");
        levels[3]=new Level(new IVec2(80,40),"./data/level4.txt");
        return levels;
    }

    public static Tank[] genTanks(){
        Tank[] tanks=new Tank[2];
        tanks[0]=new Tank(
                new IVec2(320,120),
                new Collider(-16,-16,16,16),
                "./img/characters/tanks/test/base.png",
                "./img/characters/tanks/test/turret.png",
                10,
                new IVec2(64,64),
                1,
                0.1,
                0.05
        );
        tanks[1]=new Tank(
                new IVec2(0,0),
                new Collider(-16,-16,16,16),
                "./img/characters/tanks/tank1/base.png",
                "./img/characters/tanks/tank1/turret.png",
                5,
                new IVec2(38,72),
                1,
                0.1,
                0.05
        );
        return tanks;
    }

    private static BufferedImage tilemap1=null;

    public static BufferedImage[] getGrassTextures(){
        if(tilemap1==null) {
            try {tilemap1 = ImageIO.read(new File("img/level/tileSheet_2.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[] grassTextures = new BufferedImage[8];
        grassTextures[0]=tilemap1.getSubimage(0,0,16,16);
        grassTextures[1]=tilemap1.getSubimage(16,0,16,16);
        grassTextures[2]=tilemap1.getSubimage(32,0,16,16);
        grassTextures[3]=tilemap1.getSubimage(48,0,16,16);
        grassTextures[4]=tilemap1.getSubimage(0,16,16,16);
        grassTextures[5]=tilemap1.getSubimage(16,16,16,16);
        grassTextures[6]=tilemap1.getSubimage(32,16,16,16);
        grassTextures[7]=tilemap1.getSubimage(48,16,16,16);
        return grassTextures;
    }

    public static BufferedImage[] getStoneTextures(){
        if(tilemap1==null) {
            try {tilemap1 = ImageIO.read(new File("img/level/tileSheet_2.png"));}
            catch (IOException e) {throw new RuntimeException(e);}
        }
        BufferedImage[] stoneTextures = new BufferedImage[8];
        stoneTextures[0]=tilemap1.getSubimage(0,32,16,16);
        stoneTextures[1]=tilemap1.getSubimage(16,32,16,16);
        stoneTextures[2]=tilemap1.getSubimage(32,32,16,16);
        stoneTextures[3]=tilemap1.getSubimage(48,32,16,16);
        stoneTextures[4]=tilemap1.getSubimage(0,48,16,16);
        stoneTextures[5]=tilemap1.getSubimage(16,48,16,16);
        stoneTextures[6]=tilemap1.getSubimage(32,48,16,16);
        stoneTextures[7]=tilemap1.getSubimage(48,48,16,16);
        return stoneTextures;
    }
}
