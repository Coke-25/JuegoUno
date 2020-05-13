package es.studium.JuegoUno;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
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
	//Texto jugadores
	Font font2 = new Font("Arial", Font.BOLD, 20);
	Color color2 = new Color(0,0,0);
	//Botones
	Button boton2 = new Button("     2     ");
	Button boton3 = new Button("     3     ");
	Button boton4 = new Button("     4     ");
	Button btnJugar = new Button("¡Jugar!");
	//Campos de texto para los nombres de los jugadores
	TextField txnombre1 = new TextField(15);
	TextField txnombre2 = new TextField(15);
	TextField txnombre3 = new TextField(15);
	TextField txnombre4 = new TextField(15);
	//Paneles
	Panel panelBotones = new Panel();
	Panel panelJugadores = new Panel();
	//Número de jugadores
	int numeroJugadores;
	boolean juego;
	
	Label lb = new Label("Hola");
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	UnoMain main = new UnoMain();
	
	public UnoVistaMenu(int numero,boolean j)
	{
		numeroJugadores = numero;
		setLayout(new GridBagLayout());
		setSize(screenSize);
		setTitle("UNO");
		setLocationRelativeTo(null);
		if(j==false)
		{
			//fondo
			herramienta = getToolkit();
			fondo = herramienta.getImage("cartas\\fondo.jpg");
			//panel para introducir los nombres de los jugadores según su número
			if(numeroJugadores==2)
			{
				panelJugadores.setLayout(new GridLayout());
				txnombre1.setText("Jugador1");
				txnombre2.setText("Jugador2");
				panelJugadores.add(txnombre1);
				panelJugadores.add(txnombre2);
				panelJugadores.add(btnJugar);
				add(panelJugadores);
			}
			else if(numeroJugadores==3)
			{
				panelJugadores.setLayout(new GridLayout());
				txnombre1.setText("Jugador1");
				txnombre2.setText("Jugador2");
				txnombre3.setText("Jugador3");
				panelJugadores.add(txnombre1);
				panelJugadores.add(txnombre2);
				panelJugadores.add(txnombre3);
				panelJugadores.add(btnJugar);
				add(panelJugadores);
			}
			else if(numeroJugadores==4)
			{
				panelJugadores.setLayout(new GridLayout());
				txnombre1.setText("Jugador1");
				txnombre2.setText("Jugador2");
				txnombre3.setText("Jugador3");
				txnombre4.setText("Jugador4");
				panelJugadores.add(txnombre1);
				panelJugadores.add(txnombre2);
				panelJugadores.add(txnombre3);
				panelJugadores.add(txnombre4);
				panelJugadores.add(btnJugar);
				add(panelJugadores);
			}
			//Panel para elegir número de jugadores
			else
			{
				panelBotones.setLayout(new GridLayout());
				panelBotones.add(boton2);
				panelBotones.add(boton3);
				panelBotones.add(boton4);
				add(panelBotones);
			}
		}
		else
		{
			add(lb);
		}
		setResizable(true);
		setVisible(true);
	}
	public void setJugadores(int numero)
	{
		numeroJugadores = numero;
		setVisible(false);
		UnoVistaMenu vista = new UnoVistaMenu(numero,false);
		UnoModelo modelo = new UnoModelo();
		
		new UnoControlador(modelo, vista);
	}
	public void paint(Graphics e)
	{
		//imagen
		e.drawImage(fondo,-100,0,this);
		if(numeroJugadores==0)
		{
			e.setFont(font);
			e.setColor(color);
			e.drawString("¿Cuantos jugadores sois?", 500, 150);
		}
		else if ((numeroJugadores)==2)
		{
			e.setFont(font);
			e.setColor(color);
			e.drawString("Poned vuestros nombres", 520, 150);
		}
		else if ((numeroJugadores)==3)
		{
			e.setFont(font);
			e.setColor(color);
			e.drawString("Poned vuestros nombres", 520, 150);
		}
		else if ((numeroJugadores)==4)
		{
			e.setFont(font);
			e.setColor(color);
			e.drawString("Poned vuestros nombres", 520, 150);
		}
	}
}