package main.game.characters.cubes;

import main.game.characters.Character;
import main.physics.ColliderType;
import main.physics.CollisionLayers;
import main.physics.PhysicEngine;
import main.physics.colliders.BoxCollider;
import main.physics.colliders.Collider;
import main.rendering.vfx.Vfx;
import main.rendering.vfx.VfxType;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BasicCube extends Character implements RedCube {
    protected Collider collider;
    protected Collider damageZone;

    public final int textureSize;

    protected final List<DecorativeFollowerCube> decorativeFollowerCubes;

    public BasicCube(Vec2 position) {
        super(position, DataGen.getBasicCubeTexture(), DataGen.getBasicCubeDeadTexture(), 1, new IVec2(Config.largeTileSize,Config.largeTileSize), Config.basicCubeHealth);
        this.textureSize = Config.largeTileSize;
        genColliders();
        this.decorativeFollowerCubes = new ArrayList<>();
    }

    protected BasicCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double maxHealth, int textureSize) {
        super(position, texture, deadTexture, 1, new IVec2(Config.largeTileSize,Config.largeTileSize), maxHealth);
        this.textureSize = textureSize;
        genColliders();
        this.decorativeFollowerCubes = new ArrayList<>();
    }

    protected BasicCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double maxHealth, int textureSize, List<DecorativeFollowerCube> decorativeFollowerCubes) {
        super(position, texture, deadTexture, 1, new IVec2(Config.largeTileSize,Config.largeTileSize), maxHealth);
        this.textureSize = textureSize;
        genColliders();
        this.decorativeFollowerCubes = decorativeFollowerCubes;
    }

    protected void genColliders(){
        collider = new BoxCollider(
                new Vec2(-16,-16),
                new Vec2(16,16),
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
                ColliderType.NONE_DAMAGE_DEALER,
                3,
                VfxType.VFX_ELECTRICITY,
                15
        );
        PhysicEngine.addCollider(collider, CollisionLayers.COLLISION_LAYER_ENNEMIES);
        PhysicEngine.addCollider(damageZone, CollisionLayers.COLLISION_LAYER_ENNEMIES);
        collider.setOffset();
        damageZone.setOffset();
    }

    @Override
    public int getTextureSize() {
        return textureSize;
    }

    @Override
    public List<DecorativeFollowerCube> getDecorativeFollowerCubes() {
        return decorativeFollowerCubes;
    }

    @Override
    public BufferedImage getTexture() {
        return switch (lifeState){
            case CURRENTLY_DEAD -> deadTexture;
            default -> texture;
        };
    }

    @Override
    public void killYourself() {
        super.killYourself();
        PhysicEngine.removeCollider(damageZone);
        new Vfx(position,Config.largeTileSize, DataGen.getExplosionTextures(),20);
        for(DecorativeFollowerCube decorativeFollowerCube : decorativeFollowerCubes)decorativeFollowerCube.killYourself();
    }

    @Override
    public double getRotation() {
        return 0;
    }
}
