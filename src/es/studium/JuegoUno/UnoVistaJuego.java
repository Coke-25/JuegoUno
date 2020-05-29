package es.studium.JuegoUno;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.Toolkit;

public class UnoVistaJuego extends Frame 
{
	private static final long serialVersionUID = 1L;
	//Ventana para elegir el color del comodín
	Dialog dlgComodin = new Dialog(this,"Elige color",true);
		//Botones para elegir color
		Button btnRojo = new Button();
		Button btnVerde = new Button();
		Button btnAmarillo = new Button();
		Button btnAzul = new Button();
	//Ventana de clasificacion
	Dialog dlgPuntuaciones = new Dialog(this,"Top 10 jugadores",true);
		//Contenido de esta ventana
		TextArea txArea = new TextArea(20,20);
	//Barra de menú
	MenuBar barraMenu = new MenuBar();
	Menu opcionesMenu = new Menu("Opciones");
	MenuItem nuevaPartidaMenu = new MenuItem("Nueva Partida");
	MenuItem puntuacionesMenu = new MenuItem("Puntuaciones");
	MenuItem ayudaMenu = new MenuItem("Ayuda");
	//Creamos la baraja
	Baraja baraja = new Baraja();
	//Creamos a los jugadores
	Jugador jugador1 = new Jugador();
	Jugador jugador2 = new Jugador();
	
	//Imagen de fondo
	Image tablero;
	//Imagen de las cartas
	Image cartasJ1[] = new Image[54];
	Image cartasJ2[] = new Image[54];
	//Imagen de la carta del centro
	Image centro;
	//Imagen de error de rana pepe
	Image ranaError;
	Image ranaTurno;
	Image ranaWin;
	
	Toolkit herramienta;
	//Obtener tamaño de la pantalla
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension screenSize = t.getScreenSize();
	//Texto jugadores
	Font font = new Font("Arial", Font.BOLD, 20);
	Color color = new Color(255,255,255);
	
	boolean error;
	boolean finCartas;
	
	public UnoVistaJuego()
	{
		setLayout(new GridBagLayout());
		setTitle("UNO");
		setSize(screenSize);
		//Establecer icono
		Image icono = Toolkit.getDefaultToolkit().getImage("cartas\\icono.png");
		setIconImage(icono);
		//Barra menú
		setMenuBar(barraMenu);
		barraMenu.add(opcionesMenu);
		opcionesMenu.add(nuevaPartidaMenu);
		opcionesMenu.add(puntuacionesMenu);
		opcionesMenu.add(ayudaMenu);
		//fondo
		herramienta = getToolkit();
		tablero = herramienta.getImage("cartas\\tablero.jpg");
		
		
		setLocationRelativeTo(null);
		setVisible(false);
		
		//Dialogo comodín
		dlgComodin.setLayout(new GridLayout());
		dlgComodin.setSize(400,200);
		
		btnAmarillo.setBackground(Color.YELLOW);
		btnRojo.setBackground(Color.RED);
		btnAzul.setBackground(Color.BLUE);
		btnVerde.setBackground(Color.GREEN);
		dlgComodin.add(btnAmarillo);
		dlgComodin.add(btnRojo);
		dlgComodin.add(btnVerde);
		dlgComodin.add(btnAzul);
		
		dlgComodin.setResizable(false);
		dlgComodin.setLocationRelativeTo(null);
		dlgComodin.setVisible(false);
		
		//Clasificación
		dlgPuntuaciones.setSize(400, 230);
		txArea.setEditable(false);
		txArea.setText("Nombre ------ Puntuación ------ Fecha");
		dlgPuntuaciones.add(txArea);
		dlgPuntuaciones.setResizable(false);
		dlgPuntuaciones.setLocationRelativeTo(null);
		dlgPuntuaciones.setVisible(false);
	}
	
	public void paint(Graphics e)
	{
		e.drawImage(tablero,0,0,this);
		
			e.setFont(font);
			e.setColor(color);
			//Jugador1
			e.drawString(jugador1.getNombre(), 20, 80);
			e.drawString("Puntos: ", 20,110);
			e.drawString(Integer.toString(jugador1.getPuntos()),20,140);
			//Jugador2
			e.drawString(jugador2.getNombre(), 20, 860);
			e.drawString("Puntos: ",20,890);
			e.drawString(Integer.toString(jugador2.getPuntos()),20,920);
			
			//Mostrar cartas del jugador1
			if(jugador1.getNumCartas()!=0)
			{
				int posicion=130;
				for(int i=0;i<jugador1.getNumCartas();i++)
				{
					cartasJ1[i] = herramienta.getImage(jugador1.misCartas.get(i).getEnlace());

					e.drawImage(cartasJ1[i],posicion,60,this);
					posicion = posicion + 40;
				}
			}
			//Mostrar cartas del jugador2
			if(jugador2.getNumCartas()!=0)
			{
				int posicion=130;
				for(int i=0;i<jugador2.getNumCartas();i++)
				{
					cartasJ2[i] = herramienta.getImage(jugador2.misCartas.get(i).getEnlace());

					e.drawImage(cartasJ2[i],posicion,840,this);
					posicion = posicion + 40;
				}
			}
			
			//Centro
			if(baraja.getCartaCentro()!=null)
			{
				centro = herramienta.getImage(baraja.getCartaCentro().getEnlace());
				e.drawImage(centro,750,400,this);
			}
			
			if(error==true)
			{
				ranaError = herramienta.getImage("cartas\\pepecry.png");
				e.drawImage(ranaError,0,210,this);
			}
			
			if(jugador1.getTurno()==true)
			{
				ranaTurno = herramienta.getImage("cartas\\pepeouu.png");
				e.drawImage(ranaTurno,0,170,this);
			}
			else
			{
				ranaTurno = herramienta.getImage("cartas\\pepeouu.png");
				e.drawImage(ranaTurno,0,930,this);
			}
			
			//Cuando se gana la partida
			if(jugador1.getNumCartas()==0)
			{
				e.drawString("¡¡¡"+jugador1.getNombre()+" ha ganado!!!", 730, 750);
				ranaWin = herramienta.getImage("cartas\\ranapepeWinner.gif");
				e.drawImage(ranaWin,600,250,this);
			}
			if(jugador2.getNumCartas()==0)
			{
				e.drawString("¡¡¡"+jugador2.getNombre()+" ha ganado!!!", 730, 750);
				ranaWin = herramienta.getImage("cartas\\ranapepeWinner.gif");
				e.drawImage(ranaWin,600,250,this);
			}
			if(baraja.getFinCartas()==true)
			{
				if(jugador1.getPuntos()<jugador2.getPuntos())
				{
					e.drawString("Se acabaron las cartas ¡¡¡"+jugador2.getNombre()+" ha ganado por puntos!!!", 550, 760);
				}
				else
				{
					e.drawString("Se acabaron las cartas ¡¡¡"+jugador1.getNombre()+" ha ganado por puntos!!!", 550, 760);
				}
				
				ranaWin = herramienta.getImage("cartas\\ranapepeWinner.gif");
				e.drawImage(ranaWin,600,250,this);
			}
	}
	
	public void setError(boolean e)
	{
		error = e;
	}
}