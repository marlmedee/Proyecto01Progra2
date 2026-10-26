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
        
public class LoginPlayer extends JFrame{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private manejadorUsuarios manejador;
    public LoginPlayer(manejadorUsuarios manejadorCentral){
        this.manejador = manejadorCentral;
        setTitle("Acceso al Sistema");
        setSize(320,250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel lblUsuario = new JLabel("Username:");
        lblUsuario.setBounds(40,30,100,25);
        add(lblUsuario);
        txtUsername = new JTextField();
        txtUsername.setBounds(120, 30, 140, 25);
        add(txtUsername);
        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(40, 80, 100, 25);
        add(lblPass);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 80, 140, 25);
        add(txtPassword);
        btnEntrar = new JButton("Ingresar");
        btnEntrar.setBounds(90, 140, 120, 35);
        add(btnEntrar);
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUsername.getText();
                String pass = new String(txtPassword.getPassword());
                Jugador jugadorLogueado = manejador.iniciasesion(user, pass);
                if (jugadorLogueado != null) {
                    JOptionPane.showMessageDialog(null, "¡Bienvenido, " + user + "!");
                    menuPrincipal menu = new menuPrincipal(jugadorLogueado, manejador);
                    menu.setVisible(true);
                    
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
