package gamexis.html;

import gamexis.core.GameXis;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameXisHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new GameXis();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
