package com.zelaya.gulag.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.zelaya.gulag.utiles.Render;

public class Imagen {

    private Texture textura;
    private Sprite sprite;

    public Imagen(String ruta) {
        textura = new Texture(ruta);
        sprite = new Sprite(textura);
    }

    public void dibujar() {
        sprite.draw(Render.batch);
    }

    public void setTransparencia(float transparencia) {
        sprite.setAlpha(transparencia);
    }

    public void setSize(float ancho, float alto) {
        sprite.setSize(ancho, alto);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public float getAncho() {
        return sprite.getWidth();
    }

    public float getAlto() {
        return sprite.getHeight();
    }

    public void dispose() {
        textura.dispose();
    }
}
