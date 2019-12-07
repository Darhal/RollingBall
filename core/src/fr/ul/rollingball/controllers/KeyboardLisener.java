package fr.ul.rollingball.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardLisener implements InputProcessor
{
    private static float ACC_COEF = 200000.f;
    private boolean isQuit;
    private boolean isDebug;
    private Vector2 accelration;

    public KeyboardLisener()
    {
        super();
        isQuit = isDebug = false;
        accelration = new Vector2();
    }

    public void resetAcceleration()
    {
        accelration = new Vector2(0.f,0.f);
    }

    public boolean isQuit() {
        return isQuit;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public Vector2 getAccelration() {
        return accelration;
    }

    @Override
    public boolean keyDown(int code)
    {
        if (code  == Input.Keys.ESCAPE || code == Input.Keys.Q){
           isQuit = true;
        }else if(code == Input.Keys.LEFT){
            accelration.x = -ACC_COEF;
        }else if(code == Input.Keys.RIGHT){
            accelration.x = ACC_COEF;
        }else if(code == Input.Keys.UP){
            accelration.y = ACC_COEF;
        }else if(code == Input.Keys.DOWN){
            accelration.y = -ACC_COEF;
        }

        return true;
    }

    @Override
    public boolean keyUp(int code)
    {
        if (code == Input.Keys.D){
            isDebug = !isDebug;
        }else if(code == Input.Keys.LEFT){
            accelration.x = 0;
        }else if(code == Input.Keys.RIGHT){
            accelration.x = 0;
        }else if(code == Input.Keys.UP){
            accelration.y = 0;
        }else if(code == Input.Keys.DOWN){
            accelration.y = 0;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
