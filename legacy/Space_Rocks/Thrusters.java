package Space_Rocks;

import Actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Thrusters extends BaseActor {

    public Thrusters(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("spacerocks/fire.png");
    }
}
