package Space_Rocks;

import Actors.BaseActor;
import GameLoop.BaseScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SLevelScreen extends BaseScreen {

    private SpaceShip spaceship;
    private boolean gameOver;
    @Override
    public void initialize() {
        BaseActor space = new BaseActor(0,0,mainStage);
        space.loadTexture("spacerocks/space.png");
        space.setSize(800,600);
        BaseActor.setWorldBounds(space);
        this.spaceship = new SpaceShip(400,300, mainStage);
        new Rock(600,500, mainStage);
        new Rock(600,300, mainStage);
        new Rock(600,100, mainStage);
        new Rock(400,100, mainStage);
        new Rock(200,100, mainStage);
        new Rock(200,300, mainStage);
        new Rock(200,500, mainStage);
        new Rock(400,500, mainStage);
        gameOver = false;
    }

    @Override
    public void update(float dt) {



        for(BaseActor rockActors: BaseActor.getList(mainStage, Rock.class)){
            if(rockActors.overlaps(spaceship)){
                if(spaceship.getShieldPower() <= 0){
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000, -1000);
                    BaseActor messageLose = new BaseActor(0,0,uiStage);
                    messageLose.loadTexture("spacerocks/message-lose.png");
                    messageLose.centerAtPosition(400,300);
                    messageLose.setOpacity(0);
                    messageLose.addAction(Actions.fadeIn(1));
                    gameOver = true;
                }else {
                    spaceship.setShieldPower(spaceship.getShieldPower()-34);
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActors);
                    rockActors.remove();
                }
            }

            for(BaseActor laserActor: BaseActor.getList(mainStage, Laser.class)){

                if(laserActor.overlaps(rockActors)){
                    Explosion boom = new Explosion(0,0, mainStage);
                    boom.centerAtActor(rockActors);
                    laserActor.remove();
                    rockActors.remove();
                }
            }

        }

        if(!gameOver && BaseActor.count(mainStage, Rock.class) == 0){
            BaseActor win = new BaseActor(0, 0, uiStage);
            win.loadTexture("spacerocks/message-win.png");
            win.centerAtPosition(400, 300);
            win.setOpacity(0);
            win.addAction(Actions.fadeIn(1));
            gameOver = true;
        }



    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.X){
            spaceship.warp();
        }
        if(keycode == Input.Keys.SPACE){
            spaceship.shoot();
        }
        return false;
    }


}
