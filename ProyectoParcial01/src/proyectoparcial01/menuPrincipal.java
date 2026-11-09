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
import java.util.ArrayList;

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
        JMenu menuStratego = new JMenu("STRATEGO - MARVEL HEROES!");
        JMenu menuPerfil = new JMenu("MI PERFIL");
        JMenu menuUniverso = new JMenu("UNIVERSO MARVEL");
        JMenu menuCerrar = new JMenu("CERRAR SESIÓN");
        JMenuItem itemPartidanueva = new JMenuItem("Partida nueva");
        menuStratego.add(itemPartidanueva);
        itemPartidanueva.setEnabled(manejador.getRanking().size() > 1);
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
                String user2 = JOptionPane.showInputDialog(null, "Ingrese el Username del Jugador 2 (Rival):");
                //si le dio a cancelar o lo dejo vacío
                if (user2 == null||user2.trim().isEmpty()) {
                    return; 
                }
                //validar que no juegue contra sí mismo
                if (user2.equals(jugadorActivo.getUsername())) {
                    JOptionPane.showMessageDialog(null, "Error: No puedes jugar contra ti mismo.", "Selección Inválida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //Verificar que el Jugador 2 exista en el sistema
                Jugador rival = manejador.buscarjuga(user2);
                if (rival == null) {
                    JOptionPane.showMessageDialog(null, "Error: El agente '" + user2 + "' no está registrado en el sistema.", "Jugador no encontrado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //aqui es de elegir el Bando
                String[] opciones = {"Héroes", "Villanos"};
                int seleccion = JOptionPane.showOptionDialog(null, 
                        jugadorActivo.getUsername().toUpperCase() + " vs " + rival.getUsername().toUpperCase() + 
                        "\n\n" + jugadorActivo.getUsername() + ", elige tu bando:", 
                        "Preparación de Combate", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, opciones, opciones[0]);
                // si cerro la ventana sin elegir
                if (seleccion == JOptionPane.CLOSED_OPTION) {
                    return; 
                }
                // Asignamos el bando elegido
                pieza.Bando bandoJ1 = (seleccion == 0) ? pieza.Bando.HEROES : pieza.Bando.VILLANOS;
                //Iniciar el tablero con toda la informacion correcta
                tableroJuego tablero = new tableroJuego(jugadorActivo, rival, bandoJ1, manejador);
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
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "Contrasena incorrecta");
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
                    // aqui va la llamda recursiva para armar el texto
                    String texto = "===HISTORIAL DE LAS PARTIDAS===\n\n" + construirLogRecursivo(jugadorActivo.getLogsPartidas(), 0);
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
               ArrayList<Jugador> ranking = manejador.getRanking();
                if(ranking.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Aun no hay jugadores registrados para el ranking.");
                    return;
                }
                // llamada recu
                String texto = "=== RANKING GLOBAL DEL UNIVERSO MARVEL ===\n\n" + construirRankingRecursivo(ranking, 0);
                // creamos un area de texto por si hay muchos jugadores registrados
                JTextArea area = new JTextArea(texto);
                area.setEditable(false);
                JScrollPane scroll = new JScrollPane(area);
                scroll.setPreferredSize(new Dimension(300, 250));
                ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/iconoran.png"));
                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                ImageIcon iconoRanking = new ImageIcon(imagenEscalada);
                JOptionPane.showMessageDialog(null, scroll, "Ranking de Jugadores",JOptionPane.PLAIN_MESSAGE,iconoRanking);
            }
        });
        //accion para ver estadisticas de batallas
        itemBatallas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Jugador> todosLosJugadores = manejador.getRanking();
                int usuariosActivos = todosLosJugadores.size();
                int usuariosHistoricos = usuariosActivos + manejador.getUsuariosEliminados();
                int victoriasHeroesGlobal = 0;
                int victoriasVillanosGlobal = 0;
                
                //rRecorremos a todos los jugadores del sistema para sumar sus victorias
                for (Jugador j : todosLosJugadores) {
                    victoriasHeroesGlobal += j.getPartidasHeroes();
                    victoriasVillanosGlobal += j.getPartidasVilanos();
                }
                int totalBatallas = victoriasHeroesGlobal + victoriasVillanosGlobal;
                String reporte = "=== ESTADÍSTICAS GLOBALES DEL UNIVERSO MARVEL ===\n\n";
                reporte += "Usuarios Activos: "+usuariosActivos+"\n";
                reporte += "Usuarios Historicos (incluye eliminados): "+usuariosHistoricos+"\n";
                reporte += "Partidas Totales Jugadas: "+totalBatallas+"\n";
                reporte += "Victorias Totales de HEROES: "+victoriasHeroesGlobal+"\n";
                reporte += "Victorias Totales de VILLANOS: "+victoriasVillanosGlobal+"\n\n";
                if(victoriasHeroesGlobal > victoriasVillanosGlobal){
                    reporte += "El bien y la justicia están triunfando en el universo!";
                } 
                else if(victoriasVillanosGlobal > victoriasHeroesGlobal) {
                    reporte += "El caos y los villanos dominan el universo actual!";
                } 
                else{
                    reporte += "El universo se encuentra en perfecto equilibrio neutral";
                }
                ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/iconobata.png"));
                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                ImageIcon iconoBatallas = new ImageIcon(imagenEscalada);
                JOptionPane.showMessageDialog(null, reporte, "Estadísticas Globales de Batalla", JOptionPane.PLAIN_MESSAGE,iconoBatallas);
            }
        });
        // esto es para cerrar sesion
        itemcerrarses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas cerrar sesion?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    dispose(); // cierra el menu principal
                    
                    MenuInicial menuIni = new MenuInicial();
                    menuIni.setVisible(true);
                }
            }
        });
    }
    // la recursiva num 2 construye el texto del ranking global (
    private String construirRankingRecursivo(ArrayList<Jugador> ranking, int index) {
        //si ya recorrimos toda la lista, retornamos ""
        if (index >= ranking.size()) {
            return "";
        }
        
        Jugador j = ranking.get(index);
        String linea = (index + 1) + " \u2014 " + j.getUsername() + " \u2014 " + j.getPuntos() + "\n";
        
        return linea + construirRankingRecursivo(ranking, index + 1);
    }

    // la recursiva 3 construye el texto del Historial de partidas 
    private String construirLogRecursivo(ArrayList<String> logs, int index) {
        if (index >= logs.size()) {
            return "";
        }
        return logs.get(index) + "\n" + construirLogRecursivo(logs, index + 1);
    }
}

