package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class SizePastille extends Pastille
{
    public SizePastille(GameWorld gworld, Vector2 pos)
    {
        super(gworld, pos);
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(TextureFactory.GetInstance().GetPastilleTailleTexture(), this.GetPosition().x, this.GetPosition().y, this.GetRayon(), this.GetRayon());
    }

    public void effect()
    {
        int s = 0;

        if (gameWorld.GetBall().GetTailleCurrent() == 0)
            s = 1;

        gameWorld.GetBall().SetTailleCurrent(s);

        System.out.println("Size pastille!");
    }
}
