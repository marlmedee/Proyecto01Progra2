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
public class registroPlayer extends JDialog{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnGuardar;
    private manejadorUsuarios maneja;
    public registroPlayer(manejadorUsuarios manejadorCentral){
        this.maneja = manejadorCentral;
        
        setTitle("Crear nuevo jugador");
        Image iconoVentana = new ImageIcon(getClass().getResource("/imagenes/strategoicon.jpg")).getImage();
        setIconImage(iconoVentana);
        setSize(750,450);
        setLayout(null);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//el que se solicita en el pdf
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/imagenes/marvelregistro.jpg"));
        Image imagenEscalada = iconoFondo.getImage().getScaledInstance(850, 420, Image.SCALE_SMOOTH);
        JLabel lblFondo = new JLabel(new ImageIcon(imagenEscalada));
        lblFondo.setLayout(null); // Layout nulo sobre la imagen
        setContentPane(lblFondo);
        JLabel lblusu = new JLabel("Username: ");
        lblusu.setFont(new Font("Comic Sans", Font.BOLD, 14));
        lblusu.setBounds(40, 100, 100,25);
        lblFondo.add(lblusu);
        txtUsername = new JTextField();
        txtUsername.setBounds(140, 100, 160, 25);
        lblFondo.add(txtUsername);
        JLabel lblpase = new JLabel("Password(5):");
        lblpase.setFont(new Font("Comic", Font.BOLD, 14));
        lblpase.setBounds(40, 160, 120, 25);
        lblFondo.add(lblpase);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 160, 150, 25);
        lblFondo.add(txtPassword);
        
        btnGuardar = new JButton("Guardar jugador");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBounds(110, 230, 160, 35);
        btnGuardar.setBackground(new Color(40, 40, 40)); 
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        add(btnGuardar);
        
        btnGuardar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String user = txtUsername.getText();
                String pass = new String(txtPassword.getPassword());
                try{
                    maneja.regisjuga(user, pass);
                    JOptionPane.showMessageDialog(null, "Jugador creado exitosamente!");
                    dispose();
                }
                catch(usuarioDuplicadoException ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(),"Error de registro",JOptionPane.ERROR_MESSAGE);
                }
                catch (PasswordInvalidoException ex2) {
                    JOptionPane.showMessageDialog(null, ex2.getMessage(), "error de seguridad", JOptionPane.WARNING_MESSAGE);
                }
                catch(Exception ex3){
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex3.getMessage());
                }
            }
        }); 
    }
}
