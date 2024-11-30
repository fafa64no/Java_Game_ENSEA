package main.game.vehicles;

import main.utils.vectors.Vec2;

public interface Target {
    Vec2 getPosition();
    Vec2 getVelocity();
    boolean isTargetable();
}
