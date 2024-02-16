package Components;

import Actors.Enemy;

public class HorizontalPatrolStrategy extends AbstractPatrolStrategy
{
    public HorizontalPatrolStrategy(Enemy enemy) {
        this.enemy = enemy;
    }
    @Override
    public void update(float deltaT)
    {
        enemy.getPos().x += enemy.eSpeed*deltaT;
        // TODO:
    }
}
