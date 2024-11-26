package main.game.characters.cubes;

import main.game.GameEngine;
import main.game.characters.Character;
import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.rendering.RenderEngine;
import main.utils.data.Config;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicCube extends Character implements RedCube{
    private final Collider collider;
    private final Collider damageZone;

    public BasicCube(Vec2 position, BufferedImage texture) {
        super(position, texture, 0, 0, new IVec2(Config.largeTileSize,Config.largeTileSize));
        collider = new BoxCollider(
                new Vec2(-14,-14),
                new Vec2(14,14),
                false,
                0.1,
                new Vec2(),
                this,
                ColliderType.SOLID_INERT
        );
        damageZone = new BoxCollider(
                new Vec2(-Config.cubeCollisionRange,-Config.cubeCollisionRange),
                new Vec2(Config.cubeCollisionRange,Config.cubeCollisionRange),
                false,
                0.1,
                new Vec2(),
                this,
                ColliderType.NONE_DAMAGE_DEALER
        );
        PhysicEngine.addCollider(collider, CollisionLayers.COLLISION_LAYER_ENNEMIES);
        PhysicEngine.addCollider(damageZone, CollisionLayers.COLLISION_LAYER_ENNEMIES);
        collider.setOffset();
        damageZone.setOffset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(RenderEngine.getCurrentCamera().getScale().x,RenderEngine.getCurrentCamera().getScale().y);
        g2d.translate(-RenderEngine.getCurrentCamera().getOffset().x,-RenderEngine.getCurrentCamera().getOffset().y);

        g2d.translate(position.x-Math.round((float) textureSize.x * 0.5), position.y-Math.round((float) textureSize.y * 0.5));

        g2d.drawRenderedImage(texture,null);
    }

    @Override
    public IVec2 getTextureSize() {
        return textureSize;
    }

    @Override
    public BufferedImage getTexture() {
        return texture;
    }

    @Override
    public void killYourself() {
        super.killYourself();
        PhysicEngine.removeCollider(damageZone);
        GameEngine.getCurrentLevel().getCubeRenderer().removeCube(this);
    }
}