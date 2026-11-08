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
import java.util.Collections;
public class tableroJuego extends JFrame{
    private piezaNormal[][] matrizLogica; 
    private int filaOrigen = -1;
    private int colOrigen = -1;
    private JButton casillas[][];
    private Jugador jugadorActivo;
    private int cell = 65;
    private Image fondoImg;
    private JPanel tablero;
    private JPanel cementerioHeroes;
    private JPanel cementerioVillanos;
    private manejadorUsuarios manejador;
    private Jugador jugador2;
    private pieza.Bando bandoJugador1;
    private pieza.Bando bandoJugador2;
    private final java.util.HashMap<String, Image> cacheImagenes = new java.util.HashMap<>();
    private pieza.Bando turnoActual = pieza.Bando.HEROES;
    private ArrayList<String> bitacoraEventos = new ArrayList<>();
    public tableroJuego(Jugador j1, Jugador j2, pieza.Bando bandoJ1, manejadorUsuarios m){
        this.manejador = m;
        this.jugadorActivo = j1;
        this.jugador2 = j2;
        this.bandoJugador1 = bandoJ1;
        // El bando del jugador 2 es el opuesto automáticamente
        this.bandoJugador2 = (bandoJ1 == pieza.Bando.HEROES) ? pieza.Bando.VILLANOS : pieza.Bando.HEROES;
        setTitle("Marvel Stratego - Tablero de batalla");
        Image iconoVentana = new ImageIcon(getClass().getResource("/imagenes/strategoicon.jpg")).getImage();
        setIconImage(iconoVentana);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      
        fondoImg = new ImageIcon(getClass().getResource("/imagenes/fondoTablero.jpg")).getImage();

        Rectangle pantalla = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        final int lado = ((Math.min(pantalla.width, pantalla.height) - 60) / 10) * 10;
        cell = lado / 10;

        tablero = new JPanel(new GridLayout(10, 10, 0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondoImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel contenedor = new JPanel(null) {
            @Override
            public Dimension getPreferredSize() { return new Dimension(lado, lado); }
            @Override
            public void doLayout() {
                int l = Math.min(getWidth(), getHeight());
                tablero.setBounds((getWidth() - l) / 2, (getHeight() - l) / 2, l, l);
            }
        };
        contenedor.setBackground(Color.BLACK);
        contenedor.add(tablero);
        setLayout(new BorderLayout());
        //configuramos el panel izquierdo de los heroes caidos
        cementerioHeroes=new JPanel(new FlowLayout(FlowLayout.LEFT));
        cementerioHeroes.setPreferredSize(new Dimension(130, 0));
        cementerioHeroes.setBackground(new Color(30, 30, 30));//ponemos un gris bonito
        //configuramos el panel derecho de los villanos que derrotaron
        cementerioVillanos=new JPanel(new FlowLayout(FlowLayout.LEFT));
        cementerioVillanos.setPreferredSize(new Dimension(130, 0));
        cementerioVillanos.setBackground(new Color(30, 30, 30));
        add(cementerioHeroes,BorderLayout.WEST);
        add(cementerioVillanos,BorderLayout.EAST);
        add(contenedor,BorderLayout.CENTER); //el tablero queda protegido

        tablero.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int nuevo = tablero.getWidth() / 10;
                if (nuevo > 0 && nuevo != cell) {
                    cell = nuevo;
                    refrescarTodo();
                }
            }
        });
        casillas = new JButton[10][10];
        matrizLogica = new piezaNormal[10][10];
        for(int fila = 0;fila<10;fila++){
            for(int col=0;col<10;col++){
                casillas[fila][col] = new JButton();
                casillas[fila][col].setOpaque(false);
                casillas[fila][col].setContentAreaFilled(false); 
                casillas[fila][col].setMargin(new Insets(0, 0, 0, 0));
                casillas[fila][col].setFocusPainted(false);
                
                casillas[fila][col].setBorder(null); 
                if((fila==4||fila==5)&&(col==2||col==3||col==6||col==7)){
                    
                    casillas[fila][col].setEnabled(false);
                }
                else{
                    final int f = fila;
                    final int c = col;
                    casillas[fila][col].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(filaOrigen == -1) {
                                if (matrizLogica[f][c]!=null) {
                                    //aqui validamos que no toque piezas del enemigo
                                    if (matrizLogica[f][c].getBando()!=turnoActual) {
                                        JOptionPane.showMessageDialog(null, "no es tu turno! juegan los "+turnoActual);
                                        return; // Cancela el clic
                                    }
                                    // regla la tierra 0 y las bombas 1 no se mueven
                                    try {
                                        if (matrizLogica[f][c].getRango() == 0 || matrizLogica[f][c].getRango() == 11) {
                                            throw new MovimientoInvalidoException("Las bombas y la TIERRA son piezas estáticas y no se pueden mover.");
                                        }
                                        else {
                                            filaOrigen=f;
                                            colOrigen=c;
                                            casillas[f][c].setBackground(Color.YELLOW); 
                                        }
                                    } catch (MovimientoInvalidoException ex) {
                                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Movimiento Prohibido", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } 
 
                            else{
                                //si toca otra pieza de su bando cambiamos la seleccion
                                if (matrizLogica[f][c] != null && matrizLogica[f][c].getBando() == matrizLogica[filaOrigen][colOrigen].getBando()) {
                                    casillas[filaOrigen][colOrigen].setBackground(Color.LIGHT_GRAY); 
                                    filaOrigen = f;
                                    colOrigen = c;
                                    casillas[f][c].setBackground(Color.YELLOW); 
                                }
                                //si es una casilla vacia o del enemigo intentamos mover
                                else{
                                    
                                    int difFila=Math.abs(filaOrigen-f);
                                    int difCol=Math.abs(colOrigen-c);
                                    
                                    boolean movimientoValido = false;
                                    
                                    //validacion de movimiento normal de 1 casilla
                                    if((difFila + difCol) == 1 && casillas[f][c].isEnabled()){
                                        movimientoValido = true;
                                    }
                                    // validacion de movimiento largo exclusivo del explorador de rango 2
                                    else if(matrizLogica[filaOrigen][colOrigen].getRango() == 2 && casillas[f][c].isEnabled()){
                                        if (filaOrigen==f|| colOrigen== c) { 
                                            movimientoValido = true;
                                            if (filaOrigen == f) { 
                                                int paso = (c > colOrigen) ? 1 : -1;
                                                for (int i = colOrigen + paso; i != c; i += paso) {
                                                    if (matrizLogica[f][i] != null || !casillas[f][i].isEnabled()) {
                                                        movimientoValido = false; break;
                                                    }
                                                }
                                            } 
                                            else { 
                                                int paso=(f>filaOrigen) ? 1 : -1;
                                                for (int i = filaOrigen + paso; i != f; i += paso) {
                                                    if (matrizLogica[i][c] != null || !casillas[i][c].isEnabled()) {
                                                        movimientoValido = false; break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                    if(movimientoValido){
                                        
                                        if(matrizLogica[f][c]==null){
                                            casillas[f][c].setIcon(casillas[filaOrigen][colOrigen].getIcon());
                                            matrizLogica[f][c] = matrizLogica[filaOrigen][colOrigen];
                                        }
                                        else{
                                            piezaNormal atacante = matrizLogica[filaOrigen][colOrigen];
                                            piezaNormal defensor = matrizLogica[f][c];
                                            atacable.resulCombate resultado = defensor.defender(atacante);
                                            if(resultado==atacable.resulCombate.GANA_ATACANTE){
                                                if (defensor.getRango() == 0) {
                                                    bitacoraEventos.add(atacante.getNombre()+" ("+turnoActual+") CAPTURO LA TIERRA Y GANO LA PARTIDA!");
                                                    //esto es de la suma puntos y los log
                                                    jugadorActivo.sumarPuntos(3);
                                                    if (turnoActual==pieza.Bando.HEROES) {
                                                        jugadorActivo.sumarpartiHero();
                                                    }
                                                    else {
                                                        jugadorActivo.sumarpartiVilla();
                                                    }
                                                    jugadorActivo.agregarLog("VICTORIA: Capturaste la Tierra con el bando " + turnoActual + " (+3 puntos)");
                                                
                                                    //guardamos los cambios inmediatamente en el archivo
                                                    manejador.guardarUsuarios();
                                                    JOptionPane.showMessageDialog(null, "HAS CAPTURADO LA TIERRA!\n¡LOS " + turnoActual + " HAN GANADO LA PARTIDA!");
                                                    dispose(); 
                                                    return; 
                                                } else {
                                                    bitacoraEventos.add(turnoActual + " ataco con " + atacante.getNombre() + " a " + defensor.getNombre() + " -> ¡GANO " + turnoActual + "!");
                                                    JOptionPane.showMessageDialog(null,"Ganaste el combate!");
                                                }
                                                registrarBaja(defensor);
                                                casillas[f][c].setIcon(casillas[filaOrigen][colOrigen].getIcon());
                                                matrizLogica[f][c] = atacante;
                                            } 
                                            else if(resultado==atacable.resulCombate.GANA_DEFENSOR){
                                                bitacoraEventos.add(turnoActual + " ataco con " + atacante.getNombre() + " a " + defensor.getNombre() + " -> ¡PERDIO " + turnoActual + "!");
                                                JOptionPane.showMessageDialog(null,"Tu pieza fue destruida!");
                                                registrarBaja(atacante);
                                            }
                                            else{
                                                bitacoraEventos.add(turnoActual + " ataco con " + atacante.getNombre() + " a " + defensor.getNombre() + " -> EMPATE (Ambos mueren)!");
                                                JOptionPane.showMessageDialog(null,"Empate! Ambas piezas se destruyen");
                                                registrarBaja(atacante); 
                                                registrarBaja(defensor);
                                                casillas[f][c].setIcon(null);
                                                matrizLogica[f][c] = null;
                                            }
                                        }
                                        //sin importar quien gane el que ataca abandona su casilla original
                                        casillas[filaOrigen][colOrigen].setIcon(null);
                                        matrizLogica[filaOrigen][colOrigen] = null;
                                        
                                    } 
                                    else{
                                        JOptionPane.showMessageDialog(null, "Movimiento invalido Solo 1 casilla arriba, abajo, izquierda o derecha.");
                                    }
                                    turnoActual=(turnoActual==pieza.Bando.HEROES)?pieza.Bando.VILLANOS:pieza.Bando.HEROES;
                                    JOptionPane.showMessageDialog(null, "Cambio de bando, ahora juegan: "+turnoActual);
                                    refrescarTodo();
                                    if (!tieneMovimientosValidos(turnoActual)) {
                                        declararGanadorPorBloqueo();
                                        return; // Corta la ejecución para que no limpie la selección
                                    }
                                    // limpiamos todo para el siguiente turno
                                    casillas[filaOrigen][colOrigen].setBackground(Color.LIGHT_GRAY);
                                    filaOrigen=-1;
                                    colOrigen=-1;
                                }
                            }
                        }
                    });
                }
                tablero.add(casillas[fila][col]);
        }   }
        ArrayList<piezaNormal>listaHeroes=generarFichasal(pieza.Bando.HEROES);
        ArrayList<piezaNormal>listaVillanos=generarFichasal(pieza.Bando.VILLANOS);
        colocarFichasrecu(listaHeroes, 0, 0, 0); 
        colocarFichasrecu(listaVillanos, 0, 6, 0);
        pack();
        setLocationRelativeTo(null);
        refrescarTodo();
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones de Juego");
        
        JMenuItem itemGuardar = new JMenuItem("Guardar Partida");
        JMenuItem itemCargar = new JMenuItem("Cargar Partida");
        JMenuItem itemSalir = new JMenuItem("Rendirse / Salir del Tablero");
        
        itemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPartida();
            }
        });
        itemCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPartida();
            }
        });
        JMenuItem itemBitacora = new JMenuItem("Ver bitacora de batalla");
        
        itemBitacora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bitacoraEventos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aun no ha habido combates en esta partida");
                    return;
                }
                //unimos todos los textos con saltos de línea
                String historial = "==== HISTORIAL DE BATALLAS ====\n\n";
                for (String evento : bitacoraEventos) {
                    historial += evento + "\n";
                }
                //creamos un cuadro de texto con scroll por si la partida es muy larga
                JTextArea areaTexto = new JTextArea(historial);
                areaTexto.setEditable(false);
                JScrollPane scroll = new JScrollPane(areaTexto);
                scroll.setPreferredSize(new Dimension(450, 300));
                
                JOptionPane.showMessageDialog(null, scroll, "Bitacora", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuOpciones.add(itemBitacora);
        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int confirmacion = JOptionPane.showConfirmDialog(null, 
                        "Estas seguro que deseas rendirte?\nEsto le dara 3 puntos a tu oponente", 
                        "Confirmar Retiro", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(confirmacion == JOptionPane.YES_OPTION){
                    // Determinar quien se rinde (el del turno actual) y quien gana
                    Jugador elQueSeRinde;
                    Jugador elGanador;
                    String bandoRinde;
                    String bandoGana;
                    if(turnoActual == bandoJugador1){
                        elQueSeRinde = jugadorActivo;
                        elGanador = jugador2;
                        bandoRinde = "HEROES";
                        bandoGana = "VILLANOS";
                    }
                    else{
                        elQueSeRinde = jugador2;
                        elGanador = jugadorActivo;
                        bandoRinde = "VILLANOS";
                        bandoGana = "HÉROES";
                    }
                    // darle los 3 puntos al rival si se sale el jugador 
                    elGanador.sumarPuntos(3);
                    if (bandoGana.equals("HEROES")) {
                        elGanador.sumarpartiHero();
                    }
                    else{
                        elGanador.sumarpartiVilla();
                    }
                    String eventoLog = elGanador.getUsername() + " usando " + bandoGana + 
                                       " ha ganado ya que " + elQueSeRinde.getUsername() + 
                                       " usando " + bandoRinde + " se ha retirado del juego";
                    jugadorActivo.agregarLog(eventoLog);
                    jugador2.agregarLog(eventoLog);
                    manejador.guardarUsuarios(); //aqui guardamos el nuevo puntaje
                    JOptionPane.showMessageDialog(null, elGanador.getUsername() + " gana por retiro!");
                    dispose(); // hace que se cierre el tablero y nos regrese al menu
                }
            }
        });
        menuOpciones.add(itemGuardar);
        menuOpciones.add(itemCargar);
        menuOpciones.addSeparator();
        menuOpciones.add(itemSalir);
        barraMenu.add(menuOpciones);
        setJMenuBar(barraMenu);
    }
    private ArrayList<piezaNormal> generarFichasal(pieza.Bando bando){
        String p = (bando == pieza.Bando.HEROES) ? "h" : "v";
        piezaNormal[][] gridTemporal = new piezaNormal[4][10];
        java.util.Random rnd = new java.util.Random();
        // Mapeo de las 4 filas de como se forman que va dependiendo del bando
        int filaFondo = (bando == pieza.Bando.HEROES) ? 3 : 0; // Fila 9 para H, Fila 0 para V
        int filaMediaAtras = (bando == pieza.Bando.HEROES) ? 2 : 1; // Fila 8 para H, Fila 1 para V
        int filaMediaAdelante = (bando == pieza.Bando.HEROES) ? 1 : 2; // Fila 7 para H, Fila 2 para V
        int filaFrente = (bando == pieza.Bando.HEROES) ? 0 : 3; // Fila 6 para H, Fila 3 para V
        //aqui separamos las piezas por categorias diferentes
        piezaNormal tierra = new piezaNormal(0, bando, p + "0_1");
        ArrayList<piezaNormal> bombas = new ArrayList<>();
        for (int i = 1; i <= 6; i++){
            bombas.add(new piezaNormal(11, bando, p + "11_" + i));
        }
        ArrayList<piezaNormal> exploradores = new ArrayList<>(); // Rango 2
        for (int i = 1; i <= 8; i++){
            exploradores.add(new piezaNormal(2, bando, p + "2_" + i));
        }
        ArrayList<piezaNormal> resto = new ArrayList<>();
        resto.add(new piezaNormal(10,bando,p+"10_1"));
        resto.add(new piezaNormal(9,bando,p+"9_1"));
        for (int i = 1; i <= 2; i++){
            resto.add(new piezaNormal(8,bando,p+"8_"+i));
        }
        for (int i = 1; i <= 3; i++){
            resto.add(new piezaNormal(7,bando,p+"7_"+i));
        }
        for (int i = 1; i <= 4; i++){
            resto.add(new piezaNormal(6,bando,p+"6_"+i));
        }
        for (int i = 1; i <= 4; i++){
            resto.add(new piezaNormal(5,bando,p+"5_"+i));
        }
        for (int i = 1; i <= 4; i++){
            resto.add(new piezaNormal(4,bando,p+"4_"+i));
        }
        for (int i = 1; i <= 5; i++){
            resto.add(new piezaNormal(3,bando,p+"3_"+i));
        }
        resto.add(new piezaNormal(1,bando,p+"1_1"));
        //Tierra en la última fila (sin tocar esquinas 0 y 9)
        int colTierra = 1 + rnd.nextInt(8); 
        gridTemporal[filaFondo][colTierra] = tierra;
        // Bombas protegiendo la Tierra
        gridTemporal[filaFondo][colTierra - 1] = bombas.remove(0); // Izquierda
        gridTemporal[filaFondo][colTierra + 1] = bombas.remove(0); // Derecha
        gridTemporal[filaMediaAtras][colTierra] = bombas.remove(0); // En frente
        //Bombas restantes aleatoriamente en las 2 filas del fondo
        while (!bombas.isEmpty()) {
            int f = rnd.nextBoolean() ? filaFondo : filaMediaAtras;
            int c = rnd.nextInt(10);
            if (gridTemporal[f][c] == null) {
                gridTemporal[f][c] = bombas.remove(0);
            }
        }
        // Rango 2 en las dos filas delanteras
        while (!exploradores.isEmpty()) {
            int f = rnd.nextBoolean() ? filaMediaAdelante : filaFrente;
            int c = rnd.nextInt(10);
            if (gridTemporal[f][c] == null) {
                gridTemporal[f][c] = exploradores.remove(0);
            }
        }
        //  rellenamos aleatoriamente los espacios vacios
        Collections.shuffle(resto);
        ArrayList<Point> celdasVacias = new ArrayList<>();
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 10; c++) {
                if (gridTemporal[f][c] == null) celdasVacias.add(new Point(f, c));
            }
        }
        Collections.shuffle(celdasVacias);
        for (int i = 0; i < resto.size(); i++) {
            Point pto = celdasVacias.get(i);
            gridTemporal[pto.x][pto.y] = resto.get(i);
        }
        //convertir el bloque 4x10 a una lista lineal ordenada para la funcion recursiva
        ArrayList<piezaNormal> listaFinal = new ArrayList<>();
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 10; c++) {
                listaFinal.add(gridTemporal[f][c]);
            }
        }
        return listaFinal;
    }
    private ImageIcon obtenerImagenFicha(String nombreArchivo) {
        String ruta = "/imagenes/" + nombreArchivo + ".png";
        java.net.URL url = getClass().getResource(ruta);
        if (url != null){
            ImageIcon iconoOriginal=new ImageIcon(url);
            Image imagenEscalada=iconoOriginal.getImage().getScaledInstance(cell-4,cell-4,Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        }
        else{
            System.err.println("Falta la imagen: " + ruta);
            return null; 
        }
    }
    private ImageIcon obtenerImagenReverso(pieza.Bando bando) {
        String ruta = "";
        if(bando==pieza.Bando.HEROES){
            ruta = "/imagenes/reves1.jpg";
        }
        else {
            ruta = "/imagenes/reves2.jpg";
        }
        java.net.URL url = getClass().getResource(ruta);
        if (url != null) {
            ImageIcon iconoOriginal = new ImageIcon(url);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(cell - 4, cell - 4, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        }
        return null;
    }
    //una de las funciones recursivas que son solicitadas
    private void colocarFichasrecu(ArrayList<piezaNormal>fichas,int indice,int fila,int col){
        if(indice>=fichas.size()){
            return;
        }
        if(col>=10){
            colocarFichasrecu(fichas,indice,fila+1,0);
            return;
        }
        piezaNormal piezaActual=fichas.get(indice);
        matrizLogica[fila][col]=piezaActual;
        casillas[fila][col].setText("");
        casillas[fila][col].setIcon(obtenerImagenFicha(piezaActual.getNombre()));
        colocarFichasrecu(fichas,indice+1,fila,col+1);
    }
    private void refrescarTodo() {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                if (matrizLogica[f][c]!=null) {
                    //aqui la condicion de que si es del jugador del turno mostrar, de lo contrario no
                    if (matrizLogica[f][c].getBando() == turnoActual) {
                        casillas[f][c].setIcon(obtenerImagenFicha(matrizLogica[f][c].getNombre()));
                    } 
                    else {
                        //Le enviamos el bando de esta ficha oculta para que ponga su reverso correspondiente
                        casillas[f][c].setIcon(obtenerImagenReverso(matrizLogica[f][c].getBando()));
                    }
                }
                else{
                    casillas[f][c].setIcon(null); // aseguramos que las vacias no tengan imagen
                }
            }
        }
    }
    private void registrarBaja(piezaNormal piezaEliminada){
        String ruta = "/imagenes/"+piezaEliminada.getNombre()+".png";
        java.net.URL url=getClass().getResource(ruta);
        
        if (url!=null) {
            ImageIcon iconoOriginal=new ImageIcon(url);
            Image img=iconoOriginal.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            JLabel etiquetaBaja=new JLabel(new ImageIcon(img));
            if (piezaEliminada.getBando()==pieza.Bando.HEROES) {
                cementerioHeroes.add(etiquetaBaja);
                cementerioHeroes.revalidate();
                cementerioHeroes.repaint();
            }
            else {
                cementerioVillanos.add(etiquetaBaja);
                cementerioVillanos.revalidate();
                cementerioVillanos.repaint();
            }
        }
    }
    //metodo para guardar partida
    private void guardarPartida() {
        try {
            java.io.FileOutputStream archivoSalida = new java.io.FileOutputStream("partida_marvel.stratego");
            java.io.ObjectOutputStream escritorObjetos = new java.io.ObjectOutputStream(archivoSalida);
            //guardamos la matriz principal
            escritorObjetos.writeObject(matrizLogica);
            //guardamos de quien es el turno
            escritorObjetos.writeObject(turnoActual);
            escritorObjetos.writeObject(bitacoraEventos);
            escritorObjetos.close();
            archivoSalida.close();
            JOptionPane.showMessageDialog(this, "Partida guardada exitosamente!");
            
        } catch (Exception ex){
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }
    //metodo para cargar la partida
    private void cargarPartida(){
        try {
            java.io.FileInputStream archivoEntrada = new java.io.FileInputStream("partida_marvel.stratego");
            java.io.ObjectInputStream lectorObjetos = new java.io.ObjectInputStream(archivoEntrada);
            //recuperamos los objetos exactos
            matrizLogica = (piezaNormal[][]) lectorObjetos.readObject();
            turnoActual = (pieza.Bando) lectorObjetos.readObject();
            bitacoraEventos = (ArrayList<String>) lectorObjetos.readObject();
            lectorObjetos.close();
            archivoEntrada.close();
            //actualizamos la interfaz grafica para que muestre el estado cargado
            refrescarTodo(); 
            JOptionPane.showMessageDialog(this, "partida cargada con exito! Juegan los " + turnoActual);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "no hay partida guardada o ocurrio un error al cargar");
        }
    }
    // este metodo es el que revisa si el bando actual tiene algun movimiento legal
    private boolean tieneMovimientosValidos(pieza.Bando bando) {
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                piezaNormal p = matrizLogica[f][c];
                
                // Si encontramos una pieza del bando actual
                if (p != null && p.getBando() == bando) {
                    // Si es Tierra (0) o Bomba (11), no evaluamos porque no se mueven
                    if (p.getRango() == 0 || p.getRango() == 11) {
                        continue;
                    }
                    
                    //verificamos las 4 direcciones
                    int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                    for (int[] dir : direcciones) {
                        int nuevaFila = f + dir[0];
                        int nuevaCol = c + dir[1];
                        
                        // Si esta dentro de los límites del tablero
                        if (nuevaFila >= 0 && nuevaFila < 10 && nuevaCol >= 0 && nuevaCol < 10) {
                            // Si no es la zona prohibida de en medio
                            if (casillas[nuevaFila][nuevaCol].isEnabled()) {
                                // Si la casilla esta vacía o tiene un enemigo, ES un movimiento válido
                                if (matrizLogica[nuevaFila][nuevaCol] == null || matrizLogica[nuevaFila][nuevaCol].getBando() != bando) {
                                    return true; // Encontramos al menos 1 movimiento, así que aún puede jugar
                                }
                            }
                        }
                    }
                }
            }
        }
        return false; //esto reviso todo el tablero y no encontro ni un solo movimiento
    }

    //este es el que finaliza el juego si alguien se queda sin movimientos
    private void declararGanadorPorBloqueo() {
        Jugador perdedor;
        Jugador ganador;
        String bandoPerdedor;
        String bandoGanador;

        if (turnoActual == bandoJugador1) {
            perdedor = jugadorActivo;
            ganador = jugador2;
            bandoPerdedor = (bandoJugador1 == pieza.Bando.HEROES) ? "HÉROES" : "VILLANOS";
            bandoGanador = (bandoPerdedor.equals("HÉROES")) ? "VILLANOS" : "HÉROES";
        } else {
            perdedor = jugador2;
            ganador = jugadorActivo;
            bandoPerdedor = (bandoJugador2 == pieza.Bando.HEROES) ? "HÉROES" : "VILLANOS";
            bandoGanador = (bandoPerdedor.equals("HÉROES")) ? "VILLANOS" : "HÉROES";
        }

        // Sumar puntos y estadísticas al ganador
        ganador.sumarPuntos(3);
        if (bandoGanador.equals("HEROES")) {
            ganador.sumarpartiHero();
        } else {
            ganador.sumarpartiVilla();
        }
        String log = perdedor.getUsername() + " usando " + bandoPerdedor + 
                     " ha perdido por no tener movimientos validos disponibles ante " + 
                     ganador.getUsername() + " (FECHA: " + java.time.LocalDate.now() + ")";
        
        jugadorActivo.agregarLog(log);
        jugador2.agregarLog(log);
        manejador.guardarUsuarios();
        JOptionPane.showMessageDialog(null,perdedor.getUsername() + " se ha quedado sin piezas moviles o esta completamente bloqueado!\n\n" 
                                            + ganador.getUsername() + " GANA LA PARTIDA por regla tecnica y obtiene +3 puntos!");
        dispose(); 
    }
}
