/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class piezaNormal extends pieza{
    public piezaNormal(int rango, pieza.Bando bando, String nombre) {
        super(rango, bando, nombre);
    }
    @Override
    public boolean esMovil() {
        return true;
    }
    // las piezas normales se mueven 1 casilla, el explorador lo sobrescribe
    protected boolean mueveEnLinea() {
        return false;
    }
    @Override
    public boolean validarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        int difF = Math.abs(filaOrigen - filaDestino);
        int difC = Math.abs(colOrigen - colDestino);
        if (difF + difC == 1) {
            return true;
        }
        return mueveEnLinea() && (difF + difC > 0) && (filaOrigen == filaDestino || colOrigen == colDestino);
    }
    @Override
    public atacable.resulCombate defender(pieza atacante) {
        // regla del espia el 1 atacando al mariscal (10) gana
        if (atacante.getRango() ==1&&this.getRango() == 10) {
            return atacable.resulCombate.GANA_ATACANTE;
        }
        // combate normal gana el rango mayor
        if (atacante.getRango() > this.getRango()) {
            return atacable.resulCombate.GANA_ATACANTE;
        } 
        else if (atacante.getRango() < this.getRango()){
            return atacable.resulCombate.GANA_DEFENSOR;
        }
        return atacable.resulCombate.EMPATE;
    }
}
