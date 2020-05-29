package es.studium.JuegoUno;

import es.studium.JuegoUno.UnoControlador;
import es.studium.JuegoUno.UnoModelo;
import es.studium.JuegoUno.UnoVistaMenu;

public class UnoMain 
{
	public static void main(String[] args)
	{
		UnoVistaMenu vistaMenu = new UnoVistaMenu();
		UnoVistaJuego vistaJuego = new UnoVistaJuego();
		UnoModelo modelo = new UnoModelo();
		
		new UnoControlador(modelo, vistaMenu, vistaJuego);
	}
}
