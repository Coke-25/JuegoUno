package es.studium.JuegoUno;

import java.util.ArrayList;

public class Jugador 
{
	private String nombre;
	private int puntos;
	private boolean turno;
	private boolean diUno;
	public ArrayList<Carta> misCartas;
	private int numCartas;
	
	public Jugador()
	{
		nombre="";
		puntos=1000;
		turno=false;
		diUno=false;
		misCartas = new ArrayList<Carta>();
		numCartas = 0;
	}
	
	public Jugador(String nmbr, int pt, boolean t, boolean dU, int nC)
	{
		nombre=nmbr;
		puntos=pt;
		turno=t;
		diUno=dU;
		misCartas = new ArrayList<Carta>();
		numCartas = nC;
	}
	
	public String getNombre ()
	{
		return nombre;
	}
	public void setNombre (String nmbr)
	{
		nombre = nmbr;
	}
	
	public int getPuntos ()
	{
		return puntos;
	}
	public void setPuntos (int pt)
	{
		puntos = pt;
	}
	
	public boolean getTurno ()
	{
		return turno;
	}
	public void setTurno (boolean t)
	{
		turno=t;
	}
	
	public boolean getDiUno()
	{
		return diUno;
	}
	public void setDiUno (boolean dU)
	{
		diUno = dU;
	}
	
	public int getNumCartas()
	{
		return numCartas;
	}
	public void setNumCartas (int nC)
	{
		numCartas = nC;
	}
	
	public void obtenerCarta(Carta[] cartas)
	{
		try
		{
			for(int i=0;i<cartas.length;i++)
			{
				misCartas.add(cartas[i]);
				numCartas++;
			}
		}
		catch(NullPointerException npe)
		{
			System.out.println("No hay más cartas que recibir");
		}
	}
	public void quitarCarta(int num)
	{
		misCartas.remove(num);
		numCartas--;
	}
	public void quitarTodasCartas()
	{
		misCartas.clear();
		numCartas=0;
	}
	public int cartasJugador()
	{
		return numCartas;
	}
}
