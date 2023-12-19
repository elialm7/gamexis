package Space_Rocks;

import Actors.BaseActor;
import GameLoop.BaseScreen;

public class SLevelScreen extends BaseScreen {

    private SpaceShip spaceship;
    @Override
    public void initialize() {
        BaseActor space = new BaseActor(0,0,mainStage);
        space.loadTexture("spacerocks/space.png");
        space.setSize(800,600);
        BaseActor.setWorldBounds(space);
        this.spaceship = new SpaceShip(400,300, mainStage);
    }

    @Override
    public void update(float dt) {

    }
}
