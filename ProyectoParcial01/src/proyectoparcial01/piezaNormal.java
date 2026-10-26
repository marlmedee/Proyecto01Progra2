/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class piezaNormal extends pieza implements movible, atacable {
    public piezaNormal(int rango,pieza.Bando bando, String nombre){
        super(rango,bando,nombre);
    }
    @Override
    public void revelarIdentidad() {
        this.oculta = false;
    }

    @Override
    public boolean validarMovimiento(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
   
        return true; 
    }

    @Override
    public atacable.resulCombate defender(pieza atacante) {
      
        if (this.rango < atacante.getRango()) {
            return atacable.resulCombate.GANA_ATACANTE;
        } else if (this.rango > atacante.getRango()) {
            return atacable.resulCombate.GANA_DEFENSOR;
        }
      
        return atacable.resulCombate.EMPATE;
    }
}
