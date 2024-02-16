package Components;

import Util.AABB;

import java.util.ArrayList;
import java.util.Collections;

public class CollisionComponent implements IRealTimeComponent, ICollisionListener {
    public ArrayList<AABB> PowerUp;
    public ArrayList<AABB> Walls;
    public ArrayList<AABB> Enemies;
    public ArrayList<AABB> player;
    public ArrayList<AABB> bullet;
    public CollisionComponent(){
        this.PowerUp = new ArrayList<>();
        this.Walls = new ArrayList<>();
        this.Enemies = new ArrayList<>();
        this.player = new ArrayList<>();
        this.bullet = new ArrayList<>();
    }
    // TODO:
    @Override
    public void update(float deltaT)
    {
        // TODO:
    }

    @Override
    public void addObserver(ArrayList<AABB> Observers, AABB x) {
        Observers.add(x);
    }

    @Override
    public void removeObserver(ArrayList<AABB> Observers,AABB x) {
        int i = Observers.indexOf(x);
        if (i >= 0) {
            Observers.remove(i);
        }
    }

    @Override
    public boolean playerWallCollision() {
        for (AABB wall : Walls) {
            if (player.get(0).moveIfCollide(wall)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean playerEnemyCollision() {
        for(AABB enemy : Enemies){
            if(player.get(0).collides(enemy)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int playerPowerUpCollision() {
        for(AABB powerup : PowerUp){
            if(player.get(0).collides(powerup)){
                return PowerUp.indexOf(powerup);
            }
        }
        return -1;
    }

    @Override
    public int bulletEnemyCollision() {
        for(AABB enemy : Enemies){
            if(bullet.get(0).collides(enemy)){
                return Enemies.indexOf(enemy);
            }
        }
        return -1;
    }

    @Override
    public boolean enemyWallCollision() {
        for(AABB enemy : Enemies){
            for(AABB wall : Walls)
                if(wall.moveIfCollide(enemy)){
                    return true;
                }
            }
            return false;
    }
}
