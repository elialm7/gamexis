package starfishgame;

import Actors.BaseActor;
import GameLoop.BaseScreen;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelScreen extends BaseScreen {


    private Turtle turtle;
    private boolean win;


    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800,600);

        new StarFish(400,400, mainStage);
        new StarFish(500,100, mainStage);
        new StarFish(100,450, mainStage);
        new StarFish(200,250, mainStage);

        new Rock(200,150, mainStage);
        new Rock(100,300, mainStage);
        new Rock(300,350, mainStage);
        new Rock(450,200, mainStage);

        turtle = new Turtle(20,20, mainStage);
        BaseActor.setWorldBounds(ocean);

        win = false;

    }

    @Override
    public void update(float dt) {
        for(BaseActor rockActor: BaseActor.getList(mainStage, Rock.class)){
            turtle.preventOverlap(rockActor);
        }

        for(BaseActor starfishActor: BaseActor.getList(mainStage, StarFish.class)){


            StarFish starFish = (StarFish) starfishActor;

            if(turtle.overlaps(starFish) && !starFish.isCollected()){
                starFish.collect();
                starFish.clearActions();
                starFish.addAction(Actions.fadeOut(1));
                starFish.addAction(Actions.after(Actions.removeActor()));

                WhirlPool whirl = new WhirlPool(0,0, mainStage);
                whirl.centerAtActor(starFish);
                whirl.setOpacity(0.25f);
            }
        }
        if(BaseActor.count(mainStage, StarFish.class) == 0 && !win){
            win = true;
            BaseActor  youwi = new BaseActor(0, 0, uiStage);
            youwi.loadTexture("you-win.png");
            youwi.centerAtPosition(400, 300);
            youwi.setOpacity(0);
            youwi.addAction(Actions.delay(1));
            youwi.addAction(Actions.after(Actions.fadeIn(1)));
            youwi.addAction(Actions.delay(1));
            youwi.addAction(Actions.after(new Action() {
                @Override
                public boolean act(float delta) {
                    StarFishGame.setActiveScreen(new MenuScreen());
                    return true;
                }
            }));

        }


    }
}
