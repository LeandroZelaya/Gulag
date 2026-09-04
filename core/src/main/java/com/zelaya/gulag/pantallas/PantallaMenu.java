package com.zelaya.gulag.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.Main;
import com.zelaya.gulag.elementos.Imagen;
import com.zelaya.gulag.elementos.Texto;
import com.zelaya.gulag.utiles.Config;
import com.zelaya.gulag.utiles.Recursos;
import com.zelaya.gulag.utiles.Render;

public class PantallaMenu implements Screen {

    private Main main;
    private Imagen fondo;
    private Texto titulo;
    private Texto opcionJugar;
    private Texto opcionSalir;

    private OrthographicCamera camara;
    private Viewport viewport;
    private Vector3 ratonPos;

    // Variables de audio
    private Music musicaMenu;
    private Sound sonidoClic;

    public PantallaMenu(Main main) {
        this.main = main;
        camara = new OrthographicCamera();
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, camara);
        ratonPos = new Vector3();
    }

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDOMENU);
        fondo.setSize(Config.ANCHO, Config.ALTO);

        // Carga de audios
        musicaMenu = Gdx.audio.newMusic(Gdx.files.internal(Recursos.MUSICA_MENU));
        musicaMenu.setLooping(true); // Para que se repita en bucle
        musicaMenu.setVolume(0.5f);  // Volumen al 50%
        musicaMenu.play();

        sonidoClic = Gdx.audio.newSound(Gdx.files.internal(Recursos.EFECTO_CLIC));

        // Título
        titulo = new Texto(Recursos.FUENTEMENU, 90, Color.RED, true);
        titulo.setTexto("GULAG");
        titulo.setPosition(
            (int) ((Config.ANCHO / 2) - (titulo.getAncho() / 2)),
            (int) (Config.ALTO * 0.75f)
        );

        // Botón Jugar
        opcionJugar = new Texto(Recursos.FUENTEMENU, 55, Color.WHITE, true);
        opcionJugar.setTexto("Jugar");
        opcionJugar.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionJugar.getAncho() / 2)),
            (int) (Config.ALTO * 0.45f)
        );

        // Botón Salir
        opcionSalir = new Texto(Recursos.FUENTEMENU, 55, Color.WHITE, true);
        opcionSalir.setTexto("Salir");
        opcionSalir.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionSalir.getAncho() / 2)),
            (int) (Config.ALTO * 0.32f)
        );
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.15f, 0.15f, 0.2f);

        camara.update();
        Render.batch.setProjectionMatrix(camara.combined);

        ratonPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(ratonPos);

        boolean sobreJugar = estaSobreTexto(opcionJugar, ratonPos.x, ratonPos.y);
        boolean sobreSalir = estaSobreTexto(opcionSalir, ratonPos.x, ratonPos.y);

        if (sobreJugar) {
            opcionJugar.setColor(Color.YELLOW);
            if (Gdx.input.isButtonJustPressed(0)) {
                sonidoClic.play(); // Reproduce el efecto de sonido
                musicaMenu.stop(); // Detenemos la música del menú al entrar al juego
                main.setScreen(new PantallaJuego(main));
                return;
            }
        } else {
            opcionJugar.setColor(Color.WHITE);
        }

        if (sobreSalir) {
            opcionSalir.setColor(Color.YELLOW);
            if (Gdx.input.isButtonJustPressed(0)) {
                sonidoClic.play();
                Gdx.app.exit();
                return;
            }
        } else {
            opcionSalir.setColor(Color.WHITE);
        }

        Render.batch.begin();
        fondo.dibujar();
        titulo.dibujar();
        opcionJugar.dibujar();
        opcionSalir.dibujar();
        Render.batch.end();
    }

    private boolean estaSobreTexto(Texto texto, float mx, float my) {
        float x = texto.getX();
        float y = texto.getY();
        float ancho = texto.getAncho();
        float alto = 40f;
        return mx >= x && mx <= (x + ancho) && my >= (y - 10) && my <= (y + alto);
    }

    @Override
    public void resize(int ancho, int alto) {
        viewport.update(ancho, alto, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        if (musicaMenu != null) {
            musicaMenu.stop();
        }
    }

    @Override
    public void dispose() {
        if (fondo != null) fondo.dispose();
        if (titulo != null) titulo.dispose();
        if (opcionJugar != null) opcionJugar.dispose();
        if (opcionSalir != null) opcionSalir.dispose();
        if (musicaMenu != null) musicaMenu.dispose();
        if (sonidoClic != null) sonidoClic.dispose();
    }
}
