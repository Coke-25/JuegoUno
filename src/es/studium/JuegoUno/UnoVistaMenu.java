package es.studium.JuegoUno;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;

public class UnoVistaMenu extends Frame 
{
	private static final long serialVersionUID = 1L;
	//Imagen de fondo
	Image fondo;
	Toolkit herramienta;
	//Obtener tamaño de la pantalla
	Toolkit t = Toolkit.getDefaultToolkit();
	Dimension screenSize = t.getScreenSize();
	//Títulos
	Font font = new Font("Arial", Font.BOLD, 50);
	Color color = new Color(0,0,0);
	//Botones
	Button btnJugar = new Button("¡Jugar!");
	//Campos de texto para los nombres de los jugadores
	TextField txnombre1 = new TextField(15);
	TextField txnombre2 = new TextField(15);
	//Panel
	Panel panelJugadores = new Panel();
	
	public UnoVistaMenu()
	{
		setLayout(new GridBagLayout());
		setSize(screenSize);
		setTitle("UNO");
		//Establecer icono
		Image icono = Toolkit.getDefaultToolkit().getImage("cartas\\icono.png");
		setIconImage(icono);
		
		setLocationRelativeTo(null);
			//fondo
			herramienta = getToolkit();
			fondo = herramienta.getImage("cartas\\fondo.jpg");
			
			panelJugadores.setLayout(new GridLayout());
			txnombre1.setText("Jugador1");
			txnombre2.setText("Jugador2");
			panelJugadores.add(txnombre1);
			panelJugadores.add(txnombre2);
			panelJugadores.add(btnJugar);
			add(panelJugadores);
			
		setResizable(true);
		setVisible(true);
	}
	public void paint(Graphics e)
	{
		//imagen
		e.drawImage(fondo,-100,0,this);
		
			e.setFont(font);
			e.setColor(color);
			e.drawString("Poned vuestros nombres", 520, 150);
	}
}