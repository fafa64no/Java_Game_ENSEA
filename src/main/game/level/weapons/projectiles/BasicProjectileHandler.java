package main.game.level.weapons.projectiles;

import main.physics.ColliderType;
import main.physics.layers.CollisionLayer;
import main.physics.colliders.PointCollider;
import main.rendering.layers.RenderingLayer;
import main.rendering.sprites.Sprite;
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
            CollisionLayer collisionLayer,
            RenderingLayer renderingLayer
    ) {
        projectiles[projectilePointer] = new Projectile(
            initialPosition,
            projectileSpeed,
            rotation,
            textures.textureCount,
            projectileLifeSpan,
            animationSpeed,
            this,
            getProjectileSprite(renderingLayer),
            projectilePointer
        );
        setProjectileCollider(modifier, collisionLayer);
    }

    protected void setProjectileCollider(
            double modifier,
            CollisionLayer collisionLayer
    ) {
        projectiles[projectilePointer].setMainCollider(new PointCollider(
                false,
                0,
                modifier,
                colliderType,
                collisionLayer,
                new Vec2(),
                VfxType.VFX_PIERCING_METAL,
                0,
                200,
                null
        ));
    }

    protected abstract Sprite getProjectileSprite(RenderingLayer renderingLayer);

    @Override
    public void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            double projectileSpeed,
            double modifier,
            CollisionLayer collisionLayer,
            RenderingLayer renderingLayer
    ) {
        if (projectiles[projectilePointer] == null) {
            genProjectile(initialPosition,rotation,projectileSpeed,modifier,collisionLayer,renderingLayer);
        } else {
            System.out.println("Too many projectiles");
        }
        projectilePointer = (projectilePointer + 1) % projectiles.length;
    }

    @Override
    public void fireInDirection(
            Vec2 initialPosition,
            double rotation,
            CollisionLayer collisionLayer,
            RenderingLayer renderingLayer
    ) {
        fireInDirection(
            initialPosition,
            rotation,
            defaultProjectileSpeed,
            defaultModifier,
            collisionLayer,
            renderingLayer
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
