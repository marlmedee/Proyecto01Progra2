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

public class MenuInicial extends JFrame {
private JButton btnLogin;
private JButton btnCrearPlayer;
private manejadorUsuarios manejadorcentral;
public MenuInicial() {
    manejadorcentral = new manejadorUsuarios();
        setTitle("Marvel Stratego - Inicio");
        Image iconoVentana = new ImageIcon(getClass().getResource("/imagenes/strategoicon.jpg")).getImage();
        setIconImage(iconoVentana);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null); // Fundamental para no usar auto-posicionamiento
        setLocationRelativeTo(null);
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/imagenes/marvelini.jpg"));
        Image imagenEscalada = iconoFondo.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel lblFondo = new JLabel(new ImageIcon(imagenEscalada));
        lblFondo.setLayout(null); // Fundamental para poder poner los botones encima
        setContentPane(lblFondo);
        //creacion de los botones
        btnLogin = new JButton("LOG IN");
        btnLogin.setBounds(100, 70, 200, 40);
        btnLogin.setBackground(new Color(255, 255, 255, 200));
        btnCrearPlayer = new JButton("CREAR PLAYER");
        btnCrearPlayer.setBounds(100, 120, 200, 40);
        btnCrearPlayer.setBackground(new Color(255, 255, 255, 200));
        add(btnLogin);
        add(btnCrearPlayer);
        //aqui las acciones de los botones
        btnCrearPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
               registroPlayer ventanaregis = new registroPlayer(manejadorcentral);
               ventanaregis.setVisible(true);
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPlayer ventanaLogin = new LoginPlayer(manejadorcentral);
                ventanaLogin.setVisible(true);
            }
        });
    }
}

