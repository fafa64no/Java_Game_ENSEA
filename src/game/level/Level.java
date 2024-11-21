package game.level;

import physics.Collider;
import rendering.Displayable;
import rendering.RenderEngine;
import utils.DataGen;
import utils.IVec2;
import utils.PseudoRandom;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level extends JPanel implements Displayable {
    private final char[][] map;
    private final List<Collider> colliders;

    private final IVec2 tileSize = new IVec2(16,16);
    private final BufferedImage[] grassTextures;
    private final BufferedImage[] stoneTextures;
    private BufferedImage treeTexture;
    private BufferedImage trapTexture;

    public Level(IVec2 size,String path){
        map=new char[size.x][size.y];
        colliders=new ArrayList<>();
        try{
            treeTexture = ImageIO.read(new File("./img/level/tree.png"));
            trapTexture = ImageIO.read(new File("./img/level/trap.png"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        } catch (Exception e){e.printStackTrace();}
        grassTextures = DataGen.getGrassTextures();
        stoneTextures = DataGen.getStoneTextures();

        // Search for exit
//        int exitSide=0;
//        int exitPlacement=0;
//        for (int i=0;i<size.x;i++){
//            if (this.map[0][i]=='.')        { exitSide=1;   exitPlacement=i; break; }
//            if (this.map[size.y-1][i]=='.') { exitSide=3;   exitPlacement=i; break; }
//        }
//        for (int i=0;i<size.y;i++){
//            if (this.map[i][0]=='.')        { exitSide=2;   exitPlacement=i; break; }
//            if (this.map[i][size.x-1]=='.')   { exitSide=4;   exitPlacement=i; break; }
//        }
//
//        // Generate colliders
//        switch (exitSide){
//            case 0:
//                colliders.add(new Collider(tileSize.x+24,tileSize.y+24,tileSize.x*(size.x-1)-24,tileSize.y*(size.y-1)-24,true));
//                break;
//            case 1:
//                colliders.add(new Collider(88,-40,64*(size.x-2)+40,64*(size.y-2)+40,true));
//                colliders.add(new Collider(0,0,64*exitPlacement,64));
//                colliders.add(new Collider(64*(exitPlacement+1),0,64*size.x,64));
//                break;
//            case 2:
//                colliders.add(new Collider(-40,88,64*(size.x-2)+40,64*(size.y-2)+40,true));
//                colliders.add(new Collider(0,0,64,64*exitPlacement));
//                colliders.add(new Collider(0,64*(exitPlacement+1),64,64*size.y));
//                break;
//            case 3:
//                colliders.add(new Collider(88,88,64*(size.x-2)+40,64*(size.y-2)+168,true));
//                colliders.add(new Collider(0,64*(size.y-1),64*exitPlacement,64*(size.y)));
//                colliders.add(new Collider(64*(exitPlacement+1),64*(size.y-1),64*size.x,64*(size.y)));
//                break;
//            case 4:
//                colliders.add(new Collider(88,88,64*(size.x-2)+168,64*(size.y-2)+40,true));
//                colliders.add(new Collider(64*(size.x-1),0,64*size.x,64*exitPlacement));
//                colliders.add(new Collider(64*(size.x-1),64*(exitPlacement+1),64*size.x,64*size.y));
//                break;
//        }
        for (int x=0;x<size.x;x++){
            for (int y=0;y<size.y;y++){
                switch (this.map[y][x]){
                    case 'R':
                        colliders.add(new Collider(tileSize.x*x,tileSize.y*y,tileSize.x*(x+1),tileSize.y*(y+1),0.3));
                        break;
                    case 'T':
                        colliders.add(new Collider(tileSize.x*x,tileSize.y*y,tileSize.x*(x+1),tileSize.y*(y+1)));
                        break;
                    case 'H':
                        break;
                }
            }
        }
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
        int x=Math.round((float) (-map[0].length * tileSize.x) /2);
        int y=Math.round((float) (-map.length * tileSize.y) /2);
        for (char[] line : map){
            for (char c : line){
                switch (c){
                    case ' ':
                    case '.':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(grassTextures[PseudoRandom.getRandomBetween(0,grassTextures.length,x,y)],null);
                        g2d.translate(-x,-y); break;
                    case 't':
                    case 'T':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(treeTexture,null);
                        g2d.translate(-x,-y); break;
                    case 'h':
                    case 'H':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(trapTexture,null);
                        g2d.translate(-x,-y); break;
                    case 'r':
                    case 'R':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(stoneTextures[PseudoRandom.getRandomBetween(0,stoneTextures.length,x,y)],null);
                        g2d.translate(-x,-y); break;
                }
                x+=tileSize.x;
            }
            y+=tileSize.y;
            x=Math.round((float) (-map[0].length * tileSize.x) /2);
        }
    }
}
