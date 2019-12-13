package fr.ul.rollingball.models;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.SoundFactory;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class SizePastille extends Pastille
{
    public SizePastille(GameWorld gworld, Vector2 pos)
    {
        super(gworld, pos);
    }

    public void draw(SpriteBatch batch)
    {
        TextureRegion currentFrame = (TextureRegion) TextureFactory.GetInstance().GetPastilleTailleAnimation().getKeyFrame(gameWorld.getElapsedTime());
        batch.draw(
                currentFrame, this.GetPosition().x / 1024.f * gameWorld.getViewportDimensions().x, this.GetPosition().y / 720.f * gameWorld.getViewportDimensions().y,
                this.GetRayon(), this.GetRayon()
        );
    }

    public void effect()
    {
        int s = 0;

        if (gameWorld.GetBall().GetTailleCurrent() == 0)
            s = 1;

        gameWorld.GetBall().SetTailleCurrent(s);

        SoundFactory.GetInstance().GetPtailleSound().play(SoundFactory.MASTER_VOLUME);
    }
}
