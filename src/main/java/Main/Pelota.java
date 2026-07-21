package Main;
import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Pelota {

    //ATRIBUTOS
    private int x;
    private int y;

    private float radio;

    private float velocidadX;
    private float velocidadY;
    private float velocidadInicial;
    private float velocidadMaxima = 10;

    //CONSTRUCTOR
    public Pelota(int x, int y, float radio, float velocidadX, float velocidadY){
        this.x = x;
        this.y = y;
        this.radio = radio;

        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.velocidadInicial = velocidadX;
    }

    //METODOS
    public void dibujar(){
        DrawCircle(x, y, radio, WHITE);
    }

    public void actualizar(Pala palaDerecha, Pala palaIzquierda){
        x += velocidadX; //hace que la pelota se mueva
        y += velocidadY;

        //REBOTE BORDE SUPERIOR
        if ((y - radio) < 0){
            velocidadY = -velocidadY;
            y = (int) radio; //el (int) se pone para conversion de tipos porque radio es float
        }

        //REBOTE BORDE INFERIOR
        if ((y + radio) > GetScreenHeight()){
            velocidadY = -velocidadY;
            y = (int) (GetScreenHeight() - radio);
        }

        //CONTROL DE COLISIONES PALA DERECHA
        if(velocidadX > 0 //Comprueba que la pelota se esta moviendo hacia la derecha
                && (x + radio) >= palaDerecha.getX() //Comprueba que el borde derecho de la pelota ha llegado a la pala
                && (x - radio) <= (palaDerecha.getX() + palaDerecha.getAncho()) //Comprueba que la pelota todavía no ha pasado completamente la pala
                && (y + radio) >= palaDerecha.getY()  //Comprueba que la parte INFERIOR de la pelota ha llegado a la altura de la pala
                && (y - radio) <= (palaDerecha.getY() + palaDerecha.getAlto())){ //Comprueba que la parte SUPERIOR de la pelota todavía está a la altura de la pala

            velocidadX = -velocidadX; //Cambiamos la direccion horizontal de la pala
            x = palaDerecha.getX() - (int) radio; //esta linea saca la pelota de la pala para que no se buggee
            aumentarVelocidad();
        }

        //CONTROL DE COLISIONES PALA IZQUIERDA
        if (velocidadX < 0  // La pelota se está moviendo hacia la izquierda
                && (x - radio) <= palaIzquierda.getX() + palaIzquierda.getAncho()  // El borde izquierdo de la pelota ha llegado a la pala
                && (x + radio) >= palaIzquierda.getX()  // La pelota todavía no ha pasado completamente la pala
                && (y + radio) >= palaIzquierda.getY()  // La parte INFERIOR de la pelota ha llegado a la altura de la pala
                && (y - radio) <= palaIzquierda.getY() + palaIzquierda.getAlto()) {  // La parte SUPERIOR de la pelota todavía está a la altura de la pala

            velocidadX = -velocidadX;  // Cambiamos la dirección horizontal de la pelota
            x = palaIzquierda.getX() + palaIzquierda.getAncho() + (int) radio; //Saca la pelota de la pala para que no se buggee
            aumentarVelocidad();
        }

    }

    public void aumentarVelocidad(){
        if (Math.abs(velocidadX) <= 10){ //Usamos math.abs porque la velocidad puede ser positiva o negativa
            velocidadX *= 1.05f; //La velocidad iria aumentando un 5%
        }
    }

    public boolean haSalidoPorLaIzquierda(){
        //Queremos que devuelva true cuando toda la pelota haya desaparecido,
        //que el lado derecho de la pelota toque el borde izquierdo de la ventana (limite)
        return (x + radio) < 0; //x + radio = lado derecho de la pelota
    }

    public boolean haSalidoPorLaDerecha(){
        //Queremos que devuelva true cuando toda la pelota haya desaparecido,
        //que el lado izquierdo de la pelota toque el borde derecho de la ventana (limite)
        return (x - radio) > GetScreenWidth(); //x - radio = lado izquierdo de la pelota
    }

    public void reiniciar(boolean haciaDerecha){
        //Devolvemos la pelota al centro de la ventana
        x = GetScreenWidth() / 2;
        y = GetScreenHeight() / 2;

        //Decidimos hacia que lado va a moverse
        if (haciaDerecha){
            velocidadX = Math.abs(velocidadInicial); //Math.abs = devuelve el numero absoluto (en positivo)
        } else {
            velocidadX = -Math.abs(velocidadInicial);
        }
    }

}
