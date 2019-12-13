package fr.ul.rollingball.models;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import fr.ul.rollingball.dataFactories.TextureFactory;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

public class Ball3D extends Ball
{
    public OrthographicCamera cam;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    public Environment environment;
    private float angle;

    public Ball3D(GameWorld word, Vector2 pos)
    {
        super(word, pos);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        modelBatch = new ModelBatch();

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f, 0);

        ModelLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.internal("models/sphere.obj"));
        instance = new ModelInstance(model);
        angle = 0.f;
    }

    public void draw(SpriteBatch batch)
    {
        cam.update();
        modelBatch.begin(cam);
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        if (body.getLinearVelocity().y > 0.01 || body.getLinearVelocity().y < -0.01 || body.getLinearVelocity().x > 0.01 || body.getLinearVelocity().x < -0.01) {
            angle += (Math.abs(body.getLinearVelocity().y) + Math.abs(body.getLinearVelocity().x)) / 10;
        }

        instance.transform.set(
                new Vector3(x, y, -this.GetRayon()),
                new Quaternion(new Vector3(-body.getLinearVelocity().y, body.getLinearVelocity().x, 0.f), angle),
                new Vector3(this.GetRayon()/2.f, this.GetRayon()/2.f, this.GetRayon()/2.f)
        );
        modelBatch.render(instance, environment);
        modelBatch.end();
    }
}