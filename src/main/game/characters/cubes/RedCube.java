package main.game.characters.cubes;

import main.game.characters.LifeStates;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public interface RedCube {
    Vec2 getPosition();
    IVec2 getTextureSize();
    BufferedImage getTexture();
    double getRotation();
}
