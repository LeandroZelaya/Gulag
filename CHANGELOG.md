# Changelog

## [0.1.0] - 16/7/2026

### Agregado

- Creación inicial del proyecto Gulag mediante LibGDX.
- Configuración del archivo .gitignore.
- Creación del archivo README.md.
- Creación del archivo CHANGELOG.md.
- Creación de la estructura inicial de la Wiki del proyecto.

## [0.2.0] - 2026-08-22

### Agregado
- Definición de la estructura de paquetes del proyecto (pantallas, entidades, gestores, ui)
- Clases vacías creadas: PantallaMenu, PantallaJuego, Jugador, GestorEntradas, GestorRecursos, HUD
- 
## [0.3.0] - 2026-08-25

### Agregado
- Clase `Config` con las constantes de resolución del juego (ANCHO y ALTO).
- Clase `Recursos` con las rutas de los assets (fondo del menú y fuente).
- Clase `Render` con el `SpriteBatch` estático compartido entre pantallas.
- Clase `Imagen` para cargar y dibujar texturas mediante `Sprite`.
- Clase `Texto` para generar y dibujar fuentes con `FreeTypeFontGenerator`.
- Implementación completa de `PantallaMenu` con fondo, título y dos opciones navegables con teclado (Jugar / Salir).
- Implementación base de `PantallaJuego` con cámara y viewport configurados.
- Implementación de `Main` con inicialización del `SpriteBatch` y carga de la pantalla inicial.
- Assets agregados: imagen de fondo del menú y fuente personalizada.
