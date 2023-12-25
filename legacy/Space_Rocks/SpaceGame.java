package Space_Rocks;

import GameLoop.BaseGame;

public class SpaceGame extends BaseGame {

    @Override
    public void create() {
        super.create();
        setActiveScreen(new SLevelScreen());
    }
}
