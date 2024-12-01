package main.game.level;

import main.game.vehicles.cubes.cube_variants.*;
import main.physics.ColliderType;
import main.physics.layers.CollisionLayer;
import main.physics.colliders.Collider;
import main.physics.colliders.TileMapCollider;
import main.rendering.*;
import main.rendering.layers.RenderingLayer;
import main.rendering.specific_renderers.LeavesRenderer;
import main.rendering.specific_renderers.SpriteRenderer;
import main.rendering.specific_renderers.TileMapRenderer;
import main.utils.data.Config;
import main.utils.data.TextureConfig;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final char[][] map;

    private final int tileSize;
    private final double inverseTileSize;
    private final Vec2 mapOffset = new Vec2();

    private final List<Displayable> renderers = new ArrayList<>();
    private final SpriteRenderer spriteRenderer = new SpriteRenderer();
    private final List<Collider> colliders = new ArrayList<>();

    public Level(IVec2 size){
        this.map = LevelGenerator.genTerrain(size);

        this.tileSize = TextureConfig.smallTileSize;
        this.inverseTileSize = 1.0 / tileSize;
        this.mapOffset.x = - 0.5 * map[0].length * tileSize;
        this.mapOffset.y = - 0.5 * map.length * tileSize;

        initRenderers();
        initColliders();
        initVehicles();
    }

    public void loadLevel(){
        for (Collider collider : this.colliders)
            collider.addColliderToColliderList();
        for (Displayable renderer : renderers)
            renderer.addToRenderList();
        spriteRenderer.load();
        RenderEngine.paint();
    }

    public void unloadLevel(){
        for (Collider collider : this.colliders)
            collider.removeColliderFromColliderList();
        for (Displayable renderer : renderers)
            renderer.removeFromRenderList();
        spriteRenderer.unLoad();
        RenderEngine.paint();
    }

    private void initRenderers() {
        renderers.add(new TileMapRenderer(
                this,
                this.tileSize,
                RenderingLayer.RENDERING_LAYER_TERRAIN_MIDDLE
        ));

        renderers.add(new LeavesRenderer(
                this,
                this.tileSize,
                RenderingLayer.RENDERING_LAYER_LEAVES_MIDDLE
        ));
    }

    private void initColliders() {
        colliders.add(new TileMapCollider(
                false,
                0.5,
                10,
                ColliderType.SOLID_THICK_INERT,
                CollisionLayer.COLLISION_LAYER_TERRAIN,
                new Vec2(),
                this,
                this.tileSize
        ));
    }

    private void initVehicles() {
        for (int x=0;x<map[0].length;x++) {
            for (int y = 0; y < map.length; y++) {
                Vec2 spawnPosition = Vec2.add(
                        Vec2.multiply(new Vec2(x, y), tileSize),
                        mapOffset,
                        new Vec2(tileSize * 0.5)
                );
                initVehicleAtPosition(spawnPosition, x, y);
            }
        }
    }

    private void initVehicleAtPosition(Vec2 spawnPosition, int x, int y) {
        switch (map[y][x]){
            case '1' ->
                groundCubeRenderer.addCube(new GatlingCube(spawnPosition.copy()));
            case '2' ->
                groundCubeRenderer.addCube(new GatlingWheelsCube(spawnPosition.copy()));
            case '3' ->
                groundCubeRenderer.addCube(new ArtilleryCube(spawnPosition.copy()));
            case '4' ->
                groundCubeRenderer.addCube(new BeaconCube(spawnPosition.copy()));
            case '5' ->
                flyingCubeRenderer.addCube(new FighterCube(spawnPosition.copy()));
            case '6' ->
                flyingCubeRenderer.addCube(new FighterCube(spawnPosition.copy()));
            case '0' -> {}
            case '7', '8', '9' -> System.out.println("Cube not implemented.");
        }
    }

    public double getGroundSpeed(Vec2 position){
        int tilePosX = Math.clamp((int) Math.round((position.x - mapOffset.x) * inverseTileSize),0,map[0].length-1);
        int tilePosY = Math.clamp((int) Math.round((position.y - mapOffset.y) * inverseTileSize),0,map.length-1);
        char tile = map[tilePosY][tilePosX];
        return switch (tile) {
            case 'P' -> Config.pathSpeedModifier;
            case 'M' -> 0.1;
            default -> 1;
        };
    }

    public char[][] getMap() {
        return map;
    }

    public Vec2 getMapOffset() {
        return mapOffset;
    }

    public SpriteRenderer getSpriteRenderer() {
        return spriteRenderer;
    }
}
