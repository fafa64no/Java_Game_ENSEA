package main.game.level.target;

import main.game.vehicles.LifeState;
import main.physics.dynamic_objects.DynamicPoint;
import main.utils.vectors.Vec2;

public interface Target {
    Vec2 getPosition();
    Vec2 getVelocity();

    void addPositionSource(DynamicPoint dynamicPoint);
    void addInputSource();

    boolean isTargetable();
    LifeState getLifeState();

    void dealDamage(double damage);
    double getHealthPercentage();
}
