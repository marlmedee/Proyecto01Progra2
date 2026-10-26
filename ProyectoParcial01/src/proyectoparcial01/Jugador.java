/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
public class Jugador {
    private String username;
    private String password;
    private int puntos;
    private int partidasHeroes;
    private int partidasVilanos;

    public Jugador(String username, String password) {
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

    public int getPuntos() {
        return puntos;
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
}
