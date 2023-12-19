package Space_Rocks;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpaceShip extends BaseActor {


    private Thrusters thrusters;
    private Shield shield;
    private int shieldpower;
    public SpaceShip(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("spacerocks/spaceship.png");
        setBoundaryPolygon(8);
        setAcceleration(200);
        setMaxSpeed(150);
        setDeceleration(10);
        thrusters = new Thrusters(0,0, s);
        addActor(thrusters);
        thrusters.setPosition(-thrusters.getWidth(), getHeight()/2 - thrusters.getHeight()/2);
        shield = new Shield(0,0, s);
        addActor(shield);
        shield.centerAtPosition(getWidth()/2, getHeight()/2);
        this.shieldpower = 100;
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
            thrusters.setVisible(true);
        }else {
            thrusters.setVisible(false);
        }

        applyPhysics(dt);
        wrapAroundWorld();
        shield.setOpacity(shieldpower/100f);
        if(shieldpower < 0){
            shield.setVisible(false);
        }
    }




}
