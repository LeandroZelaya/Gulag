package com.zelaya.gulag.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.zelaya.gulag.elementos.Mapa;
import com.zelaya.gulag.gestores.GestorEntradas;
import com.zelaya.gulag.utiles.Recursos;
import com.zelaya.gulag.utiles.Render;

public class Jugador {

    private float x;
    private float y;

    private final float velocidad = 150f;

    private final float ancho = 32f;
    private final float alto = 32f;

    private Texture texturaPiernas;
    private Texture texturaTorso;
    private Texture texturaAtaque;

    private Animation<TextureRegion> animacionPiernas;
    private Animation<TextureRegion> animacionAtaque;

    private TextureRegion frameTorso;

    private float tiempoAnimacionPiernas = 0f;
    private float tiempoAnimacionAtaque = 0f;

    private boolean moviendose = false;
    private boolean atacando = false;


    private boolean puedeAtacar = false;

    private final Mapa mapa;
    private final TiledMapTileLayer colisiones;
    private final GestorEntradas gestorEntradas;

    private Rectangle hitbox;

    private Vector3 mousePosicion;

    private final float anchoHitbox = 12f;
    private final float altoHitbox = 12f;

    private final float offsetHitboxX = 10f;
    private final float offsetHitboxY = 10f;

    public Jugador(
        Mapa mapa,
        float x,
        float y,
        GestorEntradas gestorEntradas
    ) {

        this.mapa = mapa;
        this.x = x;
        this.y = y;
        this.gestorEntradas = gestorEntradas;

        this.colisiones = mapa.getColisiones();

        hitbox = new Rectangle(
            x + offsetHitboxX,
            y + offsetHitboxY,
            anchoHitbox,
            altoHitbox
        );
        mousePosicion = new Vector3();

        cargarSprites();
    }

    private void cargarSprites() {
        texturaPiernas = new Texture(
            Gdx.files.internal(Recursos.PIERNAS)
        );

        texturaTorso = new Texture(
            Gdx.files.internal(Recursos.TORSO)
        );

        texturaAtaque = new Texture(
            Gdx.files.internal(Recursos.ATAQUE_PUNO)
        );

        cargarAnimacionPiernas();
        cargarTorso();
        cargarAnimacionAtaque();
    }

    private void cargarAnimacionPiernas() {

        int cantidadFrames = 13;

        int anchoFrame =
            texturaPiernas.getWidth() / cantidadFrames;

        int altoFrame =
            texturaPiernas.getHeight();

        TextureRegion[][] regiones =
            TextureRegion.split(
                texturaPiernas,
                anchoFrame,
                altoFrame
            );

        TextureRegion[] frames =
            new TextureRegion[cantidadFrames];

        for (int i = 0; i < cantidadFrames; i++) {
            frames[i] = regiones[0][i];
        }

        animacionPiernas =
            new Animation<TextureRegion>(
                0.07f,
                frames
            );

        animacionPiernas.setPlayMode(
            Animation.PlayMode.LOOP
        );
    }

    private void cargarTorso() {

        int cantidadFrames = 8;

        int anchoFrame =
            texturaTorso.getWidth() / cantidadFrames;

        int altoFrame =
            texturaTorso.getHeight();

        TextureRegion[][] regiones =
            TextureRegion.split(
                texturaTorso,
                anchoFrame,
                altoFrame
            );

        frameTorso = regiones[0][0];
    }

    private void cargarAnimacionAtaque() {

        int cantidadFrames = 7;

        int anchoFrame =
            texturaAtaque.getWidth() / cantidadFrames;

        int altoFrame =
            texturaAtaque.getHeight();

        TextureRegion[][] regiones =
            TextureRegion.split(
                texturaAtaque,
                anchoFrame,
                altoFrame
            );

        TextureRegion[] frames =
            new TextureRegion[cantidadFrames];

        for (int i = 0; i < cantidadFrames; i++) {
            frames[i] = regiones[0][i];
        }

        animacionAtaque =
            new Animation<TextureRegion>(
                0.05f,
                frames
            );

        animacionAtaque.setPlayMode(
            Animation.PlayMode.NORMAL
        );
    }

