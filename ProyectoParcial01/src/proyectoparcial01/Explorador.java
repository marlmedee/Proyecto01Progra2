/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class Explorador extends piezaNormal{
    public Explorador(pieza.Bando bando, String nombre) {
        super(2, bando, nombre);
    }

    // el rango 2 se mueve en linea recta como la torre
    @Override
    protected boolean mueveEnLinea() {
        return true;
    }
}
