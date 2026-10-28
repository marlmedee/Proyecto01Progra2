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
    }
}
