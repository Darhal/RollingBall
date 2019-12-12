package fr.ul.rollingball.dataFactories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundFactory {
    private static SoundFactory soundFactory = new SoundFactory();

    private Sound alerteSound,collisionSound,pastilleSound,perteSound,
            ptailleSound,ptempsSound,victoirSound;
    public static float MASTER_VOLUME = 0.1f;

    public SoundFactory(){
        alerteSound = Gdx.audio.newSound(Gdx.files.internal("sounds/alerte.mp3"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/collision.wav"));
        pastilleSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pastille.wav"));
        perteSound = Gdx.audio.newSound(Gdx.files.internal("sounds/perte.mp3"));
        ptailleSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ptaille.wav"));
        ptempsSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ptemps.wav"));
        victoirSound = Gdx.audio.newSound(Gdx.files.internal("sounds/victoire.mp3"));
    }

    public static SoundFactory GetInstance() {
        return soundFactory;
    }

    public Sound GetAlerteSound() {
        return alerteSound;
    }

    public Sound GetCollisionSound() {
        return collisionSound;
    }

    public Sound GetPastilleSound() {
        return pastilleSound;
    }

    public Sound GetPerteSound() {
        return perteSound;
    }

    public Sound GetPtailleSound() {
        return ptailleSound;
    }

    public Sound GetPtempsSound() {
        return ptempsSound;
    }

    public Sound GetVictoirSound() {
        return victoirSound;
    }
}