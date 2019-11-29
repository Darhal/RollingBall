package fr.ul.rollingball.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.ul.rollingball.dataFactories.TextureFactory;

public class TimePastille extends Pastille
{
    public TimePastille(GameWorld gworld, Vector2 pos)
    {
        super(gworld, pos);
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(TextureFactory.GetInstance().GetPastilleTempsTexture(), this.GetPosition().x, this.GetPosition().y, this.GetRayon(), this.GetRayon());
    }

    public void effect()
    {
        System.out.println("Time pastille!");
    }
}
