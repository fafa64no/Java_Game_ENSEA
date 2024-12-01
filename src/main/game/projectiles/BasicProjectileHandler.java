package main.game.projectiles;

import main.physics.ColliderType;
import main.physics.layers.CollisionLayer;
import main.physics.colliders.PointCollider;
import main.rendering.vfx.VfxType;
import main.utils.containers.SizedTextureArray;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

public abstract class BasicProjectileHandler implements ProjectileHandler {
    protected final SizedTextureArray textures;

    protected final Projectile[] projectiles = new Projectile[Config.maxProjectilesPerProjectileHandler];
    protected int projectilePointer = 0;

    protected final int projectileLifeSpan;

    protected final double defaultProjectileSpeed;
    protected final double defaultModifier;

    protected final VfxType vfxType;

    protected final int animationSpeed;
    protected final int framesToSkip;

    protected final ColliderType colliderType;

    public BasicProjectileHandler(
            SizedTextureArray textures,
            int projectileLifeSpan,
            double defaultProjectileSpeed,
            double defaultModifier,
            VfxType vfxType,
            ColliderType colliderType,
            int animationSpeed,
            int framesToSkip
    ) {
        this.textures = textures;
        this.projectileLifeSpan = projectileLifeSpan;
        this.defaultProjectileSpeed = defaultProjectileSpeed;
        this.defaultModifier = defaultModifier;
        this.colliderType = colliderType;
        this.vfxType = vfxType;
        this.animationSpeed = animationSpeed;
        this.framesToSkip = framesToSkip;
    }

    protected void genProjectile(
            Vec2 initialPosition,
            double rotation,
            double projectileSpeed,
            double modifier,
            CollisionLayer collisionLayer
    ) {
        PointCollider collider = new PointCollider(
        false,
            0,
            modifier,
            colliderType,
            collisionLayer,
            new Vec2(),
            null
        );
        projectiles[projectilePointer] = new Projectile(
            collider,
            initialPosition,
            projectileSpeed,
            rotation,
            textures.textureCount,
            projectileLifeSpan,
            animationSpeed,
            this,
            projectilePointer
        );
    }

    @Override
    public void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            double projectileSpeed,
            double modifier,
            CollisionLayer collisionLayer
    ) {
        if (projectiles[projectilePointer] == null) {
            genProjectile(initialPosition,rotation,projectileSpeed,modifier,collisionLayer);
        } else {
            System.out.println("Too many projectiles");
        }
        projectilePointer=(projectilePointer+1)%projectiles.length;
    }

    @Override
    public void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            CollisionLayer collisionLayer
    ) {
        fireInDirection(
            initialPosition,
            rotation,
            defaultProjectileSpeed,
            defaultModifier,
            collisionLayer
        );
    }

    @Override
    public void removeProjectile(int id) {
        projectiles[id]=null;
    }

    @Override
    public Projectile[] getProjectiles() {
        return projectiles;
    }

    @Override
    public SizedTextureArray getTextures() {
        return textures;
    }
}
