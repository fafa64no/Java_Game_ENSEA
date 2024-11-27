package main.game.projectiles;

import main.game.GameEngine;
import main.physics.CollisionLayers;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MachineGunBullet extends JPanel implements ProjectileHandler, Displayable {
    private static MachineGunBullet instance = null;

    private final BufferedImage texture;
    private final Projectile[] projectiles = new Projectile[Config.maxProjectilesPerProjectileHandler];
    private int projectilePointer = 0;

    public final int projectileLifeSpan = 500;
    public final double projectileSpeed = 10;

    public MachineGunBullet(){
        if(instance==null)instance=this;
        texture = DataGen.getMachineGunBulletShellTexture();

        instance.setOpaque(false);

        RenderEngine.addToRenderList(instance, RenderingLayers.RENDERING_LAYER_TANK);
        GameEngine.addProjectileHandler(instance);
    }

    public static MachineGunBullet getInstance(){
        if(instance!=null)return instance;
        return new MachineGunBullet();
    }

    @Override
    public void fireInDirection(Vec2 initialPosition, double rotation) {
        projectiles[projectilePointer]=new Projectile(
                projectileLifeSpan,
                3,
                initialPosition,
                projectileSpeed,
                rotation,
                projectilePointer,
                this,
                CollisionLayers.COLLISION_LAYER_ENNEMY_PROJECTILES
        );
        projectilePointer=(projectilePointer+1)%projectiles.length;
    }

    @Override
    public void updateLifetimes() {
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
            if(projectile==null||projectile.getInvisibilityFrames()>0)continue;
            g2d.translate(projectile.getCurrentPosition().x- (double) Config.smallTileSize /2,projectile.getCurrentPosition().y- (double) Config.smallTileSize /2);
            g2d.rotate(projectile.rotation,(double) Config.smallTileSize /2,(double) Config.smallTileSize /2);
            g2d.drawRenderedImage(texture,null);
            g2d.rotate(-projectile.rotation,(double) Config.smallTileSize /2,(double) Config.smallTileSize /2);
            g2d.translate(-projectile.getCurrentPosition().x+ (double) Config.smallTileSize /2,-projectile.getCurrentPosition().y+ (double) Config.smallTileSize /2);
        }
    }
}
