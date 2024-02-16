package Components;

import Actors.Enemy;
import Util.Position2D;

public class VerticalPatrolStrategy extends AbstractPatrolStrategy{

    public VerticalPatrolStrategy(Enemy enemy) {
        this.enemy = enemy;
    }
    @Override
    public void update(float deltaT)
    {
        enemy.getPos().y += enemy.eSpeed*deltaT;
        // TODO:
    }
}
