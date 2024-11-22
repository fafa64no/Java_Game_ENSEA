package main.physics;

import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public class Collision {
    private final BVec2 collisions;
    private final double angle;
    private final Vec2 normal;

    public Collision(BVec2 collisions){
        this.collisions = collisions;
        this.angle =0;
        this.normal=new Vec2();
    }

    public BVec2 didCollide() {
        return collisions;
    }
}
