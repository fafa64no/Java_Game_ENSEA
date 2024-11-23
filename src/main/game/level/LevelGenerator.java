package main.game.level;

import main.utils.data.Cfg;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;

public class LevelGenerator {
    public static char[][] genTerrain(IVec2 size){
        char[][] output=new char[size.y][size.x];
        for(int x=0;x<size.x;x++){
            for(int y=0;y<size.y;y++){
                if(PseudoRandom.isRandomBetween(0,0.05,x+5,3-y, Cfg.noiseSizeTerrainStones)){
                    output[y][x]='R';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0.5,0.55,2-x,3-y, Cfg.noiseSizeTerrainTrees)){
                    output[y][x]='T';
                    continue;
                }
                output[y][x]='.';
            }
        }
        return output;
    }
}
