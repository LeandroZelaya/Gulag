package com.zelaya.gulag.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.zelaya.gulag.utiles.Render;

public class Texto {

    private BitmapFont fuente;
    private int x = 0;
    private int y = 0;
    private String texto = "";

    public Texto(String rutaFuente, int dimension, Color color, boolean sombra) {

        FreeTypeFontGenerator generador =
            new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));

        FreeTypeFontGenerator.FreeTypeFontParameter parametros =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametros.size = dimension;
        parametros.color = color;

        if (sombra) {
            parametros.shadowColor = Color.BLACK;
            parametros.shadowOffsetX = 3;
            parametros.shadowOffsetY = 1;
        }

        fuente = generador.generateFont(parametros);
        generador.dispose();
    }

    public void dibujar() {
        fuente.draw(Render.batch, texto, x, y);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getAncho() {
        return fuente.getRegion().getRegionWidth();
    }

    public float getAlto() {
        return fuente.getCapHeight();
    }

    public void dispose() {
        fuente.dispose();
    }

    public void setColor(Color color) {
        fuente.setColor(color);
    }
}
