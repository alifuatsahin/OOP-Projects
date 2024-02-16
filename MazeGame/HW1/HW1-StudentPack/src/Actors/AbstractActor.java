package Actors;

import Components.CollisionComponent;
import Components.IRealTimeComponent;
import Util.AABB;
import Util.Position2D;

import java.awt.*;
import java.util.ArrayList;

// Meta Actor Class
// Everything in the game is an actor
public abstract class AbstractActor extends AABB implements IRealTimeComponent
{
    protected AABB newActor;
    protected static CollisionComponent Actors = new CollisionComponent();
    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public AbstractActor(Position2D<Float> pos, float szX, float szY) {
        super(pos, szX, szY);
        this.newActor = new AABB(pos, szX, szY);
    }
    // TODO:

    public void update(float deltaT, Graphics2D g)
    {
        // TODO:
    }

    public abstract boolean isDead();
}
