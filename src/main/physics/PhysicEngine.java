package main.physics;

import main.game.DynamicSprite;
import main.physics.colliders.Collider;
import main.utils.Engine;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;

import java.util.List;

public class PhysicEngine implements Engine {
    private static PhysicEngine instance;

    private final BufferedList<Collider> collider_layer_terrain = new BufferedList<>();
    private final BufferedList<Collider> collider_layer_allies = new BufferedList<>();
    private final BufferedList<Collider> collider_layer_enemies = new BufferedList<>();
    private final BufferedList<Collider> collider_layer_ally_projectiles = new BufferedList<>();
    private final BufferedList<Collider> collider_layer_enemy_projectiles = new BufferedList<>();

    private final BufferedList<DynamicSprite> dynamicSpriteList = new BufferedList<>();

    public PhysicEngine() {
        if(instance==null)instance=this;
    }

    public static PhysicEngine getInstance() {
        if(instance!=null)return instance;
        return new PhysicEngine();
    }

    @Override
    public void update() {
        testAllCollisions();
        updatePositions();
        updateColliderLists();
    }

    private void testAllCollisions() {
        testCollisionsBetweenLayers(collider_layer_allies.elements,           collider_layer_terrain.elements);
        testCollisionsBetweenLayers(collider_layer_allies.elements,           collider_layer_enemies.elements);
        testCollisionsBetweenLayers(collider_layer_allies.elements,           collider_layer_enemy_projectiles.elements);
        testCollisionsBetweenLayers(collider_layer_allies.elements,           collider_layer_allies.elements);

        testCollisionsBetweenLayers(collider_layer_enemies.elements,          collider_layer_terrain.elements);
        testCollisionsBetweenLayers(collider_layer_enemies.elements,          collider_layer_ally_projectiles.elements);

        testCollisionsBetweenLayers(collider_layer_ally_projectiles.elements, collider_layer_terrain.elements);

        testCollisionsBetweenLayers(collider_layer_enemy_projectiles.elements,collider_layer_terrain.elements);
    }

    private void testCollisionsBetweenLayers(List<Collider> colliderLayer1, List<Collider> colliderLayer2) {
        for(Collider collider1 : colliderLayer1){
            for(Collider collider2 : colliderLayer2){
                Vec2 relativeVelocity = Vec2.add(collider1.getVelocity(),collider2.getVelocity());
                Collision collision = collider1.doCollide(collider2, relativeVelocity);
                if(collision == null)continue;
                collider1.onCollide(Collision.getReverseCollision(collision));
                collider2.onCollide(collision);
            }
        }
    }

    private void updatePositions() {
        for(DynamicSprite dynamicSprite : dynamicSpriteList.elements) {
            dynamicSprite.goToNextPosition();
        }
    }

    private void updateColliderLists() {
        instance.collider_layer_terrain.flush();
        instance.collider_layer_allies.flush();
        instance.collider_layer_enemies.flush();
        instance.collider_layer_ally_projectiles.flush();
        instance.collider_layer_enemy_projectiles.flush();

        instance.dynamicSpriteList.flush();
    }



    public static void addCollider(Collider collider, CollisionLayer layer){
        switch (layer){
            case COLLISION_LAYER_TERRAIN            -> instance.collider_layer_terrain.addElement(collider);
            case COLLISION_LAYER_ALLIES             -> instance.collider_layer_allies.addElement(collider);
            case COLLISION_LAYER_ENNEMIES           -> instance.collider_layer_enemies.addElement(collider);
            case COLLISION_LAYER_ALLY_PROJECTILES   -> instance.collider_layer_ally_projectiles.addElement(collider);
            case COLLISION_LAYER_ENNEMY_PROJECTILES -> instance.collider_layer_enemy_projectiles.addElement(collider);
        }
    }

    public static void removeCollider(Collider collider){
        instance.collider_layer_terrain.removeElement(collider);
        instance.collider_layer_allies.removeElement(collider);
        instance.collider_layer_enemies.removeElement(collider);
        instance.collider_layer_ally_projectiles.removeElement(collider);
        instance.collider_layer_enemy_projectiles.removeElement(collider);
    }

    public static void addDynamicSprite(DynamicSprite dynamicSprite) {
        instance.dynamicSpriteList.addElement(dynamicSprite);
    }

    public static void removeDynamicSprite(DynamicSprite dynamicSprite) {
        instance.dynamicSpriteList.removeElement(dynamicSprite);
    }
}
