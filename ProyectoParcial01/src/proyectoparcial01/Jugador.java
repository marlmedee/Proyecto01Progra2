/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
import java.util.ArrayList;
public class Jugador extends CuentaUsuario{
    private static final long serialVersionUID = 1L;
    private ArrayList<String> logsPartidas;
    private int puntos;
    private int partidasHeroes;
    private int partidasVilanos;

    public Jugador(String username, String password) {
        super(username, password); // Le enviamos los datos a la clase padre
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVilanos = 0;
        this.logsPartidas = new ArrayList<>();
    }

    // Ya no necesitamos poner getUsername, getPassword ni setPassword aquí, 
    // porque los está heredando automáticamente de CuentaUsuario.

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad){
        this.puntos += cantidad;
    }
    
    public void sumarpartiHero(){
        this.partidasHeroes++;
    }
    
    public void sumarpartiVilla(){
        this.partidasVilanos++;
    }

    public int getPartidasHeroes() {
        return partidasHeroes;
    }
    
    public int getPartidasVilanos() {
        return partidasVilanos;
    }
    
    public ArrayList<String> getLogsPartidas(){
        return logsPartidas;
    }
    
    public void agregarLog(String resultado){
        this.logsPartidas.add(resultado);
    }
}
