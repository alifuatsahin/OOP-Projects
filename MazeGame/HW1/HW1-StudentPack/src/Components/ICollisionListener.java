package Components;

import Util.AABB;

import java.util.ArrayList;

public interface ICollisionListener
{
    // You can change the interface here (add arguments to the function or
    // change the name etc.)
    public void addObserver(ArrayList<AABB> Observer, AABB x);
    public void removeObserver(ArrayList<AABB> Observer, AABB x);
    public boolean playerWallCollision();
    public boolean playerEnemyCollision();
    public int playerPowerUpCollision();
    public int bulletEnemyCollision();
    public boolean enemyWallCollision();
}
