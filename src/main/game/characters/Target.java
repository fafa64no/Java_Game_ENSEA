package main.game.characters;

import main.utils.vectors.Vec2;

public interface Target {
    Vec2 getPosition();
    Vec2 getVelocity();
    boolean isTargetable();
}
