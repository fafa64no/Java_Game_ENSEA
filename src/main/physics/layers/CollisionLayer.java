package main.physics.layers;

public enum CollisionLayer {
    COLLISION_LAYER_TERRAIN,
    COLLISION_LAYER_ALLIES,
    COLLISION_LAYER_ENEMIES,

    COLLISION_LAYER_TERRAIN_PROJECTILES,
    COLLISION_LAYER_ALLY_PROJECTILES,
    COLLISION_LAYER_ENEMY_PROJECTILES,

    COLLISION_LAYER_FLYING_TERRAIN,
    COLLISION_LAYER_FLYING_ALLIES,
    COLLISION_LAYER_FLYING_ENEMIES,

    COLLISION_LAYER_FLYING_TERRAIN_PROJECTILES,
    COLLISION_LAYER_FLYING_ALLY_PROJECTILES,
    COLLISION_LAYER_FLYING_ENEMY_PROJECTILES,

    COLLISION_LAYER_COUNT,

    COLLISION_LAYER_UNKNOWN;


    public static CollisionLayer getCollisionLayer(int i) {
        return switch (i) {
            case 0 -> COLLISION_LAYER_TERRAIN;
            case 1 -> COLLISION_LAYER_ALLIES;
            case 2 -> COLLISION_LAYER_ENEMIES;

            case 3 -> COLLISION_LAYER_TERRAIN_PROJECTILES;
            case 4 -> COLLISION_LAYER_ALLY_PROJECTILES;
            case 5 -> COLLISION_LAYER_ENEMY_PROJECTILES;

            case 6 -> COLLISION_LAYER_FLYING_TERRAIN;
            case 7 -> COLLISION_LAYER_FLYING_ALLIES;
            case 8 -> COLLISION_LAYER_FLYING_ENEMIES;

            case 9  -> COLLISION_LAYER_FLYING_TERRAIN_PROJECTILES;
            case 10 -> COLLISION_LAYER_FLYING_ALLY_PROJECTILES;
            case 11 -> COLLISION_LAYER_FLYING_ENEMY_PROJECTILES;

            default -> {
                System.out.println("Unknown collision layer");
                yield COLLISION_LAYER_UNKNOWN;
            }
        };
    }
}
