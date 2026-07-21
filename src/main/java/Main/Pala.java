package Main;
import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Pala {

    //Atributos
    private int x; //posicion x e y de la pala
    private int y;

    private int ancho;
    private int alto;

    private float velocidad;

    private int teclaArriba; //variables que van a guardar las teclas que usa cada pala
    private int teclaAbajo;

    //CONSTRUCTOR
    public Pala(int x, int y, int ancho, int alto, float velocidad, int teclaArriba, int teclaAbajo){
       this.x = x;
       this.y = y;
       this.ancho = ancho;
       this.alto = alto;
       this.velocidad = velocidad;
       this.teclaArriba = teclaArriba;
       this.teclaAbajo = teclaAbajo;
    }

    //GETTERS
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getAncho(){
        return ancho;
    }

    public int getAlto(){
        return alto;
    }

    public float getVelocidad(){
        return velocidad;
    }

    //METODOS

    public void dibujar(){
      DrawRectangle(x, y, ancho, alto, WHITE);
    }

    public void actualizar(){

        //MOVIMIENTO VERTICAL
        if(IsKeyDown(teclaArriba)) {
            y -= velocidad;
        }
        if (IsKeyDown(teclaAbajo)){
            y += velocidad;
        }


        //CONTROL DE LIMITES VERTICAL
        if (y < 0){
            y = 0;
        }
        if (y > (GetScreenHeight() - alto)){
            y = GetScreenHeight() - alto;
        }
    }
}
