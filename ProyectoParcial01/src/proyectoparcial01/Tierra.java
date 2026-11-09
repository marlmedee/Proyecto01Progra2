/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class Tierra extends pieza{
    public Tierra(pieza.Bando bando, String nombre) {
        super(0, bando, nombre);
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
        // cualquier pieza que ataque la tierra la captura
        return atacable.resulCombate.GANA_ATACANTE;
    }
}