    public void actualizar(
        float delta,
        OrthographicCamera camara
    ) {

        float movimientoX = 0f;
        float movimientoY = 0f;

        if (gestorEntradas.isArriba()) {
            movimientoY += 1f;
        }

        if (gestorEntradas.isAbajo()) {
            movimientoY -= 1f;
        }

        if (gestorEntradas.isIzquierda()) {
            movimientoX -= 1f;
        }

        if (gestorEntradas.isDerecha()) {
            movimientoX += 1f;
        }

        if (movimientoX != 0f || movimientoY != 0f) {

            moviendose = true;

            float longitud =
                (float) Math.sqrt(
                    movimientoX * movimientoX
                        + movimientoY * movimientoY
                );

            movimientoX /= longitud;
            movimientoY /= longitud;

            tiempoAnimacionPiernas += delta;

        } else {

            moviendose = false;

            tiempoAnimacionPiernas = 0f;
        }

        float desplazamientoX =
            movimientoX * velocidad * delta;

        float desplazamientoY =
            movimientoY * velocidad * delta;


        float nuevaX = x + desplazamientoX;

        if (!hayColision(nuevaX, y)) {
            x = nuevaX;
        }

        actualizarHitbox();


        float nuevaY = y + desplazamientoY;

        if (!hayColision(x, nuevaY)) {
            y = nuevaY;
        }

        actualizarHitbox();

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            puedeAtacar = true;
        }


        if (
            Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                && puedeAtacar
                && !atacando
        ) {

            atacando = true;
            tiempoAnimacionAtaque = 0f;

            puedeAtacar = false;
        }


        if (atacando) {

            tiempoAnimacionAtaque += delta;

            if (
                animacionAtaque.isAnimationFinished(
                    tiempoAnimacionAtaque
                )
            ) {

                atacando = false;
                tiempoAnimacionAtaque = 0f;
            }
        }
    }

    private void actualizarHitbox() {

        hitbox.setPosition(
            x + offsetHitboxX,
            y + offsetHitboxY
        );
    }

    private boolean hayColision(
        float nuevaX,
        float nuevaY
    ) {

        if (colisiones == null) {
            return false;
        }

        float hitboxX =
            nuevaX + offsetHitboxX;

        float hitboxY =
            nuevaY + offsetHitboxY;

        float hitboxDerecha =
            hitboxX + anchoHitbox;

        float hitboxArriba =
            hitboxY + altoHitbox;

        int tileAncho =
            colisiones.getTileWidth();

        int tileAlto =
            colisiones.getTileHeight();

        int columnaInicial =
            (int) Math.floor(
                hitboxX / tileAncho
            );

        int columnaFinal =
            (int) Math.floor(
                (hitboxDerecha - 0.01f)
                    / tileAncho
            );

        int filaInicial =
            (int) Math.floor(
                hitboxY / tileAlto
            );

        int filaFinal =
            (int) Math.floor(
                (hitboxArriba - 0.01f)
                    / tileAlto
            );


        for (
            int columna = columnaInicial;
            columna <= columnaFinal;
            columna++
        ) {

            for (
                int fila = filaInicial;
                fila <= filaFinal;
                fila++
            ) {

                if (
                    columna < 0
                        || columna >= colisiones.getWidth()
                        || fila < 0
                        || fila >= colisiones.getHeight()
                ) {

                    return true;
                }

                if (
                    colisiones.getCell(
                        columna,
                        fila
                    ) != null
                ) {

                    return true;
                }
            }
        }

        return false;
    }

    public void dibujar(
        OrthographicCamera camara
    ) {


        mousePosicion.set(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        camara.unproject(mousePosicion);

        float centroX =
            x + ancho / 2f;

        float centroY =
            y + alto / 2f;

        float dx =
            mousePosicion.x - centroX;

        float dy =
            mousePosicion.y - centroY;

        float anguloMouse =
            MathUtils.atan2(
                dy,
                dx
            ) * MathUtils.radiansToDegrees;

        Render.batch.setProjectionMatrix(
            camara.combined
        );

        Render.batch.begin();


        if (moviendose) {

            TextureRegion framePiernas =
                animacionPiernas.getKeyFrame(
                    tiempoAnimacionPiernas
                );


            Render.batch.draw(
                framePiernas,

                centroX - 16f,
                centroY - 16f,

                16f,
                16f,

                32f,
                32f,

                1f,
                1f,

                anguloMouse
            );
        }



        TextureRegion frameTorsoActual;

        if (atacando) {

            frameTorsoActual =
                animacionAtaque.getKeyFrame(
                    tiempoAnimacionAtaque
                );

        } else {

            frameTorsoActual = frameTorso;
        }

        Render.batch.draw(
            frameTorsoActual,

            centroX - 16f,
            centroY - 16f,

            16f,
            16f,

            32f,
            32f,

            1f,
            1f,

            anguloMouse
        );

        Render.batch.end();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean estaAtacando() {
        return atacando;
    }

    public boolean estaMoviendose() {
        return moviendose;
    }

    public void dispose() {

        if (texturaPiernas != null) {
            texturaPiernas.dispose();
        }

        if (texturaTorso != null) {
            texturaTorso.dispose();
        }

        if (texturaAtaque != null) {
            texturaAtaque.dispose();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
