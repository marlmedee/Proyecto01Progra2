/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
import java.awt.*;
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
        java.awt.Image iconoVentana = new ImageIcon(getClass().getResource("/imagenes/strategoicon.jpg")).getImage();
        setIconImage(iconoVentana);
        
        setSize(850,420);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/imagenes/marvelcarga.jpg"));
        Image imagenEscalada = iconoFondo.getImage().getScaledInstance(850, 470, Image.SCALE_SMOOTH);
        JLabel lblFondo = new JLabel(new ImageIcon(imagenEscalada));
        lblFondo.setLayout(null); // Esto nos permite usar coordenadas (setBounds) encima de la imagen
        setContentPane(lblFondo);
        JLabel lblUsuario = new JLabel("Username:");
        lblUsuario.setFont(new Font("Comic Sans", Font.BOLD, 14));
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setBounds(30,100,100,25);
        add(lblUsuario);
        txtUsername = new JTextField();
        txtUsername.setBounds(110, 100, 160, 25);
        add(txtUsername);
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("Comic Sans", Font.BOLD, 14));
        lblPass.setForeground(Color.WHITE);
        lblPass.setBounds(30, 160, 100, 25);
        add(lblPass);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(110, 160, 160, 25);
        add(txtPassword);
        btnEntrar = new JButton("Ingresar");
        btnEntrar.setFont(new Font("Comic Sans", Font.BOLD, 14));
        btnEntrar.setBounds(90, 230, 120, 35);
        btnEntrar.setBackground(new Color(226, 54, 54));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false); 
        btnEntrar.setBorderPainted(false); 
        btnEntrar.setOpaque(true);
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
                    for (java.awt.Window window : java.awt.Window.getWindows()){
                        if (window instanceof MenuInicial) {
                            window.dispose();
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
