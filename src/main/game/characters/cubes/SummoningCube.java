package main.game.characters.cubes;

import main.game.characters.LifeStates;
import main.game.characters.cubes.cube_variants.ArtilleryCube;
import main.game.characters.cubes.cube_variants.BeaconCube;
import main.game.characters.cubes.cube_variants.GatlingCube;
import main.game.characters.cubes.cube_variants.GatlingWheelsCube;
import main.rendering.CubeRenderer;
import main.utils.data.Config;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public abstract class SummoningCube extends RangedCube{
    private final int numberOfSpawns;
    private final double spawnDistance;

    private final CubeHead spawnCubeHead;

    public SummoningCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures, double rotationSpeed, double maxHealth, int animationDuration, CubeHead cubeHead, int textureSize, int firingDelay, double followRange, int numberOfSpawns, double spawnDistance, CubeHead spawnCubeHead) {
        super(position, texture, deadTexture, deploymentTextures, retractionTextures, attackTextures, rotationSpeed, maxHealth, animationDuration, cubeHead, textureSize, null, firingDelay, followRange, 0);
        this.numberOfSpawns = numberOfSpawns;
        this.spawnDistance = spawnDistance;
        this.spawnCubeHead = spawnCubeHead;
    }

    protected void spawnCube(){
        for(int i=0; i<numberOfSpawns;i++){
            Vec2 spawnOffset = Vec2.multiply(new Vec2(1,0),spawnDistance).rotateBy(2.0*i*Math.PI/numberOfSpawns);
            switch (spawnCubeHead){
                case CUBE_HEAD_MACHINE_GUN_WHEELS -> CubeRenderer.addCube(new GatlingWheelsCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_BEACON -> CubeRenderer.addCube(new BeaconCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_ARTILLERY -> CubeRenderer.addCube(new ArtilleryCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_MACHINE_GUN -> CubeRenderer.addCube(new GatlingCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_NONE -> CubeRenderer.addCube(new BasicCube(Vec2.add(spawnOffset,position)));
            }
        }
    }

    @Override
    protected void attackingState() {
        if(--remainingAnimationTime<0){
            lifeState = LifeStates.CURRENTLY_PURSUING;
        }
        if(rotationSpeed > Config.minimumVectorSize)computeNewRotation();
        if(remainingFiringDelay<=0){
            remainingFiringDelay = firingDelay;
            spawnCube();
        }
    }

    @Override
    protected boolean isRotationGood() {
        return true;
    }
}
