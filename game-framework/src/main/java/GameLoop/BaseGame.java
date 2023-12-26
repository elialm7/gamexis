package GameLoop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;


public abstract class BaseGame extends Game {
   private static BaseGame game;
   private static Label.LabelStyle labelstyle;
   public static TextButton.TextButtonStyle textButtonStyle;
   public BaseGame(){
       game = this;
   }

   public static void setActiveScreen(BaseScreen s){
       game.setScreen(s);
   }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);

        labelstyle = new Label.LabelStyle();
        labelstyle.font = new BitmapFont();

        /*
        * Esta section puede ser extraida en una classe que se encargue de cargar los fonts y definir estilos.
        * */
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 48;
        parameters.color = Color.WHITE;
        parameters.borderWidth = 2;
        parameters.borderColor = Color.BLACK;
        parameters.borderStraight = true;
        parameters.minFilter = Texture.TextureFilter.Linear;
        parameters.magFilter = Texture.TextureFilter.Linear;
        BitmapFont customFont = fontGenerator.generateFont(parameters);
        labelstyle.font = customFont;

        textButtonStyle = new TextButton.TextButtonStyle();
        Texture buttonText = new Texture(Gdx.files.internal("Button/button.png"));
        NinePatch buttonPatch = new NinePatch(buttonText, 24,24,24,24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = customFont;
        textButtonStyle.fontColor = Color.GRAY;
    }
}
