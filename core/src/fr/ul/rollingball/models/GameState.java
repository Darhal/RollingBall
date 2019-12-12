package fr.ul.rollingball.models;

import com.badlogic.gdx.utils.Timer;
import fr.ul.rollingball.dataFactories.SoundFactory;

import static fr.ul.rollingball.dataFactories.SoundFactory.MASTER_VOLUME;

public class GameState
{
    public enum STATE
    {
        EN_COURS, VICTOIRE, PERTE, ARRET
    }

    private static int elapsedTime;
    private STATE state;
    private int score;
    private int remainingTime;
    private int nbPastilleNormalConsumed;
    private Timer.Task secondCounter;

    public GameState()
    {
        elapsedTime = nbPastilleNormalConsumed = score = remainingTime = 0;
        state = STATE.ARRET;
        secondCounter = new Timer.Task() {
            @Override
            public void run() {
                countDown();
            }
        };
        Timer.schedule(secondCounter, 1, 1);
    }

    private void countDown()
    {
        if (IsGameInState(STATE.EN_COURS)){
            if (remainingTime < 0)
                return;

            remainingTime -= 1;

            if (remainingTime < 10){
                if (remainingTime <= 0){
                    setState(STATE.PERTE);
                    SoundFactory.GetInstance().GetPerteSound().play(MASTER_VOLUME);
                }else{
                    SoundFactory.GetInstance().GetAlerteSound().play(MASTER_VOLUME);
                }
            }
        }
    }

    public boolean IsGameInState(STATE s)
    {
        return state == s;
    }

    public static int getElapsedTime() {
        return elapsedTime;
    }

    public static void setElapsedTime(int elapsedTime) {
        GameState.elapsedTime = elapsedTime;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getNbPastilleNormalConsumed() {
        return nbPastilleNormalConsumed;
    }

    public void setNbPastilleNormalConsumed(int nb_pastille_normal_consumed) {
        this.nbPastilleNormalConsumed = nb_pastille_normal_consumed;
    }

    public Timer.Task getSecondCounter() {
        return secondCounter;
    }

    public void setSecondCounter(Timer.Task secondCounter) {
        this.secondCounter = secondCounter;
    }
}
