package main.game.vehicles.cubes;

import main.game.GameEngine;
import main.game.vehicles.LifeState;
import main.game.vehicles.cubes.cube_variants.*;
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
                case CUBE_HEAD_MACHINE_GUN_WHEELS -> GameEngine.getCurrentLevel().getGroundCubeRenderer().addCube(new GatlingWheelsCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_BEACON -> GameEngine.getCurrentLevel().getGroundCubeRenderer().addCube(new BeaconCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_ARTILLERY -> GameEngine.getCurrentLevel().getGroundCubeRenderer().addCube(new ArtilleryCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_MACHINE_GUN -> GameEngine.getCurrentLevel().getGroundCubeRenderer().addCube(new GatlingCube(Vec2.add(spawnOffset,position),currentTarget));
                case CUBE_HEAD_NONE -> GameEngine.getCurrentLevel().getGroundCubeRenderer().addCube(new BasicCube(Vec2.add(spawnOffset,position)));
                case CUBE_HEAD_FIGHTER -> GameEngine.getCurrentLevel().getFlyingCubeRenderer().addCube(new FighterCube(Vec2.add(spawnOffset,position),currentTarget));
            }
        }
    }

    @Override
    protected void attackingState() {
        if(--remainingAnimationTime<0){
            lifeState = LifeState.CURRENTLY_PURSUING;
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
