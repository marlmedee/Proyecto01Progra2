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
    private final java.util.HashMap<String, Image> cacheImagenes = new java.util.HashMap<>();
    private pieza.Bando turnoActual = pieza.Bando.HEROES;
    public tableroJuego(Jugador j){
        this.jugadorActivo = j;
        setTitle("Marvel Stratego - Tablero de batalla");
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
                                    if (matrizLogica[f][c].getRango()==0||matrizLogica[f][c].getRango()==11) {
                                        JOptionPane.showMessageDialog(null, "las bombas y la tierra no se pueden mover");
                                    }
                                    else{
                                        filaOrigen=f;
                                        colOrigen=c;
                                        casillas[f][c].setBackground(Color.YELLOW); 
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
                                //Si es una casilla vacía o del enemigo intentamos mover
                                else{
                                    
                                    int difFila=Math.abs(filaOrigen-f);
                                    int difCol=Math.abs(colOrigen-c);
                                    
                                    if((difFila + difCol)==1 && casillas[f][c].isEnabled()){
                                        
                                        if(matrizLogica[f][c]==null){
                                            casillas[f][c].setIcon(casillas[filaOrigen][colOrigen].getIcon());
                                            matrizLogica[f][c] = matrizLogica[filaOrigen][colOrigen];
                                        }
                                        else{
                                            piezaNormal atacante = matrizLogica[filaOrigen][colOrigen];
                                            piezaNormal defensor = matrizLogica[f][c];
                                            atacable.resulCombate resultado = defensor.defender(atacante);
                                            if(resultado==atacable.resulCombate.GANA_ATACANTE){
                                                JOptionPane.showMessageDialog(null,"Ganaste el combate!");
                                                registrarBaja(defensor);
                                                casillas[f][c].setIcon(casillas[filaOrigen][colOrigen].getIcon());
                                                matrizLogica[f][c] = atacante;
                                                
                                            } 
                                            else if(resultado==atacable.resulCombate.GANA_DEFENSOR){
                                                JOptionPane.showMessageDialog(null,"Tu pieza fue destruida!");
                                                registrarBaja(atacante);
                                            }
                                            else{
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
                                        JOptionPane.showMessageDialog(null, "Movimiento inválido. Solo 1 casilla arriba, abajo, izquierda o derecha.");
                                    }
                                    turnoActual=(turnoActual==pieza.Bando.HEROES)?pieza.Bando.VILLANOS:pieza.Bando.HEROES;
                                    JOptionPane.showMessageDialog(null, "Cambio de bando, Ahora juegan: "+turnoActual);
                                    refrescarTodo();
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
    }
    private ArrayList<piezaNormal> generarFichasal(pieza.Bando bando){
        ArrayList<piezaNormal>lista = new ArrayList<>();
        String p=(bando==pieza.Bando.HEROES)? "h":"v";
        lista.add(new piezaNormal(0,bando,p+"0_1")); 
        lista.add(new piezaNormal(10,bando,p+"10_1"));
        lista.add(new piezaNormal(9,bando,p+"9_1"));
        for(int i = 1; i <= 2; i++){
           lista.add(new piezaNormal(8,bando,p+"8_"+i));
        }
        for (int i = 1; i <= 3; i++){
            lista.add(new piezaNormal(7,bando,p+"7_"+i));
        }
        for (int i = 1; i <= 4; i++){
            lista.add(new piezaNormal(6,bando,p+"6_"+i));
        }
        for (int i = 1; i <= 4; i++){
            lista.add(new piezaNormal(5,bando,p+"5_" +i));
        }
        for (int i = 1; i <= 4; i++){
            lista.add(new piezaNormal(4,bando,p+"4_" +i));
        }
        for (int i = 1; i <= 5; i++){
            lista.add(new piezaNormal(3,bando,p+"3_"+i));
        }
        for (int i = 1; i <= 8; i++){
            lista.add(new piezaNormal(2,bando,p+"2_"+i));
        }
        lista.add(new piezaNormal(1,bando,p+"1_1"));
        for (int i = 1; i <= 6; i++){
           lista.add(new piezaNormal(11,bando,p+"11_"+i));
        }
        Collections.shuffle(lista);
        return lista;
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
                        // ¡NUEVO! Le enviamos el bando de esta ficha oculta para que ponga su reverso correspondiente
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
}
