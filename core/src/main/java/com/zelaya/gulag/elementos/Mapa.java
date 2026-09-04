package com.zelaya.gulag.elementos;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Mapa {
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderizador;
    private TiledMapTileLayer colisiones;

    public Mapa(String ruta) {
        mapa = new TmxMapLoader().load(ruta);
        renderizador = new OrthogonalTiledMapRenderer(mapa);

        // Obtener la capa de colisiones de forma segura
        colisiones = (TiledMapTileLayer) mapa.getLayers().get("colisiones");

        // Si la capa existe, ocultarla
        if (colisiones != null) {
            colisiones.setVisible(false);
        } else {
            System.out.println("️ Advertencia: No se encontró la capa 'colisiones' en el mapa");
        }
    }

    public void dibujar(OrthographicCamera camara) {
        renderizador.setView(camara);
        renderizador.render();
    }

    public TiledMapTileLayer getColisiones() {
        return colisiones;
    }

    public void dispose() {
        // Importante liberar también el renderizador
        if (renderizador != null) {
            renderizador.dispose();
        }
        if (mapa != null) {
            mapa.dispose();
        }
    }
}
