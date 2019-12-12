package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureFactory
{
    private final Texture intro,piste,ball, bravo, mur, perte
            /*, pastilleNormal, pastilleTaille, pastilleTemps */;
    private TextureAtlas pastilleTailleA, pastilleTempsA, pastilleNormalA;
    private Animation pastilleNormalAnim, pastilleTailleAnim, pastilleTempsAnim;

    private static TextureFactory instance = new TextureFactory();

    private TextureFactory()
    {
        intro = new Texture(Gdx.files.internal("images/Intro.jpg"));
        piste = new Texture(Gdx.files.internal("images/Piste.jpg"));
        ball = new Texture(Gdx.files.internal("images/Bille2D.png"));

        /*pastilleNormal =new Texture(Gdx.files.internal("images/pastilleNormale.bmp"));
        pastilleTaille=new Texture(Gdx.files.internal("images/pastilleTaille.bmp"));
        pastilleTemps=new Texture(Gdx.files.internal("images/pastilleTemps.bmp"));*/

        bravo=new Texture(Gdx.files.internal("images/Bravo.bmp"));
        mur =new Texture(Gdx.files.internal("images/Murs.jpg"));
        perte =new Texture(Gdx.files.internal("images/Perte.bmp"));


        pastilleNormalA = new TextureAtlas(Gdx.files.internal("images/pastilleNormale.pack"));
        pastilleTailleA = new TextureAtlas(Gdx.files.internal("images/pastilleTaille.pack"));
        pastilleTempsA = new TextureAtlas(Gdx.files.internal("images/pastilleTemps.pack"));


        pastilleNormalAnim = new Animation(1/10f, pastilleNormalA.findRegions("pastilleNormale"), Animation.PlayMode.LOOP);
        pastilleTailleAnim = new Animation(1/10f, pastilleTailleA.findRegions("pastilleTaille"), Animation.PlayMode.LOOP);
        pastilleTempsAnim = new Animation(1/10f, pastilleTempsA.findRegions("pastilleTemps"), Animation.PlayMode.LOOP);
    }

    public static TextureFactory GetInstance() { return instance; }

    public Texture GetIntroTexture() { return intro; }

    public Texture GetPisteTexture() { return piste; }

    public Texture GetBallTexture() { return ball; }

    public Animation GetPastilleNormalAnimation() { return pastilleNormalAnim; }

    public Texture GetBravoTexture() { return bravo; }

    public Texture GetMurTexture() { return mur; }

    public Animation GetPastilleTailleAnimation() { return pastilleTailleAnim; }

    public Animation GetPastilleTempsAnimation() { return pastilleTempsAnim; }

    public Texture GetPerteTexture() { return perte; }
}
