package main.game.characters.cubes;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;
import java.util.List;

public interface RedCube {
    Vec2 getPosition();
    BufferedImage getTexture();
    double getRotation();
    int getTextureSize();
    List<DecorativeFollowerCube> getDecorativeFollowerCubes();
}
