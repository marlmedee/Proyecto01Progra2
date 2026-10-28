/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparcial01;

/**
 *
 * @author marlo
 */
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;

public class menuPrincipal extends JFrame {

    private Jugador jugadorActivo;
    private manejadorUsuarios manejador;

    public menuPrincipal(Jugador j, manejadorUsuarios m) {
        this.jugadorActivo = j;
        this.manejador = m;

        setTitle("Marvel Stratego - Menu principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //esto para cargar imagen de fondo
        ImageIcon iconoFondo = new ImageIcon(getClass().getResource("/imagenes/marvelmenu.jpg"));

// Si necesitas ajustarle el tamaño a 800x600, se mantiene igual:
        Image imagenEscalada = iconoFondo.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        iconoFondo = new ImageIcon(imagenEscalada);
        JLabel lblFondo = new JLabel(iconoFondo);
        lblFondo.setLayout(null); 
        setContentPane(lblFondo);
        JLabel lblBienvenida = new JLabel("AGENTE: "+jugadorActivo.getUsername().toUpperCase());
        lblBienvenida.setBounds(30, 20, 300, 30);
        lblBienvenida.setForeground(java.awt.Color.WHITE); 
        add(lblBienvenida); 
        JMenuBar barramenu = new JMenuBar();
        JMenu menuStratego = new JMenu("STRATEGO - Partida Nueva");
        JMenu menuPerfil = new JMenu("MI PERFIL");
        JMenu menuUniverso = new JMenu("UNIVERSO MARVEL");
        JMenu menuCerrar = new JMenu("CERRAR SESIÓN");
        JMenuItem itemPartidanueva = new JMenuItem("Partida nueva");
        menuStratego.add(itemPartidanueva);
        
        //las opciones de "mi perfil"
        JMenuItem itemLog = new JMenuItem("Log de mis ultimos juegos");
        JMenuItem itemCambiarpass = new JMenuItem("Cambiar mi password");
        JMenuItem itemeliminar = new JMenuItem("eliminar mi cuenta");
        menuPerfil.add(itemLog);
        menuPerfil.add(itemCambiarpass);
        menuPerfil.add(itemeliminar);
        //opciones de universo marvel
        JMenuItem itemranking = new JMenuItem("Ranking");
        JMenuItem itemBatallas = new JMenuItem("Batallas");
        menuUniverso.add(itemranking);
        menuUniverso.add(itemBatallas);
        
        JMenuItem itemcerrarses = new JMenuItem("Cerrar sesion ahora");
        menuCerrar.add(itemcerrarses);
        barramenu.add(menuStratego);
        barramenu.add(menuPerfil);
        barramenu.add(menuUniverso);
        barramenu.add(menuCerrar);
        setJMenuBar(barramenu);
        //esto para partida nueva
        itemPartidanueva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableroJuego tablero = new tableroJuego(jugadorActivo);
                tablero.setVisible(true);
            }
        });

        // esto es para cerrar sesion
        itemcerrarses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    dispose(); // Cierra el menú principal
                    
                    // Retorna al menú inicial[cite: 8]
                    MenuInicial menuIni = new MenuInicial();
                    menuIni.setVisible(true);
                }
            }
        });
    }
}

