/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
public class tableroJuego extends JFrame{
    private JButton casillas[][];
    private Jugador jugadorActivo;
    
    public tableroJuego(Jugador j){
        this.jugadorActivo = j;
        setTitle("Marvel Stratego - Tablero de batalla");
        setSize(700,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new GridLayout(10,10));
        casillas = new JButton[10][10];
        for(int fila = 0;fila<10;fila++){
            for(int col=0;col<10;col++){
                casillas[fila][col] = new JButton();
                casillas[fila][col].setOpaque(true);
                if((fila==4||fila==5)&&(col==2||col==3||col==6||col==7)){
                    casillas[fila][col].setBackground(Color.black);
                    casillas[fila][col].setEnabled(false);
                }
                else{
                    final int f = fila;
                    final int c = col;
                    casillas[fila][col].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Clic detectado en: Fila " + f + ", Columna " + c);
                        }
                    });
                }
                
                add(casillas[fila][col]);

            }
        }
        ArrayList<piezaNormal> listaHeroes = generarFichasal(pieza.Bando.HEROES);
        ArrayList<piezaNormal> listaVillanos = generarFichasal(pieza.Bando.VILLANOS);
        colocarFichasrecu(listaHeroes, 0, 0, 0); 
        colocarFichasrecu(listaVillanos, 0, 6, 0);
    }
    private ArrayList<piezaNormal> generarFichasal(pieza.Bando bando){
        ArrayList<piezaNormal>lista = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            lista.add(new piezaNormal(1,bando,"Ficha"+i));
        }
        Collections.shuffle(lista);
        return lista;
    }
    //una de las funciones recursivas que son solicitadas
    private void colocarFichasrecu(ArrayList<piezaNormal>fichas,int indice,int fila,int col){
        if(indice>=fichas.size()){
            return;
        }
        if(col>=10){
            colocarFichasrecu(fichas,indice,fila+1,0);
            return;
        }
        piezaNormal piezaActual = fichas.get(indice);
        if (piezaActual.getBando() == pieza.Bando.HEROES){
            casillas[fila][col].setText("H");
            casillas[fila][col].setForeground(Color.BLUE);
        } 
        else{
            casillas[fila][col].setText("V");
            casillas[fila][col].setForeground(Color.RED);
        }
        colocarFichasrecu(fichas,indice+1,fila,col+1);
    }
}
