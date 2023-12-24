package shooter;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class ServerMain {

	public static void main(String[] args) {


		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

		conf.updatesPerSecond = 60;

		new HeadlessApplication(new CuadraditosMoqueteros(), conf);
	}

}
