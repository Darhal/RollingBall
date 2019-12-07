package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.ul.rollingball.dataFactories.TextureFactory;
import fr.ul.rollingball.views.GameScreen;
import java.util.ArrayList;

public class GameWorld
{
    public static float DIMENSIONS[] = {80.f, 60.f};
    private GameScreen screen;
    private Ball2D ball;
    private ArrayList<Pastille> pastilles;
    private World world;
    private Maze maze;

    public GameWorld(GameScreen screen)
    {
        world = new World(new Vector2(0.f, 0.f), true);
        pastilles = new ArrayList<>();
        maze = new Maze(this);
        this.screen = screen;
        this.createCollisionListener();
        maze.loadLaby(pastilles);
        ball = new Ball2D(world, maze.GetBallInitialPosition());
    }

    public Ball2D GetBall() { return ball; }

    public void draw(SpriteBatch batch)
    {
        batch.draw(TextureFactory.GetInstance().GetPisteTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        maze.draw(batch);
        ball.draw(batch);
        for(Pastille p : pastilles){
            p.draw(batch);
        }
    }

    public World GetWorld() { return world; }

    public ArrayList<Pastille> GetListePastilles(){
        return pastilles;
    }

    public void changeLaby()
    {
        maze.NextLaby();
        loadLaby();
    }

    public void resetLaby()
    {
        maze.resetLaby();
        loadLaby();
    }

    private void loadLaby()
    {
        for (Pastille p : pastilles){
            p.dispose();
        }

        pastilles.clear();
        maze.loadLaby(pastilles);
        ball.GetBody().setLinearVelocity(0.f, 0.f);
        ball.GetBody().setTransform(maze.GetBallInitialPosition(), 0.f);
    }

    public boolean isVictory()
    {
        return ball.isOut();
    }

    private void createCollisionListener() {
       world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA == ball.GetBody().getFixtureList().first() && fixtureB.getUserData() instanceof Pastille){
                    Pastille pastille = (Pastille)fixtureB.getUserData();

                    if (pastille != null && !pastille.IsEaten()){
                        pastille.SetEaten(true);
                        pastille.effect();
                    }
                }else if(fixtureB == ball.GetBody().getFixtureList().first() && fixtureA.getUserData() instanceof Pastille){
                    Pastille pastille = (Pastille)fixtureA.getUserData();

                    if (pastille != null && !pastille.IsEaten()){
                        pastille.SetEaten(true);
                        pastille.effect();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) { }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { }
        });
    }

    public GameScreen getGameScreen()
    {
        return screen;
    }

}
