package Actors;

import Components.SpriteComponent;
import Util.Position2D;

import java.awt.*;
import java.io.IOException;

public class Enemy extends AbstractActor
{
    private SpriteComponent Enemy;
    public float eHealth = 100;
    public float eSpeed = 120;
    public String description = null;
    /**
     * Constructor, directly sets the every parameter
     *
     * @param pos "top right" (wrt. the screen coordinates) of the box
     * @param szX horizontal size of the box in pixels
     * @param szY vertical size of the box in pixels
     */
    public Enemy(Position2D<Float> pos, float szX, float szY) throws IOException {
        super(pos, szX, szY);
        Enemy = new SpriteComponent("./data/img/enemy.png");
        Actors.addObserver(Actors.Enemies ,super.newActor);
    }
    // TODO:

    @Override
    public boolean isDead()
    {
        // TODO:
        if(Actors.bullet.isEmpty()) {
            return false;
        }
        else{
            int i = Actors.bulletEnemyCollision();
            if(i == -1){
                return false;
            }
            else{
                Actors.removeObserver(Actors.bullet, Actors.bullet.get(i));
                return true;
            }
        }
    }

    @Override
    public void update(float deltaT, Graphics2D g)
    {
        Enemy.draw(g, super.newActor);
        if(description == "xy") {
            if (Actors.enemyWallCollision()) {
                eSpeed = -eSpeed;
            }
        }
        // TODO: or delete

    }

    @Override
    public void update(float deltaT) {

    }
}
