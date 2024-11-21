package game.projectiles;

import game.DynamicSprite;
import physics.Collider;
import utils.IVec2;

public abstract class Projectile extends DynamicSprite {
    public Projectile(IVec2 position, Collider collider, String texturePath, IVec2 projectileSpeed) {
        super(position, collider, texturePath,projectileSpeed);
    }
}
