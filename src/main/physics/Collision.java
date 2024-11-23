package main.physics;

import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public class Collision {
    public final BVec2 collisions;
    public final double angle;
    public final Vec2 normal;

    public Collision(BVec2 collisions){
        this.collisions = collisions;
        this.angle=0;
        this.normal=new Vec2();
    }
}
