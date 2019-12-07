package fr.ul.rollingball.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.ArrayList;
import java.util.HashMap;

public class Maze
{
    private static final int MUR = 0, POS_INIT_BAL = 100,
            PASTILLE_NORMAL = 128, PASTILLE_TAILLE = 200, PASTILLE_TEMPS = 225, ZONE_VIDE = 255;
    private GameWorld world;
    private int curr_laby;
    private Pixmap masque;
    private Texture map;
    private Vector2 pos_init_ball;
    private ArrayList<Body> mur;

    public Maze(GameWorld word)
    {
        this.world = word;
        this.mur = new ArrayList<>();
        this.pos_init_ball = new Vector2();
        this.curr_laby = 0;
    }

    public void buildTexLaby()
    {
        Pixmap decor = new Pixmap(Gdx.files.internal("images/Murs.jpg"));
        Pixmap piste = new Pixmap(Gdx.files.internal("images/Piste.jpg"));

        for (int x = 0; x < masque.getWidth(); x++) {
            for (int y = 0; y < masque.getHeight(); y++) {
                int alpha_channel = masque.getPixel(x, y) & 0x000000ff;
                if (alpha_channel != MUR){
                    Color c = new Color(piste.getPixel(x, y));
                    decor.setColor(c.r/4, c.g/4, c.b/4, c.a);
                    decor.fillRectangle(x, y, 1, 1);
                }
            }
        }

        map = new Texture(decor);
        decor.dispose();
        piste.dispose();
        masque.dispose();
        // map = new Texture(masque);
    }

    public void loadLaby(ArrayList<Pastille> past)
    {
        for (Body b : mur){
            world.GetWorld().destroyBody(b);
        }
        mur.clear();

        masque = new Pixmap(Gdx.files.internal("images/Laby"+curr_laby+".png"));
        this.readObject(past);
        this.buildTexLaby();
    }

    public void readObject(ArrayList<Pastille> past)
    {
        float mh = masque.getHeight(); float mw = masque.getWidth();
        float sh = Gdx.graphics.getHeight(); float sw = Gdx.graphics.getWidth();
        for(int y = 0; y < mh; y++){
            for (int x = 0; x < mw; x++){
                int alpha_channel = masque.getPixel(x, y) & 0x000000ff;
                Vector2 real_pos = new Vector2(
                        ((x+1) / mw) * sw,
                        ((mh-y-5) / mh) * sh
                );

                if (alpha_channel == MUR){
                    this.DetecterMur(x, y,
                            new Vector2(
                                (x / mw) * sw,
                                ((mh - y) / mh) * sh
                            )
                    );
                }else if (alpha_channel == POS_INIT_BAL){
                    pos_init_ball.set(real_pos);
                    this.remplirCircle(x+1, y+5);
                }else if(alpha_channel == PASTILLE_NORMAL){
                    past.add(new ScorePastille(world, real_pos));
                    this.remplirCircle(x+1, y+5);
                }else if(alpha_channel == PASTILLE_TAILLE){
                    past.add(new SizePastille(world, real_pos));
                    this.remplirCircle(x+1, y+5);
                }else if(alpha_channel == PASTILLE_TEMPS){
                    past.add(new TimePastille(world, real_pos));
                    this.remplirCircle(x+1, y+5);
                }
            }
        }
    }

    public void DetecterMur(int x, int y, Vector2 rpos)
    {
        int[] offset = new int[]{1, -1};

        for(int i = 0; i< offset.length; i++){
            int posx = x + offset[i];
            int posy = y + offset[i];

            if (posx >= 0 && posx < masque.getWidth()){
                int pix = masque.getPixel(posx, y) & 0x000000ff;
                if (pix != MUR){
                    BodyDef def = new BodyDef();
                    def.type = BodyDef.BodyType.StaticBody;
                    def.position.set(rpos.x, rpos.y);
                    Body body = world.GetWorld().createBody(def);
                    FixtureDef fdef = new FixtureDef();
                    CircleShape shape = new CircleShape();
                    shape.setRadius(1.f);
                    fdef.shape = shape;
                    body.createFixture(fdef).setUserData("M");
                    mur.add(body);
                }
            }

            if(posy >= 0 && posy < masque.getHeight()){
                int pix = masque.getPixel(x, posy) & 0x000000ff;
                if (pix != MUR){
                    BodyDef def = new BodyDef();
                    def.type = BodyDef.BodyType.StaticBody;
                    def.position.set(rpos.x, rpos.y);
                    Body body = world.GetWorld().createBody(def);
                    FixtureDef fdef = new FixtureDef();
                    CircleShape shape = new CircleShape();
                    shape.setRadius(1.f);
                    fdef.shape = shape;
                    body.createFixture(fdef).setUserData("M");
                    mur.add(body);
                }
            }
        }

    }

    public void remplirCircle(int x, int y)
    {
        masque.setColor(ZONE_VIDE/255.f, ZONE_VIDE/255.f, ZONE_VIDE/255.f, ZONE_VIDE/255.f);
        masque.fillCircle(x, y, 5);
    }

    public void draw(SpriteBatch sb)
    {
        sb.draw(map, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose()
    {
        map.dispose();
        masque.dispose();
    }

    public GameWorld GetGameWorld(){
        return world;
    }

    public Vector2 GetBallInitialPosition()
    {
        return pos_init_ball;
    }

    public Texture GetMapTexture()
    {
        return map;
    }

    public void NextLaby(){
        curr_laby = ++curr_laby % 6;
    }

    public void resetLaby()
    {
        curr_laby = 0;
    }
}
