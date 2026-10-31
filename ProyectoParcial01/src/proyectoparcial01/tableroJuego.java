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
    private piezaNormal[][] matrizLogica; 
    private int filaOrigen = -1;
    private int colOrigen = -1;
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
        matrizLogica = new piezaNormal[10][10];
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
                            if(filaOrigen == -1) {
                                if (matrizLogica[f][c]!=null) {
                                    // regla la tierra (0) y las bombas 1 no se mueven
                                    if (matrizLogica[f][c].getRango()==0||matrizLogica[f][c].getRango()==11) {
                                        JOptionPane.showMessageDialog(null, "las bombas y la tierra no se pueden mover");
                                    }
                                    else{
                                        filaOrigen=f;
                                        colOrigen=c;
                                        casillas[f][c].setBackground(Color.YELLOW); 
                                    }
                                }
                            } 
                            else{
                                //si toca otra pieza de su bando cambiamos la seleccion
                                if (matrizLogica[f][c] != null && matrizLogica[f][c].getBando() == matrizLogica[filaOrigen][colOrigen].getBando()) {
                                    casillas[filaOrigen][colOrigen].setBackground(Color.LIGHT_GRAY); 
                                    filaOrigen = f;
                                    colOrigen = c;
                                    casillas[f][c].setBackground(Color.YELLOW); 
                                }
                                //Si es una casilla vacía o del enemigo intentamos mover
                                else{
                                    
                                    int difFila=Math.abs(filaOrigen-f);
                                    int difCol=Math.abs(colOrigen-c);
                                    
                                    if((difFila + difCol)==1&&casillas[f][c].isEnabled()){
                                        if(matrizLogica[f][c]==null){
                                            casillas[f][c].setText(casillas[filaOrigen][colOrigen].getText());
                                            casillas[f][c].setForeground(casillas[filaOrigen][colOrigen].getForeground());
                                            matrizLogica[f][c] = matrizLogica[filaOrigen][colOrigen];
                                        }
                                        else{
                                            piezaNormal atacante = matrizLogica[filaOrigen][colOrigen];
                                            piezaNormal defensor = matrizLogica[f][c];
                                            
                                            //llamamos a la interfaz que programe en el modulo 1
                                            atacable.resulCombate resultado = defensor.defender(atacante);
                                            
                                            if(resultado == atacable.resulCombate.GANA_ATACANTE){
                                                JOptionPane.showMessageDialog(null, "¡Ganaste el combate!");
                                                casillas[f][c].setText(casillas[filaOrigen][colOrigen].getText());
                                                casillas[f][c].setForeground(casillas[filaOrigen][colOrigen].getForeground());
                                                matrizLogica[f][c] = atacante;
                                            } 
                                            else if(resultado == atacable.resulCombate.GANA_DEFENSOR){
                                                JOptionPane.showMessageDialog(null, "Tu pieza fue destruida!");
                                                // El defensor se queda, la matriz destino no cambia
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(null, "Empate! Ambas piezas se destruyen");
                                                casillas[f][c].setText("");
                                                matrizLogica[f][c] = null;
                                            }
                                        }
                                        
                                        //sin importar quien gane el que ataca abandona su casilla original
                                        casillas[filaOrigen][colOrigen].setText("");
                                        matrizLogica[filaOrigen][colOrigen] = null;
                                        
                                    } 
                                    else{
                                        JOptionPane.showMessageDialog(null, "Movimiento inválido. Solo 1 casilla arriba, abajo, izquierda o derecha.");
                                    }
                                    
                                    // limpiamos todo para el siguiente turno
                                    casillas[filaOrigen][colOrigen].setBackground(Color.LIGHT_GRAY);
                                    filaOrigen=-1;
                                    colOrigen=-1;
                                }
                            }
                        }
                    });
                }
                add(casillas[fila][col]);
        }   }
        ArrayList<piezaNormal>listaHeroes=generarFichasal(pieza.Bando.HEROES);
        ArrayList<piezaNormal>listaVillanos=generarFichasal(pieza.Bando.VILLANOS);
        colocarFichasrecu(listaHeroes, 0, 0, 0); 
        colocarFichasrecu(listaVillanos, 0, 6, 0);
    }
    private ArrayList<piezaNormal> generarFichasal(pieza.Bando bando){
        ArrayList<piezaNormal>lista = new ArrayList<>();
        String prefijo=(bando==pieza.Bando.HEROES)? "H":"V";
        lista.add(new piezaNormal(0,bando,"Tierra"));
        lista.add(new piezaNormal(10,bando,prefijo+"Rango 10"));
        lista.add(new piezaNormal(9,bando,prefijo+"Rango 9"));
        for (int i = 0;i < 2; i++) {
            lista.add(new piezaNormal(8,bando,prefijo+" rango 8"));
        }
        for (int i = 0; i < 3; i++) {
            lista.add(new piezaNormal(7,bando,prefijo+" rango 7"));
        }
        for (int i = 0; i < 4; i++) {
            lista.add(new piezaNormal(6,bando,prefijo+" rango 6"));
        }
        for (int i = 0; i < 4; i++) {
            lista.add(new piezaNormal(5,bando,prefijo+" rango 5"));
        }
        for (int i = 0; i < 4; i++) {
            lista.add(new piezaNormal(4,bando,prefijo+" rango 4"));
        }
        for (int i = 0; i < 5; i++) {
            lista.add(new piezaNormal(3,bando,prefijo+" rango 3"));
        }
        for (int i = 0; i < 8; i++) {
            lista.add(new piezaNormal(2,bando,prefijo+" rango 2"));
        }
        lista.add(new piezaNormal(1,bando,prefijo+" rango 1"));
        for (int i = 0; i < 6; i++) {
            lista.add(new piezaNormal(11, bando, "Bomba"));
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
        piezaNormal piezaActual=fichas.get(indice);
        matrizLogica[fila][col]=piezaActual;
        if (piezaActual.getBando()==pieza.Bando.HEROES){
            casillas[fila][col].setText("H"+piezaActual.getRango());
            casillas[fila][col].setForeground(Color.BLUE);
        } 
        else{
            casillas[fila][col].setText("V"+piezaActual.getRango());
            casillas[fila][col].setForeground(Color.RED);
        }
        colocarFichasrecu(fichas,indice+1,fila,col+1);
    }
}
