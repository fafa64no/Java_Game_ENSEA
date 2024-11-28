package main.game.characters.cubes;

import main.utils.data.Config;
import main.utils.vectors.BVec2;
import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public class MovingCube extends RangedCube{
    protected final double attackRange;

    public MovingCube(Vec2 position, BufferedImage texture, BufferedImage deadTexture, BufferedImage[] deploymentTextures, BufferedImage[] retractionTextures, BufferedImage[] attackTextures) {
        super(position, texture, deadTexture, deploymentTextures, retractionTextures, attackTextures, 0.15, Config.movingCubeHealth);
        this.attackRange = Config.cubeAttackRange;
    }

    protected void updatePosition(){
        double distanceToTravel = Vec2.getDistance(position,currentTarget.getPosition());
        if(distanceToTravel<attackRange){
            setInput(new Vec2());
            return;
        }

        setInput(new Vec2(0,-0.7));

        collider.setOffset();
        damageZone.setOffset();
        detectionZone.setOffset();
    }

    @Override
    public Vec2 getCurrentVelocity() {
        Vec2 actualVelocity=new Vec2();
        actualVelocity.x=-currentVelocity.y*Math.sin(rotation);
        actualVelocity.y= currentVelocity.y*Math.cos(rotation);
        return actualVelocity;
    }

    @Override
    public void goToNextPosition(BVec2 canMove, double friction){
        if(canMove.x)   position.x=position.x-currentVelocity.y*Math.sin(rotation);
        if(canMove.y)   position.y=position.y+currentVelocity.y*Math.cos(rotation);
        collider.setOffset();
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
