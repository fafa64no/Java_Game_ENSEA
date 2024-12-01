package main.physics;

import main.physics.colliders.Collider;
import main.physics.dynamic_objects.DynamicPoint;
import main.physics.layers.ColliderLayer;
import main.physics.layers.CollisionLayer;
import main.utils.Engine;
import main.utils.containers.BufferedList;

public class PhysicEngine implements Engine {
    private static PhysicEngine instance;

    private final ColliderLayer[] colliderLayers
            = new ColliderLayer[CollisionLayer.COLLISION_LAYER_COUNT.ordinal()];

    private final BufferedList<DynamicPoint> dynamicPointBufferedList = new BufferedList<>();

    public PhysicEngine() {
        if(instance==null)instance=this;
        initColliderLayers();
    }

    @Override
    public void update() {
        updateMovements();
        testAllCollisions();
        updatePositions();
        updateColliderLists();
    }

    private void testAllCollisions() {
        // "testCollisionsWith()" tests collisions both ways

        colliderLayers[CollisionLayer.COLLISION_LAYER_ALLIES.ordinal()]
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_ALLIES.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_ENEMIES.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN_PROJECTILES.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES.ordinal()]);

        colliderLayers[CollisionLayer.COLLISION_LAYER_ENEMIES.ordinal()]
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_ENEMIES.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN_PROJECTILES.ordinal()])
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES.ordinal()]);

        colliderLayers[CollisionLayer.COLLISION_LAYER_ALLY_PROJECTILES.ordinal()]
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN.ordinal()]);

        colliderLayers[CollisionLayer.COLLISION_LAYER_ENEMY_PROJECTILES.ordinal()]
                .testCollisionsWith(colliderLayers[CollisionLayer.COLLISION_LAYER_TERRAIN.ordinal()]);
    }

    private void updatePositions() {
        for(DynamicPoint dynamicPoint : dynamicPointBufferedList.elements) {
            dynamicPoint.applyPhysics();
        }
    }

    private void updateMovements() {
        for(DynamicPoint dynamicPoint : dynamicPointBufferedList.elements) {
            dynamicPoint.updatePhysics();
        }
    }

    private void updateColliderLists() {
        for (ColliderLayer colliderLayer : colliderLayers) {
            colliderLayer.flush();
        }
        instance.dynamicPointBufferedList.flush();
    }

    public static void addCollider(Collider collider, CollisionLayer layer){
        instance.colliderLayers[layer.ordinal()].addCollider(collider);
    }

    public static void removeCollider(Collider collider){
        for (ColliderLayer colliderLayer : instance.colliderLayers) {
            colliderLayer.removeCollider(collider);
        }
    }

    public static void addDynamicPoint(DynamicPoint dynamicPoint) {
        instance.dynamicPointBufferedList.addElement(dynamicPoint);
    }

    public static void removeDynamicPoint(DynamicPoint dynamicPoint) {
        instance.dynamicPointBufferedList.removeElement(dynamicPoint);
    }

    private void initColliderLayers() {
        for (int i = 0; i < colliderLayers.length; i++) {
            colliderLayers[i] = new ColliderLayer();
        }
    }

    public static PhysicEngine getInstance() {
        if(instance!=null)return instance;
        return new PhysicEngine();
    }
}
