package Actors;

import Components.SpriteComponent;
import Util.Position2D;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PowerUp extends AbstractActor
{
    private final SpriteComponent Scroll;
    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public PowerUp(Position2D<Float> pos, float szX, float szY) throws IOException {
        super(pos, szX, szY);
        Scroll = new SpriteComponent("./data/img/scroll.png");
        Actors.addObserver(Actors.PowerUp ,super.newActor);
    }
    // TODO:

    @Override
    public void update(float deltaT, Graphics2D g)
    {
        Scroll.draw(g, super.newActor);
        // TODO or delete
    }

    @Override
    public boolean isDead()
    {
        int i = Actors.playerPowerUpCollision();
        if(Actors.playerPowerUpCollision() == -1){
            return false;
        }
        else{
            Actors.removeObserver(Actors.PowerUp, Actors.PowerUp.get(i));
            return true;
        }
        // TODO:
    }


    @Override
    public void update(float deltaT) {

    }
}
