package main.utils.data;

import main.game.level.Level;
import main.utils.vectors.IVec2;

public class DataGen {

/*      ################ - Levels, Tanks and Cameras - ################      */

    public static Level[] genLevels(){
        Level[] levels = new Level[1];
        levels[0] = new Level(new IVec2(Config.defaultMapWidth,Config.defaultMapHeight));
        return levels;
    }

}
