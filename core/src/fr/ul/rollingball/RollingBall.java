package fr.ul.rollingball;

import com.badlogic.gdx.Game;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.views.SplashScreen;

public class RollingBall extends Game {
	private SplashScreen splashScreen;
	
	@Override
	public void create () {
		splashScreen = new SplashScreen(this);
		this.setScreen(splashScreen);
		// SoundFactory.GetInstance().GetVictoirSound().play();
	}

	
	@Override
	public void dispose () {
		splashScreen.dispose();
	}
}
