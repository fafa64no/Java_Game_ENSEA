package main.game.level;

import main.utils.data.Cfg;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;

public class LevelGenerator {
    public static char[][] genTerrain(IVec2 size){
        char[][] output=new char[size.y][size.x];
        for(int x=0;x<size.x;x++){
            for(int y=0;y<size.y;y++){
                if(PseudoRandom.isRandomBetween(-0.05,0.05,-x+50,30+y, Cfg.noiseSizeTerrainPath)){
                    output[y][x]='P';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0,0.1,x+5,3-y, Cfg.noiseSizeTerrainStones)){
                    output[y][x]='R';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0.45,0.55,2-x,3-y, Cfg.noiseSizeTerrainTrees)){
                    output[y][x]='T';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0,0.001,3*x,3-2*y, Cfg.noiseSizeTerrainTraps)){
                    output[y][x]='H';
                    continue;
                }
                output[y][x]='.';
            }
        }
        return output;
    }
}
