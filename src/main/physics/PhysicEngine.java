package main.physics;

import main.game.DynamicSprite;
import main.physics.colliders.Collider;
import main.utils.Engine;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine implements Engine {
    private static PhysicEngine instance;

    private final List<Collider> colliderList_layer_terrain =new ArrayList<>(); // Terrain
    private final List<Collider> colliderList_layer_allies =new ArrayList<>(); // Allies
    private final List<Collider> colliderList_layer_enemies =new ArrayList<>(); // Enemies
    private final List<Collider> colliderList_layer_ally_projectiles =new ArrayList<>(); // Ally projectiles
    private final List<Collider> colliderList_layer_enemy_projectiles =new ArrayList<>(); // Enemy projectiles

    private List<Collider> collidersToRemove=new ArrayList<>();
    private List<Collider> collidersToAdd_layer_terrain =new ArrayList<>();
    private List<Collider> collidersToAdd_layer_allies =new ArrayList<>();
    private List<Collider> collidersToAdd_layer_enemies =new ArrayList<>();
    private List<Collider> collidersToAdd_layer_ally_projectiles =new ArrayList<>();
    private List<Collider> collidersToAdd_layer_enemy_projectiles =new ArrayList<>();

    public PhysicEngine() {
        if(instance==null)instance=this;
    }

    public static void addCollider(Collider collider, CollisionLayers layer){
        switch (layer){
            case COLLISION_LAYER_TERRAIN            -> instance.collidersToAdd_layer_terrain.add(collider);
            case COLLISION_LAYER_ALLIES             -> instance.collidersToAdd_layer_allies.add(collider);
            case COLLISION_LAYER_ENNEMIES           -> instance.collidersToAdd_layer_enemies.add(collider);
            case COLLISION_LAYER_ALLY_PROJECTILES   -> instance.collidersToAdd_layer_ally_projectiles.add(collider);
            case COLLISION_LAYER_ENNEMY_PROJECTILES -> instance.collidersToAdd_layer_enemy_projectiles.add(collider);
        }
    }

    public static void removeCollider(Collider collider){
        instance.collidersToRemove.add(collider);
    }

    public static PhysicEngine getInstance() {
        if(instance!=null)return instance;
        return new PhysicEngine();
    }

    @Override
    public void update() {
        for (Collider colliderAlly : colliderList_layer_allies){
            DynamicSprite dynamicSprite=colliderAlly.getParent();
            if(dynamicSprite==null)continue;

            double currentInverseFriction=1;
            Vec2 velocity=dynamicSprite.getCurrentVelocity();
            BVec2 canMove=new BVec2();

            for(Collider colliderTerrain : colliderList_layer_terrain){
                Collision collision=colliderAlly.doCollide(colliderTerrain,velocity);
                if(collision==null)continue;
                colliderAlly.onCollide(colliderTerrain.getColliderType());
                colliderTerrain.onCollide(colliderAlly.getColliderType());
                boolean doesPreventMovement = colliderTerrain.getColliderType()==ColliderType.SOLID_INERT ||colliderTerrain.getColliderType()==ColliderType.SOLID_DAMAGE_DEALER;
                if(collision.collisions.x&&doesPreventMovement){
                    canMove.x=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderTerrain.getFriction());
                }
                if(collision.collisions.y&&doesPreventMovement){
                    canMove.y=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderTerrain.getFriction());
                }
                if(canMove.isFalse()){
                    break;
                }
            }

            for(Collider colliderEnemy : colliderList_layer_enemies){
                Collision collision=colliderAlly.doCollide(colliderEnemy,velocity);
                if(collision==null)continue;
                colliderAlly.onCollide(colliderEnemy.getColliderType());
                colliderEnemy.onCollide(colliderAlly.getColliderType());
                boolean doesPreventMovement = colliderEnemy.getColliderType()==ColliderType.SOLID_INERT || colliderEnemy.getColliderType()==ColliderType.SOLID_DAMAGE_DEALER;
                if(collision.collisions.x&&doesPreventMovement){
                    canMove.x=false;
                    currentInverseFriction=Math.min(currentInverseFriction, colliderEnemy.getFriction());
                }
                if(collision.collisions.y&&doesPreventMovement){
                    canMove.y=false;
                    currentInverseFriction=Math.min(currentInverseFriction, colliderEnemy.getFriction());
                }
                if(canMove.isFalse()){
                    break;
                }
            }

            dynamicSprite.goToNextPosition(canMove,currentInverseFriction);
        }

        for (Collider colliderEnemy : colliderList_layer_enemies){
            DynamicSprite dynamicSprite=colliderEnemy.getParent();
            if(dynamicSprite==null)continue;

            double currentInverseFriction=1;
            Vec2 velocity=dynamicSprite.getCurrentVelocity();
            BVec2 canMove=new BVec2();

            for (Collider colliderAllyProjectile : colliderList_layer_ally_projectiles){
                Collision collision=colliderEnemy.doCollide(colliderAllyProjectile,velocity);
                if(collision==null)continue;
                colliderEnemy.onCollide(colliderAllyProjectile.getColliderType());
                colliderAllyProjectile.onCollide(colliderEnemy.getColliderType());
                boolean doesPreventMovement = colliderAllyProjectile.getColliderType()==ColliderType.SOLID_INERT ||colliderAllyProjectile.getColliderType()==ColliderType.SOLID_DAMAGE_DEALER;
                if(collision.collisions.x&&doesPreventMovement){
                    canMove.x=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderAllyProjectile.getFriction());
                }
                if(collision.collisions.y&&doesPreventMovement){
                    canMove.y=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderAllyProjectile.getFriction());
                }
                if(canMove.isFalse()){
                    break;
                }
            }
        }

        for(Collider collider : collidersToRemove){
            instance.colliderList_layer_terrain.remove(collider);
            instance.colliderList_layer_allies.remove(collider);
            instance.colliderList_layer_enemies.remove(collider);
            instance.colliderList_layer_ally_projectiles.remove(collider);
            instance.colliderList_layer_enemy_projectiles.remove(collider);
        }

        instance.colliderList_layer_terrain.addAll(collidersToAdd_layer_terrain);
        instance.colliderList_layer_allies.addAll(collidersToAdd_layer_allies);
        instance.colliderList_layer_enemies.addAll(collidersToAdd_layer_enemies);
        instance.colliderList_layer_ally_projectiles.addAll(collidersToAdd_layer_ally_projectiles);
        instance.colliderList_layer_enemy_projectiles.addAll(collidersToAdd_layer_enemy_projectiles);

        instance.collidersToRemove=new ArrayList<>();
        instance.collidersToAdd_layer_terrain =new ArrayList<>();
        instance.collidersToAdd_layer_allies =new ArrayList<>();
        instance.collidersToAdd_layer_enemies =new ArrayList<>();
        instance.collidersToAdd_layer_ally_projectiles =new ArrayList<>();
        instance.collidersToAdd_layer_enemy_projectiles =new ArrayList<>();
    }
}
