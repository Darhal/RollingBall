package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.RollingBall;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class SplashScreen extends ScreenAdapter
{
    private SpriteBatch listeAffichage;
    private RollingBall game;

    private Timer.Task timerTask = new Timer.Task() {
        @Override
        public void run() {
            game.setScreen(new GameScreen());
        }
    };

    public SplashScreen(RollingBall game){
        super();
        this.game = game;
        listeAffichage = new SpriteBatch();
        Timer.schedule(timerTask, 1);

    }

    @Override
    public void render(float dt)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        listeAffichage.begin();
        listeAffichage.draw(TextureFactory.GetInstance().GetIntroTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        listeAffichage.end();
    }

    @Override
    public void dispose()
    {
        listeAffichage.dispose();
    }
}
