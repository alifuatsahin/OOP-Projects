package Actors;

import Components.PlayerInputComponent;
import Components.SpriteComponent;
import Core.GameWindow;
import Util.Position2D;

import java.awt.*;
import java.io.IOException;

public class Player extends AbstractActor
{
    protected SpriteComponent player;
    protected PlayerInputComponent PIC;
    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public Player(Position2D<Float> pos, float szX, float szY) throws IOException {
        super(pos, szX, szY);
        this.player = new SpriteComponent("./data/img/player.png");
        PIC = PlayerInputComponent.GetInstance(super.newActor);
        GameWindow.GetInstance().attachKeyListener(PIC);
        Actors.addObserver(Actors.player ,super.newActor);
    }
    // TODO:

    @Override
    public void update(float deltaT, Graphics2D g)
    {
        player.draw(g, super.newActor);
        if(Actors.playerWallCollision()) {
        }
        else{
            PIC.update(deltaT);
        }
        // TODO: or delete
    }

    @Override
    public boolean isDead()
    {
        if(Actors.playerEnemyCollision()){
            Actors.removeObserver(Actors.player, Actors.player.get(0));
            return true;
        }
        // TODO:
        return false;
    }

    @Override
    public void update(float deltaT) {

    }
}
