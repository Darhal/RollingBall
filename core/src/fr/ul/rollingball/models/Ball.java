package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Ball
{
    public static float rayons[] = { Gdx.graphics.getWidth() / 100,  Gdx.graphics.getWidth() / 50};
    private int current_taille;
    protected Body body;
    protected GameWorld world;

    public Ball(GameWorld world, Vector2 pos)
    {
        this.world = world;
        current_taille = 1;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(pos.x, pos.y);
        body = world.GetWorld().createBody(def);
        FixtureDef fdef = new FixtureDef();
        fdef.density = 1.f;
        fdef.friction = 0.25f;
        fdef.restitution = 0.f;
        CircleShape shape = new CircleShape();
        shape.setRadius(rayons[current_taille]/2.f);
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);
    }

    public float GetRayon() { return rayons[current_taille]; }

    public Vector2 GetPosition() {
        return new Vector2(body.getPosition().x - this.GetRayon()/2.f, body.getPosition().y - this.GetRayon()/2.f);
    }

    public void SetTailleCurrent(int is_big){
        current_taille = is_big;
        body.getFixtureList().first().getShape().setRadius(GetRayon() / 2.f);
    }

    public int GetTailleCurrent(){
        return current_taille;
    }

    public void ApplyForce(Vector2 force)
    {
        body.applyForce(force, body.getWorldCenter(), true);
    }

    public Body GetBody() { return body; }

    public boolean isOut()
    {
        Vector2 pos = this.GetPosition();
        return (pos.x < -this.GetRayon() || pos.x > world.getViewportDimensions().x + this.GetRayon())
                || (pos.y < -this.GetRayon() || pos.y > world.getViewportDimensions().y + this.GetRayon());
    }

    public abstract void draw(SpriteBatch batch);
}
