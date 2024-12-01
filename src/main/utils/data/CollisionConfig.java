package main.utils.data;

import main.physics.Collision;
import main.utils.vectors.Vec4;

public class CollisionConfig {
    public final static int tileMapColliderRange = 4;

    public static final Vec4 wallCollisionBox = new Vec4(
            0,
            1,
            0,
            1
    );
    public static final Vec4 treeCollisionBox = new Vec4(
            0.5-Config.treeHitBoxSize * 0.5,
            0.5+Config.treeHitBoxSize * 0.5,
            0.5-Config.treeHitBoxSize * 0.5,
            0.5+Config.treeHitBoxSize * 0.5
    );

    public static boolean doesCollisionApplyVfx(Collision collision) {
        return true;
    }

    public static boolean doesCollisionApplyPhysics(Collision collision) {
        return true;
    }

    public static boolean doesCollisionApplyEffects(Collision collision) {
        return true;
    }
}
