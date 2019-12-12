package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class ScorePastille extends Pastille
{
    public ScorePastille(GameWorld gworld, Vector2 pos)
    {
        super(gworld, pos);
    }

    public void draw(SpriteBatch batch)
    {
        TextureRegion currentFrame = (TextureRegion) TextureFactory.GetInstance().GetPastilleNormalAnimation().getKeyFrame(gameWorld.getElapsedTime());
        batch.draw(
                currentFrame, this.GetPosition().x, this.GetPosition().y,
                this.GetRayon(), this.GetRayon()
        );
    }

    public void effect()
    {
        gameWorld.getGameScreen().incrementPastillesNormal();
        gameWorld.getGameScreen().addScore(1);
        SoundFactory.GetInstance().GetPastilleSound().play(SoundFactory.MASTER_VOLUME);
    }
}
