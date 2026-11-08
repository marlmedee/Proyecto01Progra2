/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
import java.io.Serializable;
public class piezaNormal extends pieza implements movible, atacable, Serializable {
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
    public atacable.resulCombate defender(pieza atacante){
      //esta es la regla del minero (el de 3) que es el unico que sobrevive a las bombas
        if(atacante.getRango() == 3 && this.getRango() == 11){
            return atacable.resulCombate.GANA_ATACANTE;
        }
        // esta es la regla del espia, if el espia que es el 1 ataca primero asesina al Mariscal (10)
        if(atacante.getRango()==1&&this.getRango()==10){
            return atacable.resulCombate.GANA_ATACANTE;
        }
        //esta es las reglas de las bombas(las 11) Destruye a cualquier otra pieza que intente tocarla
        if(this.getRango()==11){
            return atacable.resulCombate.GANA_DEFENSOR;
        }
        // y esto es la logica del combate normal que el numero mayor gana
        if(atacante.getRango()>this.getRango()){
            return atacable.resulCombate.GANA_ATACANTE;
        } 
        else if(atacante.getRango() < this.getRango()){
            return atacable.resulCombate.GANA_DEFENSOR;
        }
        else{
            return atacable.resulCombate.EMPATE;
        }
    }

    public String getNombre() {
       return this.nombre;
    }
}
