package Space_Rocks;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
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


    public void warp(){
        if(getStage() == null){
            return;
        }
        Warp warp1 = new Warp(0, 0, this.getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(800), (float) MathUtils.random(600));
        Warp warp2 = new Warp(0,0, this.getStage());
        warp2.centerAtActor(this);
    }


    public void shoot(){
        if(getStage()== null){
            return;
        }
        Laser laser = new Laser(0,0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());

    }

    public int getShieldPower(){
        return this.shieldpower;
    }

    public void setShieldPower(int power){
        this.shieldpower = power;
    }

}
