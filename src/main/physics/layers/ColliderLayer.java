package main.physics.layers;

import main.physics.Collision;
import main.physics.colliders.Collider;
import main.utils.containers.BufferedList;
import main.utils.vectors.Vec2;

public class ColliderLayer {
    private final BufferedList<Collider> colliderList = new BufferedList<>();

    public ColliderLayer testCollisionsWith(ColliderLayer colliderLayer) {
        for(Collider collider1 : colliderList.elements){
            for(Collider collider2 : colliderLayer.getColliderList().elements){
                if(collider2 == collider1) continue;
                Vec2 relativeVelocity = Vec2.add(collider1.getVelocity(),collider2.getVelocity());
                Collision collision = collider1.doCollide(collider2, relativeVelocity);
                if(collision == null)continue;
                collider1.onCollide(Collision.getReverseCollision(collision));
                collider2.onCollide(collision);
            }
        }
        return this;
    }

    public void addCollider(Collider collider) {
        colliderList.addElement(collider);
    }

    public void removeCollider(Collider collider) {
        colliderList.removeElement(collider);
    }

    public void flush() {
        colliderList.flush();
    }

    public BufferedList<Collider> getColliderList() {
        return colliderList;
    }
}
