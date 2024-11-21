package game;

import physics.Collider;
import rendering.Displayable;
import rendering.RenderEngine;
import utils.IVec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Sprite extends JPanel implements Displayable {
    protected IVec2 position;
    private final Collider collider;
    protected BufferedImage texture;

    public Sprite(IVec2 position, Collider collider, String texturePath) {
        this.position = position;
        this.collider = collider;

        this.setOpaque(false);

        try {this.texture = ImageIO.read(new File(texturePath));}
        catch (Exception e){e.printStackTrace();}
    }

    public Collider getCollider() {
        return collider;
    }

    public IVec2 getPosition() {
        return position;
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
}
