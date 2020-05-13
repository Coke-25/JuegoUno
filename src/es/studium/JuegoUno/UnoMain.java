package es.studium.JuegoUno;

import es.studium.JuegoUno.UnoControlador;
import es.studium.JuegoUno.UnoModelo;
import es.studium.JuegoUno.UnoVistaMenu;

public class UnoMain 
{
	public static void main(String[] args)
	{
		UnoVistaMenu vista = new UnoVistaMenu(0,false);
		UnoModelo modelo = new UnoModelo();
		
		new UnoControlador(modelo, vista);
	}
}
