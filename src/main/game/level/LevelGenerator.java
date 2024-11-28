package main.game.level;

import main.utils.data.Config;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec2;

import java.io.*;

public class LevelGenerator {
    public static char[][] genTerrain(IVec2 size){
        char[][] output=new char[size.y][size.x];
        genCharMap(output);
        addSpawnTiles(output);
        addCubeTiles(output);
        addStructureTiles(output);
        return output;
    }

    private static void genCharMap(char[][] map){
        for(int x = 0; x < map[0].length; x++){
            for(int y = 0; y < map.length; y++){
                // Terrain
                if(PseudoRandom.isRandomBetween(-0.05,0.05,-x+50,30+y, Config.noiseSizeTerrainPath)){
                    map[y][x]='P';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0,0.1,x+5,3-y, Config.noiseSizeTerrainStones)){
                    map[y][x]='R';
                    continue;
                }
                if(PseudoRandom.isRandomBetween(0.45,0.55,2-x,3-y, Config.noiseSizeTerrainTrees)){
                    map[y][x]='T';
                    continue;
                }
                // Enemies
                double spawnKey=Math.abs(PseudoRandom.getRandomDouble(30*x,3-20*y, Config.noiseSizeTerrainCubes));
                if(spawnKey<0.0001){
                    map[y][x]='2';
                    continue;
                }
                if(spawnKey<0.0002){
                    map[y][x]='1';
                    continue;
                }
                if(spawnKey<0.0005){
                    map[y][x]='0';
                    continue;
                }
                map[y][x]='.';
                // Structures
                spawnKey=Math.abs(PseudoRandom.getRandomDouble(22*x,3-18*y, Config.noiseSizeTerrainStructures));
                if(spawnKey<0.00002){
                    map[y][x]='V';
                    continue;
                }
                if(spawnKey<0.00005){
                    map[y][x]='C';
                    continue;
                }
                map[y][x]='.';
            }
        }
    }

    private static void insertTiles(char[][] map, IVec2 size, IVec2 offset, String path){
        int sizeX, sizeY, middleX,middleY,minX,maxX,minY,maxY;
        sizeX = map[0].length;
        sizeY = map.length;
        middleX = sizeX/2;
        middleY = sizeY/2;
        minX = Math.clamp(middleX + offset.x - size.x/2,0,sizeX);
        maxX = Math.clamp(middleX + offset.x + size.x/2,0,sizeX);
        minY = Math.clamp(middleY + offset.y - size.y/2,0,sizeY);
        maxY = Math.clamp(middleY + offset.y + size.y/2,0,sizeY);

        if(minX==maxX || minY==maxY)return;

        char[][] tilesToInsert = new char[size.y][size.x];
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("assets/data/"+path));
            for (int i = 0; i < size.y; i++) tilesToInsert[i]=bufferedReader.readLine().toCharArray();
            bufferedReader.close();
        } catch (Exception e){System.out.println("Had issue inserting tiles at "+offset);}

        int i=0,j=0;
        for(int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if(tilesToInsert[j][i]!='X') map[y][x]= tilesToInsert[j][i];
                j++;
            }
            j=0;
            i++;
        }
    }

    private static void addSpawnTiles(char[][] map){
        insertTiles(map,new IVec2(Config.spawnWidth,Config.spawnHeight),new IVec2(),"defaultSpawn.txt");
    }

    private static void addCubeTiles(char[][] map){
        for(int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                switch (map[y][x]){
                    case '1':
                        addCube1Tiles(map,new IVec2(x,y));
                        break;
                    case '2':
                        addCube2Tiles(map,new IVec2(x,y));
                        break;
                }
            }
        }
    }

    private static void addCube1Tiles(char[][] map, IVec2 offset){
        IVec2 actualOffset = new IVec2();
        actualOffset.x = offset.x - map[0].length/2;
        actualOffset.y = offset.y - map.length/2;
        insertTiles(map,new IVec2(4,4),actualOffset,"defaultCube1.txt");
    }

    private static void addCube2Tiles(char[][] map, IVec2 offset){
        IVec2 actualOffset = new IVec2();
        actualOffset.x = offset.x - map[0].length/2;
        actualOffset.y = offset.y - map.length/2;
        insertTiles(map,new IVec2(6,6),actualOffset,"defaultCube2.txt");
    }

    private static void addStructureTiles(char[][] map){
        int structures0Count=0;
        int structures1Count=0;
        for(int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                switch (map[y][x]){
                    case 'C':
                        addStructure0Tiles(map,new IVec2(x,y));
                        structures0Count++;
                        break;
                    case 'V':
                        addStructure1Tiles(map,new IVec2(x,y));
                        structures1Count++;
                        break;
                }
            }
        }
        System.out.println("\nGenerated structures : "+(structures0Count+structures1Count)+"\n\t0 : "+structures0Count+"\n\t1 : "+structures1Count+"\n");
    }

    private static void addStructure0Tiles(char[][] map, IVec2 offset){
        IVec2 actualOffset = new IVec2();
        actualOffset.x = offset.x - map[0].length/2;
        actualOffset.y = offset.y - map.length/2;
        insertTiles(map,new IVec2(14,14),actualOffset,"structure0.txt");
    }

    private static void addStructure1Tiles(char[][] map, IVec2 offset){
        IVec2 actualOffset = new IVec2();
        actualOffset.x = offset.x - map[0].length/2;
        actualOffset.y = offset.y - map.length/2;
        insertTiles(map,new IVec2(14,14),actualOffset,"structure1.txt");
    }


    private static void writeMap(char[][] map){
        try {
            PrintWriter printWriter= new PrintWriter(new FileWriter("assets/data/autoGeneratedLevel.txt"));
            for (char[] chars : map) {
                for (int x = 0; x < map[0].length; x++) {
                    printWriter.write(chars[x]);
                }
                printWriter.write('\n');
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
