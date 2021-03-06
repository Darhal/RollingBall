package fr.ul.rollingball.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.ul.rollingball.controllers.GestureListener;
import fr.ul.rollingball.controllers.KeyboardLisener;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.models.Ball3D;
import fr.ul.rollingball.models.GameState;
import fr.ul.rollingball.models.GameWorld;
import fr.ul.rollingball.models.Pastille;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static fr.ul.rollingball.dataFactories.SoundFactory.MASTER_VOLUME;

public class GameScreen extends ScreenAdapter
{
    private static long iterationDuration = 1; // en ms
    private SpriteBatch gameBatch;
    private GameWorld gameWorld;
    private GameState gameState;

    // Input:
    private KeyboardLisener keyboardListener;
    private GestureListener gestureListener;

    private SpriteBatch textBatch;
    private Camera textCamera;
    private BitmapFont font;
    private Timer.Task changeLabyTask;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private StretchViewport viewport;

    public GameScreen()
    {
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        // camera.position.set(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f, 0);

        keyboardListener = new KeyboardLisener();
        gestureListener = new GestureListener();
        InputMultiplexer input_multi = new InputMultiplexer();
        input_multi.addProcessor(keyboardListener);
        input_multi.addProcessor(new GestureDetector(gestureListener));
        Gdx.input.setInputProcessor(input_multi);

        gameBatch = new SpriteBatch();
        textBatch = new SpriteBatch();
        gameState = new GameState();
        gameWorld =  new GameWorld(this);
        gameState.setRemainingTime(30);
        gameState.setState(GameState.STATE.EN_COURS);
        textCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        textCamera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,0);

        changeLabyTask = new Timer.Task() {
            @Override
            public void run() {
                gameWorld.changeLaby();
                gameState.setRemainingTime(60 + gameState.getNbPastilleNormalConsumed());
                gameState.setNbPastilleNormalConsumed(0);
                gameState.setState(GameState.STATE.EN_COURS);
                keyboardListener.resetAcceleration();
            }
        };
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        textCamera = new OrthographicCamera(width, height);
        textCamera.position.set(width/2, height/2, 0);

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

    public void update(float dt)
    {
        if (keyboardListener.isQuit()){
            gameState.setState(GameState.STATE.ARRET);
            return;
        }
        gameWorld.update(dt);
    }

    @Override
    public void render(float dt)
    {
        long startTime = System.currentTimeMillis();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.update(dt);

        camera.update();
        if (keyboardListener.isDebug()){
            debugRenderer.render(gameWorld.GetWorld(), camera.combined);
        }else {
            gameBatch.begin();
            gameBatch.setProjectionMatrix(camera.combined);
            gameWorld.draw(gameBatch);
            gameBatch.end();

            textCamera.update();
            textBatch.setProjectionMatrix(textCamera.combined);
            textBatch.begin();
            textCamera.update();
            renderText(textBatch);
            textBatch.end();
        }

        if (gameState.IsGameInState(GameState.STATE.ARRET)){
            Gdx.app.exit();
            return;
        }else if (!gameState.IsGameInState(GameState.STATE.EN_COURS)){
            gameBatch.begin();
            if (gameState.IsGameInState(GameState.STATE.PERTE)){
                gameBatch.draw(TextureFactory.GetInstance().GetPerteTexture(),
                        viewport.getWorldWidth()/4, viewport.getWorldHeight()/4,
                        viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);
            }else if(gameState.IsGameInState(GameState.STATE.VICTOIRE)){
                gameBatch.draw(TextureFactory.GetInstance().GetBravoTexture(),
                        viewport.getWorldWidth()/4, viewport.getWorldHeight()/4,
                        viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);
            }
            gameBatch.end();

            textCamera.update();
            textBatch.setProjectionMatrix(textCamera.combined);
            textBatch.begin();
            String string = "Nombre de pastilles normales avalées : "+gameState.getNbPastilleNormalConsumed();
            Vector2 off = getPositionOffset(font, string);
            font.draw(textBatch, "Nombre de pastilles normales avalées : "+gameState.getNbPastilleNormalConsumed(),
                    viewport.getWorldWidth()/2 - off.x / 2,
                    viewport.getWorldHeight()/4
            );
            textBatch.end();
        }

        long endTime = System.currentTimeMillis();

        if (endTime - startTime < iterationDuration){
            try {
                TimeUnit.MILLISECONDS.sleep(iterationDuration - (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (gameState.IsGameInState(GameState.STATE.EN_COURS)){
            if (gameWorld.isVictory()){
                gameState.setState(GameState.STATE.VICTOIRE);
                SoundFactory.GetInstance().GetVictoirSound().play(MASTER_VOLUME);
                changeLaby();
            }

            if (gameState.getRemainingTime() <= 0){
                gameState.setState(GameState.STATE.PERTE);
                SoundFactory.GetInstance().GetPerteSound().play(MASTER_VOLUME);

                Timer.Task resetLaby = new Timer.Task() {
                    @Override
                    public void run() {
                        gameWorld.resetLaby();
                        gameState.setRemainingTime(60);
                        gameState.setScore(0);
                        gameState.setNbPastilleNormalConsumed(0);
                        gameState.setState(GameState.STATE.EN_COURS);
                        keyboardListener.resetAcceleration();
                    }
                };
                Timer.schedule(resetLaby, 3);
            }
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
        gameState.setRemainingTime(gameState.getRemainingTime() + 5);
    }

    @Override
    public void show() {
        super.show();
        gameState.setState(GameState.STATE.EN_COURS);
    }

    public void renderText(SpriteBatch textBatch)
    {
        String string = "Temps restant : "+gameState.getRemainingTime();
        Vector2 off = getPositionOffset(font, string);
        font.draw(textBatch, string,
                Gdx.graphics.getWidth()/2.f - off.x / 2,
                Gdx.graphics.getHeight()
        );
        string = "Score : "+gameState.getScore();
        off = getPositionOffset(font, string);
        font.draw(textBatch, "Score : "+gameState.getScore(),
                Gdx.graphics.getWidth() - off.x,
                Gdx.graphics.getHeight()
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

    public KeyboardLisener getKeyboardListener()
    {
        return keyboardListener;
    }

    public GestureListener getGestureListener()
    {
        return gestureListener;
    }

    public Vector2 getViewportDimensions()
    {
        return new Vector2(viewport.getWorldWidth(), viewport.getWorldHeight());
    }
}
