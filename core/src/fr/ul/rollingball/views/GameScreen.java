package fr.ul.rollingball.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.ul.rollingball.models.GameWorld;

public class GameScreen extends ScreenAdapter
{
    private SpriteBatch gameBatch;
    private GameWorld gameWorld;

    public GameScreen()
    {
        gameBatch = new SpriteBatch();
        gameWorld =  new GameWorld(this);
    }

    @Override
    public void render(float dt)
    {
        gameWorld.update(dt);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.begin();
        gameWorld.draw(gameBatch);
        gameBatch.end();
    }

    @Override
    public void dispose()
    {
        gameBatch.dispose();
    }
}
