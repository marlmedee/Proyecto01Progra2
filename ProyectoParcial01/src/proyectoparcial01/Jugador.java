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
import java.io.Serializable;
public class Jugador implements Serializable{
    private String username;
    private String password;
    private static final long serialVersionUID = 1L;
    private ArrayList<String> logsPartidas;
    private int puntos;
    private int partidasHeroes;
    private int partidasVilanos;

    public Jugador(String username, String password) {
        this.puntos = 0;
        this.logsPartidas = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVilanos = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public ArrayList<String> getLogsPartidas(){
        return logsPartidas;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void sumarPuntos(int puntosGanados){
        this.puntos +=puntosGanados;
    }
    public void sumarpartiHero(){
        this.partidasHeroes++;
    }
    public void sumarpartiVilla(){
        this.partidasVilanos++;
    }
    public void agregarLog(String resultado){
        this.logsPartidas.add(resultado);
    }
}
