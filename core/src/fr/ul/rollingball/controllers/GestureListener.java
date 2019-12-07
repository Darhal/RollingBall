package fr.ul.rollingball.controllers;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class GestureListener implements GestureDetector.GestureListener
{
    private static float COEF_ACC = 500.f;
    private boolean isGestDetected;
    private Vector2 accelration;

    public GestureListener()
    {
        isGestDetected = false;
        accelration = new Vector2();
    }

    public Vector2 getAccelration()
    {
        if (isGestDetected) {
            isGestDetected = false;
            return accelration;
        }else{
            return new Vector2();
        }
    }


    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        accelration.x = velocityX * COEF_ACC;
        accelration.y = -velocityY * COEF_ACC;
        isGestDetected = true;
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
