/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public interface atacable {
    public enum resulCombate {
        GANA_ATACANTE,GANA_DEFENSOR,EMPATE
    }
    resulCombate defender(pieza atacante);
}
