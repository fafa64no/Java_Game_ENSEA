package main.game.projectiles;

import main.game.GameEngine;
import main.physics.ColliderType;
import main.physics.CollisionLayer;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayer;
import main.rendering.vfx.VfxType;
import main.utils.RequiresUpdates;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BasicProjectileHandler extends JPanel implements ProjectileHandler, Displayable, RequiresUpdates {
    protected final BufferedImage[] textures;
    protected final Projectile[] projectiles = new Projectile[Config.maxProjectilesPerProjectileHandler];
    protected int projectilePointer = 0;

    protected final int projectileLifeSpan;
    protected final double projectileSpeed;
    protected final double modifier;
    protected final CollisionLayer collisionLayer;
    protected final ColliderType colliderType;

    protected final VfxType vfxType;

    protected final int animationSpeed;
    protected final int invisibilityFrames;

    public BasicProjectileHandler(BufferedImage[] textures, int projectileLifeSpan, double projectileSpeed, double modifier, CollisionLayer collisionLayer, VfxType vfxType, ColliderType colliderType, int animationSpeed, int invisibilityFrames) {
        this.textures = textures;
        this.projectileLifeSpan = projectileLifeSpan;
        this.projectileSpeed = projectileSpeed;
        this.modifier = modifier;
        this.collisionLayer = collisionLayer;
        this.colliderType = colliderType;
        this.vfxType = vfxType;
        this.animationSpeed = animationSpeed;
        this.invisibilityFrames = invisibilityFrames;

        this.setOpaque(false);

        RenderEngine.addToRenderList(this, RenderingLayer.RENDERING_LAYER_TANK);
        GameEngine.addRequiresUpdates(this);
    }

    @Override
    public void fireInDirection(Vec2 initialPosition, double rotation) {
        if(projectiles[projectilePointer]!=null){
            System.out.println("Too many projectiles");
            return;
        }
        projectiles[projectilePointer]=new Projectile(
                projectileLifeSpan,
                invisibilityFrames,
                initialPosition,
                projectileSpeed,
                rotation,
                projectilePointer,
                this,
                collisionLayer,
                modifier,
                textures.length,
                animationSpeed,
                vfxType,
                colliderType
        );
        projectilePointer=(projectilePointer+1)%projectiles.length;
    }

    @Override
    public void updateRemainingTime() {
        for(int i=0; i<projectiles.length; i++){
            Projectile projectile=projectiles[i];
            if(projectile==null)continue;
            projectile.decrementTimeRemaining();
            projectile.incrementPosition();
        }
    }

    @Override
    public void removeProjectile(int id) {
        projectiles[id]=null;
    }

    @Override
    public void draw() {
        RenderEngine.getInstance().remove(this);
        RenderEngine.getInstance().add(this);
    }

    @Override
    public void clear() {
        RenderEngine.getInstance().remove(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        for(Projectile projectile : projectiles){
            if(projectile==null)continue;
            g2d.translate(projectile.getCurrentPosition().x- (double) Config.smallTileSize /2,projectile.getCurrentPosition().y- (double) Config.smallTileSize /2);
            g2d.rotate(projectile.rotation,(double) Config.smallTileSize /2,(double) Config.smallTileSize /2);
            g2d.drawRenderedImage(textures[projectile.getCurrentAnimationFrame()],null);
            g2d.rotate(-projectile.rotation,(double) Config.smallTileSize /2,(double) Config.smallTileSize /2);
            g2d.translate(-projectile.getCurrentPosition().x+ (double) Config.smallTileSize /2,-projectile.getCurrentPosition().y+ (double) Config.smallTileSize /2);
        }
    }
}
