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
import java.awt.event.*;

public class menuPrincipal extends JFrame {

    private Jugador jugadorActivo;
    private manejadorUsuarios manejador;

    public menuPrincipal(Jugador j, manejadorUsuarios m) {
        this.jugadorActivo = j;
        this.manejador = m;

        setTitle("Marvel Stratego - Menú Principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon iconoFondo = new ImageIcon("fondo.jpg");
        JLabel lblFondo = new JLabel(iconoFondo);
        lblFondo.setLayout(null);
        setContentPane(lblFondo);
        JLabel lblBienvenida = new JLabel("AGENTE: "+jugadorActivo.getUsername().toUpperCase());
        lblBienvenida.setBounds(30, 20, 300, 30);
        lblBienvenida.setForeground(java.awt.Color.WHITE); 
        add(lblBienvenida); 
        JButton btnPartida = new JButton("STRATEGO - Partida Nueva");
        btnPartida.setBounds(250, 150, 300, 50);
        add(btnPartida);
        JButton btnPerfil = new JButton("MI PERFIL");
        btnPerfil.setBounds(250, 220, 300, 50);
        add(btnPerfil);
        JButton btnUniverso = new JButton("UNIVERSO MARVEL");
        btnUniverso.setBounds(250, 290, 300, 50);
        add(btnUniverso);
        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.setBounds(250, 360, 300, 50);
        add(btnCerrar);
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        btnPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableroJuego tablero = new tableroJuego(jugadorActivo);
                tablero.setVisible(true);
            }
        });
    }
}

