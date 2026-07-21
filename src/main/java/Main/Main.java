package Main;
import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Main {

    public static void main(String[] args) {

        //Variables para puntuacion
        int puntosDerecha = 0;
        int puntosIzquierda = 0;

        //Variables estado del juego
        boolean juegoTerminado = false;
        boolean juegoPausado = false;

        //Variable ganador
        String ganador = "";

        //Variables iniciales para la ventana
        final int ANCHO_VENTANA = 800;
        int ALTO_VENTANA = 600;

        //Asi se crea la ventana en el main:
        InitWindow(ANCHO_VENTANA, ALTO_VENTANA, "PONG 2");

        SetTargetFPS(60); //Ponemos los fps

        //Construimos las palas del pong
        Pala palaIzquierda = new Pala(100,200,20,150,7.3f, KEY_W, KEY_S);
        Pala palaDerecha = new Pala(700, 200, 20, 150, 7.3f, KEY_UP, KEY_DOWN);

        //Construimos la pala del pong
        Pelota pelota = new Pelota(GetScreenWidth() / 2, GetScreenHeight() / 2, 15, 5, 7);

        //Game loop donde va pasando todo
        while(!WindowShouldClose()){

            if (IsKeyPressed(KEY_P) && !juegoTerminado){
                juegoPausado = !juegoPausado;
            }
             if(!juegoTerminado && !juegoPausado){
                 palaIzquierda.actualizar();
                 palaDerecha.actualizar();
                 pelota.actualizar(palaDerecha, palaIzquierda);

                 //PUNTUACION
                 if(pelota.haSalidoPorLaDerecha()){
                     puntosIzquierda++;
                     pelota.reiniciar(false);
                 }

                 if (pelota.haSalidoPorLaIzquierda()){
                     puntosDerecha++;
                     pelota.reiniciar(true);
                 }

                 //COMPROBAMOS SI LA PARTIDA HA ACABADO
                 if (puntosDerecha >= 10 || puntosIzquierda >= 10){

                     if(puntosDerecha >= 10){
                         ganador = "Jugador derecho";
                     }

                     if (puntosIzquierda >= 10){
                         ganador = "Jugador izquierdo";
                     }
                     juegoTerminado = true;

                 }
             }

            BeginDrawing();
            ClearBackground(BLACK);

            palaIzquierda.dibujar();
            palaDerecha.dibujar();
            pelota.dibujar();
            
            //DIBUJAMOS LOS MARCADORES
            DrawText(String.valueOf(puntosIzquierda), 250, 40, 80, WHITE);
            DrawText(String.valueOf(puntosDerecha), 500, 40, 80, WHITE);
            
            //DIBUJAMOS LINEA DISCONTINUA DEL CENTRO
            for (int i = 0; i < GetScreenHeight(); i+=20) {
                DrawLine(GetScreenWidth() / 2, // X inicial: centro de la pantalla
                        i,                              // Y inicial
                        GetScreenWidth() / 2,           // X final: mismo centro
                        i + 10,                         // Y final: 10 píxeles más abajo
                        WHITE);                         // Color
            }

            //TEXTO PARA PAUSAR
            DrawText("Pulsa P para pausar", 270, 10, 25, WHITE);
            //TEXTO CUANDO PULSAS PAUSA
            if(juegoPausado && !juegoTerminado){
                DrawText("PAUSA", 300, 250, 80, WHITE);
            }

            //TEXTO QUE ANUNCIA GANADOR
            if(juegoTerminado){
                DrawText(ganador + " ha ganado!", 100, 130, 40, WHITE);
            }

            EndDrawing();
        }
        CloseWindow();
    }
}
