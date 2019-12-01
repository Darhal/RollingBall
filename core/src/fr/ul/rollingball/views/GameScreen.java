package fr.ul.rollingball.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;

import java.util.concurrent.TimeUnit;

public class GameScreen extends ScreenAdapter
{
    private SpriteBatch gameBatch;
    private GameWorld gameWorld;
    private static long iterationDuration = 1; // en ms
    private GameState gameState;
    private SpriteBatch textBatch;
    private Camera textCamera;
    private BitmapFont font;
    private Timer.Task changeLabyTask;

    public GameScreen()
    {
        gameBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        gameState = new GameState();
        gameWorld =  new GameWorld(this);
        gameState.setRemainingTime(60);
        gameState.setState(GameState.STATE.EN_COURS);
        textCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,0);

        changeLabyTask = new Timer.Task() {
            @Override
            public void run() {
                gameWorld.changeLaby();
                gameState.setRemainingTime(60);
                gameState.setScore(0);
                gameState.setState(GameState.STATE.EN_COURS);
            }
        };
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        textCamera = new OrthographicCamera(width, height);
        textCamera.position.set(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f, 0);

        FreeTypeFontGenerator fgenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)((40.f/1024.f)*width);
        parameter.borderWidth = (int)((3.f/1024.f)*width);
        parameter.borderColor = Color.BLACK;
        parameter.color = new Color(1.f, 1.f, 0.4f, 0.75f);

        font = fgenerator.generateFont(parameter);
        fgenerator.dispose();
        textBatch.setProjectionMatrix(textCamera.combined);
    }

    @Override
    public void render(float dt)
    {
        long startTime = System.currentTimeMillis();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (gameState.IsGameInState(GameState.STATE.ARRET)){
            Gdx.app.exit();
            return;
        }else if (!gameState.IsGameInState(GameState.STATE.EN_COURS)){
            // textCamera.update();
            // textBatch.setProjectionMatrix(textCamera.combined);
            textBatch.begin();
            renderIntermediate(gameBatch, textBatch);
            textBatch.end();
        }

        gameWorld.update(dt);
        gameBatch.begin();
        gameWorld.draw(gameBatch);
        gameBatch.end();

        // textCamera.update();
        // textBatch.setProjectionMatrix(textCamera.combined);
        textBatch.begin();
        textCamera.update();
        renderText(textBatch);
        textBatch.end();

        long endTime = System.currentTimeMillis();

        if (endTime - startTime < iterationDuration){
            try {
                TimeUnit.MILLISECONDS.sleep(iterationDuration - (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (gameWorld.isVictory()){
            SoundFactory.GetInstance().GetVictoirSound().play();
            gameState.setState(GameState.STATE.VICTOIRE);
            changeLaby();
        }
    }

    @Override
    public void dispose()
    {
        gameBatch.dispose();
        textBatch.dispose();
        font.dispose();
    }

    public void changeLaby()
    {
        Timer.schedule(changeLabyTask, 3);
    }

    public void addScore(int score)
    {
        gameState.setScore(gameState.getScore() + score);
    }

    public void incrementPastillesNormal()
    {
        gameState.setNbPastilleNormalConsumed(gameState.getNbPastilleNormalConsumed() + 1);
    }

    public void incrementTimeRemaining()
    {
        gameState.setRemainingTime(gameState.getRemainingTime() + 10);
    }

    @Override
    public void show() {
        super.show();
        gameState.setState(GameState.STATE.EN_COURS);
    }

    public void renderIntermediate(SpriteBatch batch, SpriteBatch textBatch)
    {
        if (gameState.IsGameInState(GameState.STATE.PERTE)){
            batch.draw(TextureFactory.GetInstance().GetPerteTexture(),
                    0, 0,
                    Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        }else if(gameState.IsGameInState(GameState.STATE.VICTOIRE)){
            batch.draw(TextureFactory.GetInstance().GetBravoTexture(),
                    0,0,
                    Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        }

        font.draw(textBatch, "Nombre de pastilles normales avalées : "+gameState.getNbPastilleNormalConsumed(), 0,0);
    }

    public void renderText(SpriteBatch textBatch)
    {
        font.draw(textBatch, "Temps restant : "+gameState.getRemainingTime(),
                0,
                0
        );
        font.draw(textBatch, "Score : "+gameState.getScore(),
                0,
                0
        );
    }

    public GameState getGameState()
    {
        return gameState;
    }

    private Vector2 getPositionOffset(BitmapFont bitmapFont, String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return new Vector2(glyphLayout.width, glyphLayout.height);
    }
}
