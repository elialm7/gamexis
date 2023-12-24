package starfishgame;

import GameLoop.BaseGame;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class StarFishGame extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}
