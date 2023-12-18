package starfishgame;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public class Turtle extends BaseActor implements Serializable {


  private float VelocityModule;
  private float VelocityAngle;

  private float AccelerationModule;
  private float AccelerationAngle;

  private float Xposition;
  private float Yposition;





    public Turtle(float x, float y, Stage s){
        super(x, y, s);
        String[] filenames = {
                "turtle-1.png", "turtle-2.png","turtle-3.png", "turtle-4.png", "turtle-5.png", "turtle-6.png"
        };
        loadAnimationFromFiles(filenames, 0.1f, true);
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);


    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
           accelerateAtAngle(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
           accelerateAtAngle(270);
        }
        applyPhysics(dt);
        setAnimationPaused(!isMoving());
        if(getSpeed()>0){
            setRotation(getMotionAngle());
        }
        updateState();
        boundToWorld();
        alignCamera();



    }

    private void updateState(){
        this.VelocityModule = getVelocityModule();
        this.VelocityAngle = getVelocityAngle();
        this.AccelerationModule = getAccelerationModule();
        this.AccelerationAngle = getAccelerationAngle();
        String velocity = "("+VelocityModule+", "+VelocityAngle+")";
        Gdx.app.log("INFO:Velocitiy:", velocity);
    }

}
