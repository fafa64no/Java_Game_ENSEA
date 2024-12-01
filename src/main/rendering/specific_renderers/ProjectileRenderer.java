package main.rendering.specific_renderers;

import main.game.projectiles.Projectile;
import main.game.projectiles.ProjectileHandler;
import main.rendering.Displayable;
import main.rendering.RenderEngine;
import main.rendering.layers.RenderingLayer;
import main.utils.containers.SizedTextureArray;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class ProjectileRenderer extends JPanel implements Displayable {
    private final List<ProjectileHandler> projectileHandlers = new ArrayList<>();
    private final RenderingLayer renderingLayer;

    public ProjectileRenderer(RenderingLayer renderingLayer) {
        this.renderingLayer = renderingLayer;
        this.setOpaque(false);
        addToRenderList();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g.create();
        RenderEngine.getCurrentCamera().transformGraphicsToCamera(g2d);
        for (ProjectileHandler projectileHandler : projectileHandlers) {
            paintProjectiles(g2d, projectileHandler);
        }
    }

    private void paintProjectiles(Graphics2D g2d, ProjectileHandler projectileHandler) {
        for (Projectile projectile : projectileHandler.getProjectiles()) {
            paintProjectile(g2d, projectile, projectileHandler.getTextures());
        }
    }

    private void paintProjectile(Graphics2D g2d, Projectile projectile, SizedTextureArray textures) {
        if (projectile == null) return;
        double posX = projectile.getPosition().x - 0.5 * textures.textureSize;
        double posY = projectile.getPosition().y - 0.5 * textures.textureSize;
        double rotationCenter = 0.5 * textures.textureSize;

        AffineTransform tx = new AffineTransform();
        tx.translate(posX,posY);
        tx.rotate(projectile.getRotation(),rotationCenter,rotationCenter);

        g2d.drawRenderedImage(textures.textures[projectile.getCurrentAnimationFrame()],tx);
    }

    public void addProjectileHandle(ProjectileHandler projectileHandler) {
        projectileHandlers.add(projectileHandler);
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
    public void addToRenderList() {
        RenderEngine.addToRenderList(this, renderingLayer);
    }

    @Override
    public void removeFromRenderList() {
        RenderEngine.removeFromRenderList(this);
    }

}
