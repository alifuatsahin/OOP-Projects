package Components;

import java.io.IOException;

// On a real-time system all components may want to update wrt.
// time change
public interface IRealTimeComponent
{
    public void update(float deltaT);
}
