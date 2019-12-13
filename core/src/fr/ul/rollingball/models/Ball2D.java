package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class Ball2D extends Ball
{
    public Ball2D(GameWorld word, Vector2 pos)
    {
        super(word, pos);
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(
                TextureFactory.GetInstance().GetBallTexture(),
                this.GetPosition().x, this.GetPosition().y,
                this.GetRayon(), this.GetRayon()
        );
    }
}
