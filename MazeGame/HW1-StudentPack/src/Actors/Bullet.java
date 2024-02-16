package Actors;

import Components.SpriteComponent;
import Util.Position2D;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Bullet extends AbstractActor
{
    private final SpriteComponent Bullet;
    public float bSpeed = 300;
    protected String lastDirection;

    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public Bullet(Position2D<Float> pos, float szX, float szY, String lastDirection) throws IOException {
        super(pos, szX, szY);
        Bullet = new SpriteComponent("./data/img/bullet.img");
        Actors.addObserver(Actors.bullet, super.newActor);
        this.lastDirection = lastDirection;
    }
    // TODO:

    @Override
    public void update(float deltaT, Graphics2D g)
    {
        Bullet.draw(g, super.newActor);
        if(Objects.equals(lastDirection, "right")){super.newActor.getPos().x += bSpeed*deltaT;}
        if(Objects.equals(lastDirection, "left")){super.newActor.getPos().x -= bSpeed*deltaT;}
        if(Objects.equals(lastDirection, "up")){super.newActor.getPos().y -= bSpeed*deltaT;}
        if(Objects.equals(lastDirection, "down")){super.newActor.getPos().y += bSpeed*deltaT;}
        // TODO: or delete
    }

    @Override
    public boolean isDead()
    {
        // TODO:
        return false;
    }

    @Override
    public void update(float deltaT) {

    }
}
