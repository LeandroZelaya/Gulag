package com.zelaya.gulag.gestores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GestorEntradas extends InputAdapter {
    private boolean arriba, abajo, izquierda, derecha;

    public boolean atacando = false;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arriba = true;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                abajo = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                izquierda = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                derecha = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                arriba = false;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                abajo = false;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                izquierda = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                derecha = false;
                break;
        }
        return true;
    }

    public boolean isArriba() { return arriba; }
    public boolean isAbajo() { return abajo; }
    public boolean isIzquierda() { return izquierda; }
    public boolean isDerecha() { return derecha; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            atacando = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            atacando = false;
        }
        return true;
    }

}
