package game;

import physics.Collider;
import rendering.Camera;
import rendering.Displayable;
import rendering.RenderEngine;
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

    private final BufferedImage[] grassTextures;
    private BufferedImage treeTexture;
    private BufferedImage trapTexture;
    private BufferedImage rockTexture;

    private Camera currentCamera;

    public Level(IVec2 size,String path){
        this.map=new char[size.x][size.y];
        this.colliders=new ArrayList<>();
        this.grassTextures=new BufferedImage[4];
        try{
            grassTextures[0] = ImageIO.read(new File("./img/level/grass0.png"));
            grassTextures[1] = ImageIO.read(new File("./img/level/grass1.png"));
            grassTextures[2] = ImageIO.read(new File("./img/level/grass2.png"));
            grassTextures[3] = ImageIO.read(new File("./img/level/grass3.png"));
            treeTexture = ImageIO.read(new File("./img/level/tree.png"));
            trapTexture = ImageIO.read(new File("./img/level/trap.png"));
            rockTexture = ImageIO.read(new File("./img/level/rock.png"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            for (int i = 0; i< size.y; i++) map[i]=bufferedReader.readLine().toCharArray();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // Search for exit
        int exitSide=0;
        int exitPlacement=0;
        for (int i=0;i<size.x;i++){
            if (this.map[0][i]=='.')        { exitSide=1;   exitPlacement=i; break; }
            if (this.map[size.y-1][i]=='.') { exitSide=3;   exitPlacement=i; break; }
        }
        for (int i=0;i<size.y;i++){
            if (this.map[i][0]=='.')        { exitSide=2;   exitPlacement=i; break; }
            if (this.map[i][size.x-1]=='.')   { exitSide=4;   exitPlacement=i; break; }
        }

        // Generate colliders
        switch (exitSide){
            case 0:
                colliders.add(new Collider(88,88,64*(size.x-2)+40,64*(size.y-2)+40,true));
                break;
            case 1:
                colliders.add(new Collider(88,-40,64*(size.x-2)+40,64*(size.y-2)+40,true));
                colliders.add(new Collider(0,0,64*exitPlacement,64));
                colliders.add(new Collider(64*(exitPlacement+1),0,64*size.x,64));
                break;
            case 2:
                colliders.add(new Collider(-40,88,64*(size.x-2)+40,64*(size.y-2)+40,true));
                colliders.add(new Collider(0,0,64,64*exitPlacement));
                colliders.add(new Collider(0,64*(exitPlacement+1),64,64*size.y));
                break;
            case 3:
                colliders.add(new Collider(88,88,64*(size.x-2)+40,64*(size.y-2)+168,true));
                colliders.add(new Collider(0,64*(size.y-1),64*exitPlacement,64*(size.y)));
                colliders.add(new Collider(64*(exitPlacement+1),64*(size.y-1),64*size.x,64*(size.y)));
                break;
            case 4:
                colliders.add(new Collider(88,88,64*(size.x-2)+168,64*(size.y-2)+40,true));
                colliders.add(new Collider(64*(size.x-1),0,64*size.x,64*exitPlacement));
                colliders.add(new Collider(64*(size.x-1),64*(exitPlacement+1),64*size.x,64*size.y));
                break;
        }
        for (int x=1;x<size.x-1;x++){
            for (int y=1;y<size.y-1;y++){
                switch (this.map[y][x]){
                    case 'R':
                        colliders.add(new Collider(64*x,64*y+8,64*(x+1),64*(y+1)-8,0.3));
                        break;
                    case 'T':
                        colliders.add(new Collider(64*x,64*y,64*(x+1),64*(y+1)));
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
    public void linkCamera(Camera camera) {
        this.currentCamera=camera;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g.create();
        g2d.scale(currentCamera.getScale().x,currentCamera.getScale().y);
        g2d.translate(-currentCamera.getOffset().x,-currentCamera.getOffset().y);
        int x=0;    int y=0;
        for (char[] line : map){
            for (char c : line){
                switch (c){
                    case ' ':
                    case '.':
                        g2d.translate(x,y);
                        g2d.drawRenderedImage(grassTextures[PseudoRandom.getRandomBetween(0,3,x,y)],null);
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
                        g2d.drawRenderedImage(rockTexture,null);
                        g2d.translate(-x,-y); break;
                }
                x+=64;
            }
            y+=64;
            x=0;
        }
    }
}
