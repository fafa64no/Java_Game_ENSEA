package main.game.level.target;

import main.game.vehicles.LifeState;
import main.utils.vectors.Vec2;

public interface Target {
    Vec2 getPosition();
    Vec2 getVelocity();

    boolean isTargetable();
    LifeState getLifeState();

    void dealDamage(double damage);
    double getHealthPercentage();
}
