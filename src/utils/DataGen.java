package utils;

import game.Level;
import game.characters.vehicles.tank.Tank;
import physics.Collider;

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
        Tank[] tanks=new Tank[1];
        tanks[0]=new Tank(
                new IVec2(320,120),
                new Collider(16,16,48,48),
                "./img/characters/tanks/test/base.png",
                "./img/characters/tanks/test/turret.png",
                10,
                new IVec2(64,64),
                1,
                0.1,
                0.05
        );
        return tanks;
    }
}
