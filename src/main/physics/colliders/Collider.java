package main.physics.colliders;

import main.game.DynamicSprite;
import main.physics.Collision;
import main.utils.vectors.IVec2;

public interface Collider {
    Collision doCollide(Collider c, IVec2 offset);
    void onCollide();
    IVec2 getOffset();
    boolean isInverted();
    DynamicSprite getParent();
    double getFriction();
    void setOffset(DynamicSprite requester,IVec2 offset);
}
