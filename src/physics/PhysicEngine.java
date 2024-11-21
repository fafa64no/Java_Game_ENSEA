package physics;

import game.DynamicSprite;
import utils.BVec2;
import utils.Engine;
import utils.IVec2;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine implements Engine {
    private static PhysicEngine instance;

    private final List<Collider> staticColliders;
    private final List<DynamicSprite> dynamicSprites;

    public PhysicEngine() {
        if(instance==null)instance=this;

        this.dynamicSprites = new ArrayList<>();
        this.staticColliders = new ArrayList<>();
    }

    public static void addStaticCollider(Collider collider){
        instance.staticColliders.add(collider);
    }

    public static void removeStaticCollider(Collider collider){
        instance.staticColliders.remove(collider);
    }

    public static void addDynamicSprite(DynamicSprite sprite){
        instance.dynamicSprites.add(sprite);
    }

    public static void removeDynamicSprite(DynamicSprite sprite){
        instance.dynamicSprites.remove(sprite);
    }

    public static PhysicEngine getInstance() {
        return instance;
    }

    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : dynamicSprites){
            IVec2 nextPosition=dynamicSprite.computeNewPosition();
            BVec2 canMove=new BVec2();
            double currentInverseFriction=1;
            for (Collider staticCollider : staticColliders){
                BVec2 collisions=staticCollider.doCollide(dynamicSprite.getCollider(),dynamicSprite.getPosition(),nextPosition);
                if(collisions.x){
                    canMove.x=false;
                    currentInverseFriction=Math.min(currentInverseFriction,staticCollider.getFriction());
                }
                if(collisions.y){
                    canMove.y=false;
                    currentInverseFriction=Math.min(currentInverseFriction,staticCollider.getFriction());
                }
                if(canMove.isFalse())     break;
            }
            dynamicSprite.goToNextPosition(canMove,currentInverseFriction);
        }
    }
}
