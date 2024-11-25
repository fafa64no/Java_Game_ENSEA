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

    private final List<Collider> colliderList_layer0=new ArrayList<>(); // Terrain
    private final List<Collider> colliderList_layer1=new ArrayList<>(); // Allies
    private final List<Collider> colliderList_layer2=new ArrayList<>(); // Enemies
    private final List<Collider> colliderList_layer3=new ArrayList<>(); // Ally projectiles
    private final List<Collider> colliderList_layer4=new ArrayList<>(); // Enemy projectiles

    public PhysicEngine() {
        if(instance==null)instance=this;
    }

    public static void addCollider(Collider collider, CollisionLayers layer){
        switch (layer){
            case COLLISION_LAYER_TERRAIN            -> instance.colliderList_layer0.add(collider);
            case COLLISION_LAYER_ALLIES             -> instance.colliderList_layer1.add(collider);
            case COLLISION_LAYER_ENNEMIES           -> instance.colliderList_layer2.add(collider);
            case COLLISION_LAYER_ALLY_PROJECTILES   -> instance.colliderList_layer3.add(collider);
            case COLLISION_LAYER_ENNEMY_PROJECTILES -> instance.colliderList_layer4.add(collider);
        }
    }

    public static void removeCollider(Collider collider){
        instance.colliderList_layer0.remove(collider);
        instance.colliderList_layer1.remove(collider);
        instance.colliderList_layer2.remove(collider);
        instance.colliderList_layer3.remove(collider);
        instance.colliderList_layer4.remove(collider);
    }

    public static PhysicEngine getInstance() {
        if(instance!=null)return instance;
        return new PhysicEngine();
    }

    @Override
    public void update() {
        for (Collider colliderAlly : colliderList_layer1){
            DynamicSprite dynamicSprite=colliderAlly.getParent();
            if(dynamicSprite==null)continue;

            double currentInverseFriction=1;
            Vec2 velocity=dynamicSprite.getCurrentVelocity();
            BVec2 canMove=new BVec2();

            for(Collider colliderTerrain : colliderList_layer0){
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

            for(Collider colliderEnemy : colliderList_layer2){
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

        for (Collider colliderEnemy : colliderList_layer2){
            DynamicSprite dynamicSprite=colliderEnemy.getParent();
            if(dynamicSprite==null)continue;

            double currentInverseFriction=1;
            Vec2 velocity=dynamicSprite.getCurrentVelocity();
            BVec2 canMove=new BVec2();

            for (Collider colliderAllyProjectile : colliderList_layer3){
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
    }
}
