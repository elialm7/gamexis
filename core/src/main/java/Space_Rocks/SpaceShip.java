package Space_Rocks;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpaceShip extends BaseActor {

    public SpaceShip(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("spacerocks/spaceship.png");
        setBoundaryPolygon(8);
        setAcceleration(200);
        setMaxSpeed(150);
        setDeceleration(10);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        float degreesPerSecond = 120;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rotateBy(degreesPerSecond*dt);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rotateBy(-degreesPerSecond*dt);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            accelerateAtAngle(getRotation());
        }

        applyPhysics(dt);
       wrapAroundWorld();

    }




}
