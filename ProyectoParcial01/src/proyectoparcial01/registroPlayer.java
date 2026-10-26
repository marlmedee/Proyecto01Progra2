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
public class registroPlayer extends JFrame{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnGuardar;
    private manejadorUsuarios maneja;
    public registroPlayer(manejadorUsuarios manejadorCentral){
        this.maneja = manejadorCentral;
        
        setTitle("Crear nuevo jugador");
        setSize(320,250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//el q se solicita en el pdf
        
        JLabel lblusu = new JLabel("Username: ");
        lblusu.setBounds(30, 30, 100,25);
        add(lblusu);
        txtUsername = new JTextField();
        txtUsername.setBounds(110, 30, 150, 25);
        add(txtUsername);
        JLabel lblpase = new JLabel("Contrasena(5):");
        lblpase.setBounds(30, 80, 100, 25);
        add(lblpase);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 80, 140, 25);
        add(txtPassword);
        
        btnGuardar = new JButton("Guardar jugador");
        btnGuardar.setBounds(80,140,150,35);
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
                catch(usuarioDuplicadoException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error de registro",JOptionPane.ERROR_MESSAGE);
                }
                catch (PasswordInvalidoException ex) {
                   
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Clave Inválida", JOptionPane.ERROR_MESSAGE);
                }
            }
        }); 
    }
}
