package main.physics;

import main.physics.colliders.Collider;
import main.physics.layers.CollisionLayer;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

public record Collision(
    Collider colliderSource,
    Collider colliderTarget,

    ColliderType colliderTypeSource,
    ColliderType colliderTypeTarget,

    CollisionLayer collisionLayerSource,
    CollisionLayer collisionLayerTarget,

    double colliderSourceFriction,
    double colliderTargetFriction,

    double colliderSourceModifier,
    double colliderTargetModifier,

    BVec2 didCollide,
    Vec2 normal
) {
    public static Collision getReverseCollision(Collision collision) {
        return new Collision(
            collision.colliderTarget,
            collision.colliderSource,

            collision.colliderTypeTarget,
            collision.colliderTypeSource,

            collision.collisionLayerTarget,
            collision.collisionLayerSource,

            collision.colliderTargetFriction,
            collision.colliderSourceFriction,

            collision.colliderTargetModifier,
            collision.colliderSourceModifier,

            collision.didCollide,
            new Vec2(
                    -collision.normal.x,
                    -collision.normal.y
            )
        );
    }
}
