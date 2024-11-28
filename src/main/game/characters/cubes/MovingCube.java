package main.game.characters.cubes;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class MovingCube extends RangedCube{
    protected final double attackRange;

    public MovingCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture, deploymentTextures, retractionTextures, attackTextures);
        this.attackRange=100;
    }

    protected void updatePosition(){
        double distanceToTravel = Vec2.getDistance(position,currentTarget.getPosition());
        if(distanceToTravel<attackRange){
            setInput(new Vec2());
            return;
        }
        Vec2 direction = Vec2.substract(currentTarget.getPosition(),position);

        setInput(direction.normalize());

        collider.setOffset();
        damageZone.setOffset();
        detectionZone.setOffset();
    }

    @Override
    protected void pursuingState() {
        super.pursuingState();
        updatePosition();
    }

    @Override
    protected void attackingState() {
        super.attackingState();
        updatePosition();
    }

    @Override
    protected void retractingState() {
        super.retractingState();
        setInput(new Vec2());
    }

    @Override
    public void killYourself() {
        super.killYourself();
        setInput(new Vec2());
    }
}
