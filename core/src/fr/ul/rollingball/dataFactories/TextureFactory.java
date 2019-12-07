package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureFactory
{
    private final Texture intro,piste,ball,pastilleNormal, bravo, mur, pastilleTaille,
        pastilleTemps,perte;

    private static TextureFactory instance = new TextureFactory();

    private TextureFactory()
    {
        intro = new Texture(Gdx.files.internal("images/Intro.jpg"));
        piste = new Texture(Gdx.files.internal("images/Piste.jpg"));
        ball = new Texture(Gdx.files.internal("images/Bille2D.png"));

        pastilleNormal =new Texture(Gdx.files.internal("images/pastilleNormale.bmp"));
        bravo=new Texture(Gdx.files.internal("images/Bravo.bmp"));
        mur =new Texture(Gdx.files.internal("images/Murs.jpg"));
        pastilleTaille=new Texture(Gdx.files.internal("images/pastilleTaille.bmp"));
        pastilleTemps=new Texture(Gdx.files.internal("images/pastilleTemps.bmp"));
        perte =new Texture(Gdx.files.internal("images/Perte.bmp"));
    }

    public static TextureFactory GetInstance() { return instance; }

    public Texture GetIntroTexture() { return intro; }

    public Texture GetPisteTexture() { return piste; }

    public Texture GetBallTexture() { return ball; }

    public Texture GetPastilleNormalTexture() { return pastilleNormal; }

    public Texture GetBravoTexture() { return bravo; }

    public Texture GetMurTexture() { return mur; }

    public Texture GetPastilleTailleTexture() { return pastilleTaille; }

    public Texture GetPastilleTempsTexture() { return pastilleTemps; }

    public Texture GetPerteTexture() { return perte; }
}
