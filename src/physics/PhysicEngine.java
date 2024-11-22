package physics;

import game.DynamicSprite;
import utils.Engine;
import utils.vectors.BVec2;
import utils.vectors.IVec2;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine implements Engine {
    private static PhysicEngine instance;

    private final List<Collider> colliderList_layer0=new ArrayList<>();
    private final List<Collider> colliderList_layer1=new ArrayList<>();
    private final List<Collider> colliderList_layer2=new ArrayList<>();
    private final List<Collider> colliderList_layer3=new ArrayList<>();
    private final List<Collider> colliderList_layer4=new ArrayList<>();

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
        return instance;
    }

    @Override
    public void update() {
        for (Collider colliderAlly : colliderList_layer1){
            DynamicSprite dynamicSprite=colliderAlly.getParent();
            if(dynamicSprite==null)continue;

            double currentInverseFriction=1;
            IVec2 velocity=dynamicSprite.getCurrentVelocity();
            BVec2 canMove=new BVec2();

            for(Collider colliderTerrain : colliderList_layer0){
                BVec2 collisions=colliderAlly.doCollide(colliderTerrain,velocity);
                if(!collisions.isFalse())colliderTerrain.onCollide();
                if(collisions.x){
                    canMove.x=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderTerrain.getFriction());
                }
                if(collisions.y){
                    canMove.y=false;
                    currentInverseFriction=Math.min(currentInverseFriction,colliderTerrain.getFriction());
                }
                if(canMove.isFalse()){
                    System.out.println("Colliding");
                    break;
                }
            }
            dynamicSprite.goToNextPosition(canMove,currentInverseFriction);
            colliderAlly.onCollide();
        }

//        for (DynamicSprite dynamicSprite : dynamicSprites){
//            IVec2 nextPosition=dynamicSprite.computeNewPosition();
//            BVec2 canMove=new BVec2();
//            double currentInverseFriction=1;
//            for (BoxCollider staticSolidCollider : staticSolidColliders){
//                BVec2 collisions= staticSolidCollider.doCollide(dynamicSprite.getCollider(),dynamicSprite.getPosition(),nextPosition);
//                if(collisions.x){
//                    canMove.x=false;
//                    currentInverseFriction=Math.min(currentInverseFriction, staticSolidCollider.getFriction());
//                }
//                if(collisions.y){
//                    canMove.y=false;
//                    currentInverseFriction=Math.min(currentInverseFriction, staticSolidCollider.getFriction());
//                }
//                if(canMove.isFalse())     break;
//            }
//            dynamicSprite.goToNextPosition(canMove,currentInverseFriction);
//        }
    }
}
