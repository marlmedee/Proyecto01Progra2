/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public abstract class pieza {
    public enum Bando{
        HEROES, VILLANOS
    }
    protected int rango;
    protected Bando bando;
    protected boolean oculta;
    protected String nombre;

    public pieza(int rango, Bando bando, String nombre) {
        this.rango = rango;
        this.bando = bando;
        this.nombre = nombre;
        this.oculta = true;
    }
    public int getRango(){
        return rango;
    }
    public Bando getBando(){
        return bando;
    }
    public abstract void revelarIdentidad();
}
