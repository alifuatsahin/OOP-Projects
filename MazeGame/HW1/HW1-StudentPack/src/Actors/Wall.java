package Actors;

import Components.SpriteComponent;
import Util.Position2D;

import java.awt.*;
import java.io.IOException;

public class Wall extends AbstractActor
{
    private SpriteComponent Wall;
    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public Wall(Position2D<Float> pos, float szX, float szY) throws IOException {
        super(pos, szX, szY);
        Wall = new SpriteComponent("./data/img/wall.png");
        Actors.addObserver(Actors.Walls, super.newActor);
    }

    @Override
    public void update(float deltaT, Graphics2D g)
    {
        Wall.draw(g, super.newActor);
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
