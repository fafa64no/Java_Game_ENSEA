package main.game.level;

import main.game.characters.cubes.BasicCube;
import main.game.characters.cubes.RedCube;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.physics.colliders.SolidCollider;
import main.physics.colliders.TilemapCollider;
import main.rendering.*;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.debug.Debug;
import main.utils.vectors.IVec2;
import main.utils.noise.PseudoRandom;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level extends JPanel implements Displayable {
    private final char[][] map;
    private final BufferedImage[][] mapTextures;
    private final List<Collider> colliders = new ArrayList<>();

    private final Vec2 mapOffset = new Vec2();

    private final BufferedImage[][] grassTextures;
    private final BufferedImage[][] stoneTextures;
    private final BufferedImage[][] treeTextures;
    private final BufferedImage[][] pathTextures;
    private final BufferedImage trapTexture;
    private final BufferedImage barrierTexture;

    private final LeavesRenderer leavesRenderer;
    private final CubeRenderer cubeRenderer;

    public Level(IVec2 size,String path){
        this.setOpaque(false);
        map=new char[size.y][size.x];
        mapTextures=new BufferedImage[size.y][size.x];
        mapOffset.x=-map[0].length * Config.smallTileSize /2.0;
        mapOffset.y=-map.length * Config.smallTileSize /2.0;
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        } catch (Exception e){e.printStackTrace();}

        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();
        treeTextures = DataGen.getTreeTextures();
        pathTextures = DataGen.getPathTextures();
        trapTexture = DataGen.getTrapTexture();
        barrierTexture = DataGen.getBarrierTexture();

        initTextures();
        initColliders();
        spawnEnemies();

        leavesRenderer = new LeavesRenderer(map,mapOffset);
        cubeRenderer = new CubeRenderer();
    }

    public Level(IVec2 size){
        this.setOpaque(false);
        map=LevelGenerator.genTerrain(size);

        Debug.printTimeSinceLast("Generated terrain char array");

        mapTextures=new BufferedImage[size.y][size.x];
        mapOffset.x=-map[0].length * Config.smallTileSize /2.0;
        mapOffset.y=-map.length * Config.smallTileSize /2.0;

        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();
        treeTextures = DataGen.getTreeTextures();
        pathTextures = DataGen.getPathTextures();
        trapTexture = DataGen.getTrapTexture();
        barrierTexture = DataGen.getBarrierTexture();

        leavesRenderer = new LeavesRenderer(map,mapOffset);
        cubeRenderer = new CubeRenderer();

        initTextures();
        initColliders();
        spawnEnemies();
    }

    private void initTextures(){
        for (int x=0;x<map[0].length;x++) {
            for (int y = 0; y < map.length; y++) {
                switch (map[y][x]){
                    case 'R':
                        mapTextures[y][x]=stoneTextures[PseudoRandom.getRandomBetween(0,stoneTextures.length-1,x,y, Config.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,stoneTextures[0].length-1,x,y, Config.noiseSizeTerrainVariant)];
                        break;
                    case 'T':
                        mapTextures[y][x]=treeTextures[PseudoRandom.getRandomBetween(0,treeTextures.length-1,x,y, Config.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,treeTextures[0].length-1,x,y, Config.noiseSizeTerrainVariant)];
                        break;
                    case 'P':
                        mapTextures[y][x]=pathTextures[PseudoRandom.getRandomBetween(0,pathTextures.length-1,x,y, Config.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,pathTextures[0].length-1,x,y, Config.noiseSizeTerrainVariant)];
                        break;
                    case '0':
                    case '1':
                        mapTextures[y][x]=trapTexture;
                        break;
                    default:
                        mapTextures[y][x]=grassTextures[PseudoRandom.getRandomBetween(0,grassTextures.length-1,x,y, Config.noiseSizeTerrainColor)]
                                [PseudoRandom.getRandomBetween(0,grassTextures[0].length-1,x,y, Config.noiseSizeTerrainVariant)];
                        break;
                }
            }
        }
    }

    private void initColliders(){
        colliders.add(new BoxCollider(
                new Vec2( mapOffset.x, mapOffset.y),
                new Vec2(-mapOffset.x,-mapOffset.y),
                true,
                0.5,
                new Vec2()
        ));
        colliders.add(new TilemapCollider(this));
    }

    private void spawnEnemies(){
        Vec2 spawnPosition;
        int cube0count=0;
        int cube1count=0;
        for (int x=0;x<map[0].length;x++) {
            for (int y = 0; y < map.length; y++) {
                switch (map[y][x]){
                    case '0':
                        spawnPosition = Vec2.add(Vec2.multiply(new Vec2(x,y),Config.smallTileSize),mapOffset,new Vec2(Config.smallTileSize*0.5));
                        cubeRenderer.addCube(new BasicCube(spawnPosition.copy(),DataGen.getBasicCubeTexture()));
                        cube0count++;
                        break;
                    case '1':
                        spawnPosition = Vec2.add(Vec2.multiply(new Vec2(x,y),Config.smallTileSize),mapOffset,new Vec2(Config.smallTileSize*0.5));
                        cubeRenderer.addCube(new BasicCube(spawnPosition.copy(),DataGen.getBasicCubeTexture()));
                        cube1count++;
                        break;
                }
            }
        }
        System.out.println("\nGenerated cubes : "+(cube0count+cube1count)+"\n\t0 : "+cube0count+"\n\t1 : "+cube1count+"\n");
    }

    public double getGroundSpeed(Vec2 position){
        int tilePosX = Math.clamp((int) Math.round((position.x-mapOffset.x) / Config.smallTileSize),0,map[0].length-1);
        int tilePosY = Math.clamp((int) Math.round((position.y-mapOffset.y) / Config.smallTileSize),0,map.length-1);
        char tile = map[tilePosY][tilePosX];
        return switch (tile) {
            case 'P' -> Config.pathSpeedModifier;
            case 'M' -> 0.1;
            default -> 1;
        };
    }

    private Vec4 getTilesWindow(Vec2 center, Vec2 halfWindowSize){
        return new Vec4(
                center.x-halfWindowSize.x+map[0].length/2.0,
                center.y-halfWindowSize.y+map.length/2.0,
                center.x+halfWindowSize.x+map[0].length/2.0,
                center.y+halfWindowSize.y+map.length/2.0
        );
    }

    private IVec4 getTilesIDWindowFromCameraWithExtraBorder(){
        Vec2 cameraCenter = RenderEngine.getCurrentCamera().getTargetTileOffset();
        Vec2 halfWindowSize = new Vec2(
                RenderEngine.getInstance().getContentPane().getSize().width /(2*Config.smallTileSize*RenderEngine.getCurrentCamera().getScale().x),
                RenderEngine.getInstance().getContentPane().getSize().height/(2*Config.smallTileSize*RenderEngine.getCurrentCamera().getScale().y)
        );
        Vec4 tilesWindow = getTilesWindow(cameraCenter,halfWindowSize);
        return new IVec4(
            (int) Math.floor(Math.max(-Config.mapHorizontalWallThickness, tilesWindow.x)),
            (int) Math.ceil(Math.min(map[0].length+ Config.mapHorizontalWallThickness, tilesWindow.z)),
            (int) Math.floor(Math.max(-Config.mapVerticalWallThickness, tilesWindow.y)),
            (int) Math.ceil(Math.min(map.length+ Config.mapVerticalWallThickness, tilesWindow.w))
        );
    }

    public IVec4 getTilesIDWindowFromCamera(IVec2 additionalCameraSize){
        Vec4 visibleWindow = RenderEngine.getCurrentCamera().getDisplayWindow(Vec2.multiply(additionalCameraSize,Config.smallTileSize));
        return new IVec4(
            (int) Math.floor((visibleWindow.x-mapOffset.x)/Config.smallTileSize),
            (int) Math.ceil( (visibleWindow.y-mapOffset.x)/Config.smallTileSize),
            (int) Math.floor((visibleWindow.z-mapOffset.y)/Config.smallTileSize),
            (int) Math.ceil( (visibleWindow.w-mapOffset.y)/Config.smallTileSize)
        );
    }

    public LeavesRenderer getLeavesRenderer() {
        return leavesRenderer;
    }

    public CubeRenderer getCubeRenderer() {
        return cubeRenderer;
    }

    public char[][] getMap() {
        return map;
    }

    public Vec2 getMapOffset() {
        return mapOffset;
    }

    public List<Collider> getColliders() {
        return colliders;
    }

    @Override
    public void draw() {
        RenderEngine.getInstance().remove(this);
        RenderEngine.getInstance().add(this);
    }

    @Override
    public void clear() {
        RenderEngine.getInstance().remove(this);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);
        // Don't render outside of camera range because lag
        IVec4 tileIDWindow = getTilesIDWindowFromCameraWithExtraBorder();
        for (int x = tileIDWindow.x; x < tileIDWindow.y; x++) {
            for (int y = tileIDWindow.z; y < tileIDWindow.w; y++) {
                double posX=mapOffset.x+x* Config.smallTileSize;
                double posY=mapOffset.y+y* Config.smallTileSize;
                g2d.translate(posX,posY);
                if(x>=0&&x<map[0].length&&y>=0&&y<map.length){
                    g2d.drawRenderedImage(mapTextures[y][x],null);
                }else{
                    g2d.drawRenderedImage(barrierTexture,null);
                }
                g2d.translate(-posX,-posY);
            }
        }
    }
}
