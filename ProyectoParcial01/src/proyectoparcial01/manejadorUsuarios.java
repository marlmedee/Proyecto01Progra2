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
public class manejadorUsuarios {
    private ArrayList<Jugador> listjuga;
    public manejadorUsuarios(){
        listjuga = new ArrayList<>();
    }
    public Jugador buscarjuga(String username){
        for (Jugador j:listjuga) {
            if(j.getUsername().equals(username)){
                return j;
            }
        }
        return null;
    }
    public void regisjuga(String username, String password) throws usuarioDuplicadoException,PasswordInvalidoException{
        if(buscarjuga(username)!=null){
            throw new usuarioDuplicadoException("Error: El usuario '"+username+"' ya existe, favor intente con otro.");
        }
        if(password == null||password.length()!=5){
            throw new PasswordInvalidoException("Error: la contrasena debe tener exactamente 5 caracteres.");
            
        }
        Jugador nuevojuga = new Jugador(username,password);
        listjuga.add(nuevojuga);
    }
    public Jugador iniciasesion(String username,String password){
        Jugador j = buscarjuga(username);
        if(j !=null && j.getPassword().equals(password)){
            return j;
        }
        return null;
    }
}
