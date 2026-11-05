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

public class menuPrincipal extends JFrame {

    private Jugador jugadorActivo;
    private manejadorUsuarios manejador;

    public menuPrincipal(Jugador j, manejadorUsuarios m) {
        this.jugadorActivo = j;
        this.manejador = m;

        setTitle("Marvel Stratego - Menu principal");
        Image iconoVentana = new ImageIcon(getClass().getResource("/imagenes/strategoicon.jpg")).getImage();
        setIconImage(iconoVentana);
        setSize(750, 600);
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
                tableroJuego tablero = new tableroJuego(jugadorActivo,manejador);
                tablero.setVisible(true);
            }
        });
        // esta accion es para cambiar contrasena
        itemCambiarpass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passActual = JOptionPane.showInputDialog(null, "Ingresa tu contraseña actual:");
                
                // Verificamos que no haya cancelado y que la contraseña coincida
                if (passActual != null) {
                    if (passActual.equals(jugadorActivo.getPassword())) {
                        String passNueva = JOptionPane.showInputDialog(null, "Ingresa tu NUEVA contraseña (exactamente 5 caracteres):");
                        
                        if (passNueva != null){
                            if (passNueva.length() == 5) { // regla tipica del Stratego en programacion
                                jugadorActivo.setPassword(passNueva);
                                manejador.guardarUsuarios();
                                JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente!");
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Error: La contraseña debe tener exactamente 5 caracteres", "Validacion", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "password incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //la accion para eliminar cuenta
        itemeliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, 
                        "Estas bien SEGURO de eliminar tu cuenta?\n(Se perdera todo tu historial)", 
                        "Eliminar Cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    //verificacion de seguridad final
                    String passValidacion = JOptionPane.showInputDialog(null, "Por seguridad, ingresa tu contraseña para eliminar:");
                    if (passValidacion != null && passValidacion.equals(jugadorActivo.getPassword())) {
                        
                        // Aqui llamamos al manejador para borrar el usuario de la lista
                        manejador.eliminarUsuario(jugadorActivo); // Asumiendo que tu manejadorUsuarios tiene este método
                        
                        JOptionPane.showMessageDialog(null, "Cuenta eliminada, cerrando sesion...");
                        dispose(); // cierra el menu
                        new MenuInicial().setVisible(true); // Regresa al inicio
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    }
                }
            }
        });
        // accion para ver los ultimos juegos
        itemLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jugadorActivo.getLogsPartidas().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aun no has jugado ninguna partida", "Historial vacío", JOptionPane.INFORMATION_MESSAGE);
                } 
                else {
                    String texto="===HISTORIAL DE LAS PARTIDAS===\n\n";
                    for (String log : jugadorActivo.getLogsPartidas()) {
                        texto+=log+"\n";
                    }
                    JTextArea area = new JTextArea(texto);
                    area.setEditable(false);
                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(350, 200));
                    JOptionPane.showMessageDialog(null, scroll, "Log de Juegos", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //evento de accion para ver ranking
        itemranking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí idealmente pediríamos al manejador la lista de todos los jugadores para ordenarlos.
                //aqui mostramos los puntos del usuario actual
                JOptionPane.showMessageDialog(null, 
                    "Agente: " + jugadorActivo.getUsername() + 
                    "\nPuntos Acumulados: " + jugadorActivo.getPuntos() + 
                    "\n\n(Mas adelante conectaremos la lista completa para comparar con otros jugadores)", 
                    "Ranking Universo Marvel", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // esto es para cerrar sesion
        itemcerrarses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas cerrar sesion?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    dispose(); // cierra el menu principal
                    
                    // retorna al menú inicial
                    MenuInicial menuIni = new MenuInicial();
                    menuIni.setVisible(true);
                }
            }
        });
    }
}

