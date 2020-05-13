package es.studium.JuegoUno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UnoControlador implements WindowListener, ActionListener
{
	UnoVistaMenu vista = null;
	UnoModelo modelo = null;
	
	int n=vista.numeroJugadores;
	
	public UnoControlador(UnoModelo modelo, UnoVistaMenu vista)
	{
		this.vista = vista;
		this.modelo = modelo;
		
		vista.addWindowListener(this);
		vista.boton2.addActionListener(this);
		vista.boton3.addActionListener(this);
		vista.boton4.addActionListener(this);
		vista.btnJugar.addActionListener(this);
	}

	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {System.exit(0);}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(vista.boton2.equals(e.getSource()))
		{
			vista.setJugadores(2);
			vista.repaint();
		}
		else if(vista.boton3.equals(e.getSource()))
		{
			vista.setJugadores(3);
			vista.repaint();
		}
		else if(vista.boton4.equals(e.getSource()))
		{
			vista.setJugadores(4);
			vista.repaint();
		}
		if(vista.btnJugar.equals(e.getSource()))
		{
			//Se le asignan los nombres a los jugadores en la base de datos y el momento en el que jugaron
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(modelo.driver);
				//Establecer la conexión con la BD Empresa
				modelo.connection = DriverManager.getConnection(modelo.url, modelo.login, modelo.password);
				//Crear una sentencia
				modelo.statement = modelo.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				if(vista.numeroJugadores==2)
				{
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre1.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre2.getText()+"',now());");
				}
				else if(vista.numeroJugadores==3)
				{
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre1.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre2.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre3.getText()+"',now());");
				}
				else if(vista.numeroJugadores==4)
				{
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre1.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre2.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre3.getText()+"',now());");
					modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vista.txnombre4.getText()+"',now());");
				}
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			//Se cierra la ventana de recogida de datos para dar paso a la del juego
			vista.setVisible(false);
			UnoVistaMenu vista = new UnoVistaMenu(n,true);
			UnoModelo modelo = new UnoModelo();
			
			new UnoControlador(modelo, vista);
		}
	}

}
