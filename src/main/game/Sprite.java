package main.game;

import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.utils.vectors.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Sprite extends JPanel implements Displayable {
    protected Vec2 position;
    protected BufferedImage texture;

    public Sprite(Vec2 position, String texturePath) {
        this.position = position;

        this.setOpaque(false);

        try {this.texture = ImageIO.read(new File(texturePath));}
        catch (Exception e){e.printStackTrace();}
    }

    public Vec2 getPosition() {
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
