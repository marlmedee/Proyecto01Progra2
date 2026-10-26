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
public class MenuInicial extends JFrame {
private JButton btnLogin;
private JButton btnCrearPlayer;
private manejadorUsuarios manejadorcentral;
public MenuInicial() {
    manejadorcentral = new manejadorUsuarios();
        setTitle("Marvel Stratego - Inicio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Fundamental para no usar auto-posicionamiento
        setLocationRelativeTo(null);
        btnLogin = new JButton("LOG IN");
        btnLogin.setBounds(100, 50, 200, 40);
        btnCrearPlayer = new JButton("CREAR PLAYER");
        btnCrearPlayer.setBounds(100, 120, 200, 40);
        add(btnLogin);
        add(btnCrearPlayer);
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

