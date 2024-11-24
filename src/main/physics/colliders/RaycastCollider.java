package main.physics.colliders;

import main.physics.Collision;
import main.utils.vectors.Vec2;

public class RaycastCollider extends SolidCollider{
    public RaycastCollider(Vec2 offset) {
        super(false, 0, offset);
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return null;
    }
}
