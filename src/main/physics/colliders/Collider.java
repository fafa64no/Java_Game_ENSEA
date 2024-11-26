package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.ColliderType;
import main.physics.Collision;
import main.utils.vectors.Vec2;

public interface Collider {
    Collision doCollide(Collider c, Vec2 offset);
    void onCollide(ColliderType colliderType, Collision collision);
    Vec2 getOffset();
    boolean isInverted();
    DynamicSprite getParent();
    double getFriction();
    void setOffset();
    ColliderType getColliderType();
}
