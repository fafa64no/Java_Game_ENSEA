package main.game.characters.cubes;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class MovingCube extends RangedCube{
    public MovingCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture, deploymentTextures, retractionTextures, attackTextures);
    }

    private void updatePosition(){
        Vec2 direction = Vec2.substract(position,currentTarget.getPosition());
        double distanceToTravel = Math.min(direction.getLength(),1);
        Vec2 newPos = Vec2.multiply(direction.normalize(),distanceToTravel);

        position=Vec2.add(newPos,position);

        collider.setOffset();
        damageZone.setOffset();
        detectionZone.setOffset();
    }

    @Override
    protected void pursuingState() {
        super.pursuingState();
        updatePosition();
    }
}
