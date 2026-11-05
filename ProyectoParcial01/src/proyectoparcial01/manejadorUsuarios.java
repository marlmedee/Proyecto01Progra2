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
import java.io.*;
public class manejadorUsuarios {
    private ArrayList<Jugador> listjuga;
    public manejadorUsuarios(){
        cargarUsuarios();
        
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
        guardarUsuarios();
    }
    public Jugador iniciasesion(String username,String password){
        Jugador j = buscarjuga(username);
        if(j !=null && j.getPassword().equals(password)){
            return j;
        }
        return null;
    }
    public void eliminarUsuario(Jugador usuarioAEliminar) {
        listjuga.remove(usuarioAEliminar);
        guardarUsuarios();
    }
    public void guardarUsuarios() {
        try{
            FileOutputStream archivo = new FileOutputStream("registro_jugadores.stratego");
            ObjectOutputStream escritor = new ObjectOutputStream(archivo);
            escritor.writeObject(listjuga);
            escritor.close();
            archivo.close();
        } catch (Exception e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
        
    }
    private void cargarUsuarios(){
        try{
            FileInputStream archivo = new FileInputStream("registro_jugadores.stratego");
            ObjectInputStream lector = new ObjectInputStream(archivo);
            listjuga = (ArrayList<Jugador>) lector.readObject();
            lector.close();
            archivo.close();
        } catch (Exception e){
            //si hay un error o es la primera vez que se corre el juego y el archivo no existe, iniciamos la lista vacía
            listjuga = new ArrayList<>();
        }
    }
}

