package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Pastille
{
    private static float ratio = 0.009f;
    protected GameWorld gameWorld;
    private Body body;
    private boolean is_eaten;


    public Pastille(GameWorld gworld, Vector2 pos)
    {
        this.gameWorld = gworld;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(pos.x, pos.y);
        body = gworld.GetWorld().createBody(def);
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        CircleShape shape = new CircleShape();
        shape.setRadius(GetRayon()/2.f);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public float GetRayon() { return ratio * Gdx.graphics.getWidth(); }

    public Vector2 GetPosition() { return new Vector2(body.getPosition().x - this.GetRayon()/2.f, body.getPosition().y - this.GetRayon()/2.f); }

    public Body GetBody() { return body; }

    public void SetEaten(boolean b) {
        is_eaten = b;
    }

    public boolean IsEaten() { return is_eaten; }

    public abstract void draw(SpriteBatch batch);

    public abstract  void effect();

    public void dispose()
    {
        gameWorld.GetWorld().destroyBody(body);
    }

}
