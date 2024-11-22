package physics.colliders;

import game.DynamicSprite;
import utils.vectors.BVec2;
import utils.vectors.IVec2;

public interface Collider {
    BVec2 doCollide(Collider c, IVec2 offset);
    void onCollide();
    IVec2 getOffset();
    boolean isInverted();
    DynamicSprite getParent();
    double getFriction();
    void setOffset(DynamicSprite requester,IVec2 offset);
}
