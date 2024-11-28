package main.game.characters.cubes;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public interface RedCube {
    Vec2 getPosition();
    BufferedImage getTexture();
    double getRotation();
    int getTextureSize();
}
