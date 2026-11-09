/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class Bomba extends pieza{
    public Bomba(pieza.Bando bando, String nombre) {
        super(11, bando, nombre);
    }
    @Override
    public boolean esMovil() {
        return false;
    }
    @Override
    public boolean validarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        return false;
    }
    @Override
    public atacable.resulCombate defender(pieza atacante) {
        // solo el rango 3 (minero) destruye bombas
        if (atacante.getRango() == 3) {
            return atacable.resulCombate.GANA_ATACANTE;
        }
        return atacable.resulCombate.GANA_DEFENSOR;
    }
}
