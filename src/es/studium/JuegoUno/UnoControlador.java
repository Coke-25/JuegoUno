package es.studium.JuegoUno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UnoControlador implements WindowListener, ActionListener, MouseListener
{
	UnoVistaMenu vistaMenu = null;
	UnoVistaJuego vistaJuego = null;
	UnoModelo modelo = null;
	
	public UnoControlador(UnoModelo modelo, UnoVistaMenu vistaMenu, UnoVistaJuego vistaJuego)
	{
		this.vistaMenu = vistaMenu;
		this.vistaJuego = vistaJuego;
		this.modelo = modelo;
		
		//Listeners
		vistaMenu.addWindowListener(this);
		vistaMenu.btnJugar.addActionListener(this);
		
		vistaJuego.addWindowListener(this);
		vistaJuego.addMouseListener(this);
		vistaJuego.nuevaPartidaMenu.addActionListener(this);
		vistaJuego.puntuacionesMenu.addActionListener(this);
		vistaJuego.ayudaMenu.addActionListener(this);
		vistaJuego.dlgComodin.addWindowListener(this);
		vistaJuego.dlgPuntuaciones.addWindowListener(this);
		vistaJuego.btnAzul.addActionListener(this);
		vistaJuego.btnAmarillo.addActionListener(this);
		vistaJuego.btnRojo.addActionListener(this);
		vistaJuego.btnVerde.addActionListener(this);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		if(vistaJuego.dlgComodin.isActive())
		{
			vistaJuego.dlgComodin.setVisible(false);
		}
		else if(vistaJuego.dlgPuntuaciones.isActive())	
		{
			vistaJuego.dlgPuntuaciones.setVisible(false);
		}
		else
		{
			
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(modelo.driver);
				//Establecer la conexión con la BD Empresa
				modelo.connection = DriverManager.getConnection(modelo.url, modelo.login, modelo.password);
				//Crear una sentencia
				modelo.statement = modelo.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				modelo.rs = modelo.statement.executeQuery("SELECT max(idJugador) as 'idJugador' FROM jugadores WHERE nombreJugador='"+vistaJuego.jugador1.getNombre()+"';");
				modelo.rs.next();
				int idJ1 = modelo.rs.getInt("idJugador");
				modelo.rs = modelo.statement.executeQuery("SELECT max(idJugador) as 'idJugador' FROM jugadores WHERE nombreJugador='"+vistaJuego.jugador2.getNombre()+"';");
				modelo.rs.next();
				int idJ2 = modelo.rs.getInt("idJugador");
				modelo.statement.executeUpdate("UPDATE jugadores set puntuacionJugador="+vistaJuego.jugador1.getPuntos()+" where idJugador="+idJ1+";");
				modelo.statement.executeUpdate("UPDATE jugadores set puntuacionJugador="+vistaJuego.jugador2.getPuntos()+" where idJugador="+idJ2+";");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error BD 1 (establecer puntuación)-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error BD 2 (establecer puntuación)-"+sqle.getMessage());
			}
			
			System.exit(0);
		}
	}

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
		if(vistaMenu.btnJugar.equals(e.getSource()))
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
				
				modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vistaMenu.txnombre1.getText()+"',now());");
				modelo.statement.executeUpdate("INSERT INTO jugadores(nombreJugador,fechaPartidaJugador) values ('"+vistaMenu.txnombre2.getText()+"',now());");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error BD 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error BD 2-"+sqle.getMessage());
			}
			//Se le asignan los nombres de los jugadores a sus respectivas clases
			vistaJuego.jugador1.setNombre(vistaMenu.txnombre1.getText());
			vistaJuego.jugador2.setNombre(vistaMenu.txnombre2.getText());
			//Se cierra la ventana de recogida de datos para dar paso a la del juego
			vistaMenu.setVisible(false);
			vistaJuego.setVisible(true);
			
			//Se reparte la primera mano de cartas a cada jugador
			vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(7));
			vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(7));
			
			
			//Se asigna la carta del centro
			Carta[] auxCarta = vistaJuego.baraja.darCartas(1);
			Carta auxCarta2=null;
			
			if(auxCarta[0].getEspecial()==false)
			{
				auxCarta2 = auxCarta[0];
			}
			//Si es una carta especial se pasa hasta que sea numérica
			else
			{
				while(auxCarta[0].getEspecial()==true)
				{
					auxCarta = vistaJuego.baraja.darCartas(1);
					auxCarta2 = auxCarta[0];
				}
			}
			
			vistaJuego.baraja.setCartaCentro(auxCarta2);
			vistaJuego.repaint();
			
			vistaJuego.jugador1.setTurno(true);
		}
		
		//Botones del comodín para cambiar de color
		if(vistaJuego.btnAzul.equals(e.getSource()))
		{
			vistaJuego.baraja.setColorCartaCentro("azul");
			vistaJuego.dlgComodin.setVisible(false);
		}
		else if(vistaJuego.btnRojo.equals(e.getSource()))
		{
			vistaJuego.baraja.setColorCartaCentro("roja");
			vistaJuego.dlgComodin.setVisible(false);
		}
		else if(vistaJuego.btnAmarillo.equals(e.getSource()))
		{
			vistaJuego.baraja.setColorCartaCentro("amarilla");
			vistaJuego.dlgComodin.setVisible(false);
		}
		else if(vistaJuego.btnVerde.equals(e.getSource()))
		{
			vistaJuego.baraja.setColorCartaCentro("verde");
			vistaJuego.dlgComodin.setVisible(false);
		}
		//Para nueva partida
		if(vistaJuego.nuevaPartidaMenu.equals(e.getSource()))
		{
			vistaJuego.setError(false);
			vistaJuego.jugador1.setPuntos(1000);
			vistaJuego.jugador2.setPuntos(1000);
			
			vistaJuego.jugador1.quitarTodasCartas();
			vistaJuego.jugador2.quitarTodasCartas();
			
			vistaJuego.baraja.setFinCartas(false);
			vistaJuego.baraja.setPosSiguienteCarta(0);
			vistaJuego.baraja.barajar();
			//Se reparte la primera mano de cartas a cada jugador
			vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(7));
			vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(7));
			
			//Se asigna la carta del centro
			Carta[] auxCarta = vistaJuego.baraja.darCartas(1);
			Carta auxCarta2=null;
			
			if(auxCarta[0].getEspecial()==false)
			{
				auxCarta2 = auxCarta[0];
			}
			//Si es una carta especial se pasa hasta que sea numérica
			else
			{
				while(auxCarta[0].getEspecial()==true)
				{
					auxCarta = vistaJuego.baraja.darCartas(1);
					auxCarta2 = auxCarta[0];
				}
			}
			
			vistaJuego.baraja.setCartaCentro(auxCarta2);
			
			vistaJuego.jugador1.setTurno(true);
			vistaJuego.repaint();		
		}
		
		if(vistaJuego.puntuacionesMenu.equals(e.getSource()))
		{
			try
			{
				String consulta="";
				//Cargar los controladores para el acceso a la BD
				Class.forName(modelo.driver);
				//Establecer la conexión con la BD Empresa
				modelo.connection = DriverManager.getConnection(modelo.url, modelo.login, modelo.password);
				//Crear una sentencia
				modelo.statement = modelo.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				modelo.rs= modelo.statement.executeQuery("SELECT nombreJugador,puntuacionJugador,fechaPartidaJugador FROM jugadores ORDER BY 2 desc LIMIT 10;");
				while(modelo.rs.next())
				{
					//obtenemos el nombre del jugador
					String x = "       " + modelo.rs.getString("nombreJugador") +"                   ";
					//obtenemos puntuación
					x = x + modelo.rs.getInt("puntuacionJugador") +"             ";
					//obtenemos fecha de la partida
					String fecha = modelo.rs.getString("fechaPartidaJugador");
					//Separamos el string fecha entre la fecha y la hora
					String parte[] = fecha.split(" ");
					//La parte de la fecha se divide para ordenarlo en formato europeo
					String parteFecha[] = parte[0].split("-");
					parte[0] = parteFecha[2] + "-" + parteFecha[1] + "-" + parteFecha[0];
					//La parte de la hora se divide para dejar solo la hora y los minutos
					String parteHora[] = parte[1].split(":");
					parte[1] =  parteHora[0] + ":" + parteHora[1];
					x = x + "Fecha: " + parte[0] + " Hora: " + parte[1];
					//Se añade todo en una linea
					consulta = consulta + x + "\n";
				}
				String titulo = "------Nombre --------- Puntuación -------------------- Fecha";
				consulta = titulo + "\n" + consulta;
				vistaJuego.txArea.setText(consulta);
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
			}
			
			vistaJuego.dlgPuntuaciones.setVisible(true);
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		//Obtenemos las coordenadas del lugar donde se ha pulsado con el ratón
		int pulX = arg0.getX();
		int pulY = arg0.getY();
		
		//Para que se refresque la pantalla cuando se gana al acabarse las cartas
		if(vistaJuego.baraja.getFinCartas()==true)
		{
			vistaJuego.repaint();
		}
		
		if((vistaJuego.jugador1.getNumCartas()==0)||(vistaJuego.jugador2.getNumCartas()==0))
		{
			System.out.println("maincra");
			try
			{
				//Cargar los controladores para el acceso a la BD
				Class.forName(modelo.driver);
				//Establecer la conexión con la BD Empresa
				modelo.connection = DriverManager.getConnection(modelo.url, modelo.login, modelo.password);
				//Crear una sentencia
				modelo.statement = modelo.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				modelo.rs = modelo.statement.executeQuery("SELECT idJugador FROM jugadores WHERE nombreJugador='"+vistaJuego.jugador1.getNombre()+"';");
				int idJ1 = modelo.rs.getInt("idJugador");
				modelo.rs = modelo.statement.executeQuery("SELECT idJugador FROM jugadores WHERE nombreJugador='"+vistaJuego.jugador2.getNombre()+"';");
				int idJ2 = modelo.rs.getInt("idJugador");
				modelo.statement.executeUpdate("UPDATE jugadores set puntuacionJugador="+vistaJuego.jugador1.getPuntos()+" where idJugador="+idJ1+";");
				modelo.statement.executeUpdate("UPDATE jugadores set puntuacionJugador="+vistaJuego.jugador2.getPuntos()+" where idJugador="+idJ2+";");
				
			}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error BD 1 (establecer puntuación)-"+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				System.out.println("Error BD 2 (establecer puntuación)-"+sqle.getMessage());
			}
		}
		//System.out.println("X - " + pulX + " Y - "+ pulY);
		
			//Botón de pedir cartas
			if((pulX>1184)&&(pulX<1540)&&(pulY<647)&&(pulY>400))
			{
				if(vistaJuego.jugador1.getTurno()==true)
				{
					vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(2));
					vistaJuego.jugador1.setTurno(false);
					vistaJuego.jugador2.setTurno(true);
					vistaJuego.setError(false);
					
					//puntos
					int puntos = vistaJuego.jugador1.getPuntos();
					puntos -=10;
					vistaJuego.jugador1.setPuntos(puntos);
					
					vistaJuego.repaint();
				}
				else
				{
					vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(2));
					vistaJuego.jugador2.setTurno(false);
					vistaJuego.jugador1.setTurno(true);
					vistaJuego.setError(false);
					
					//puntos
					int puntos = vistaJuego.jugador2.getPuntos();
					puntos -=10;
					vistaJuego.jugador2.setPuntos(puntos);
					
					vistaJuego.repaint();
				}
			}
			
			//Botones de pasar turno
			if((pulX>1455)&&(pulX<1661)&&(pulY<302)&&(pulY>226))
			{
				vistaJuego.jugador1.setTurno(false);
				vistaJuego.jugador2.setTurno(true);
				vistaJuego.setError(false);
				
				//puntos
				int puntos = vistaJuego.jugador1.getPuntos();
				puntos -=30;
				vistaJuego.jugador1.setPuntos(puntos);
				
				vistaJuego.repaint();
			}
			if((pulX>1455)&&(pulX<1661)&&(pulY<763)&&(pulY>688))
			{
				vistaJuego.jugador2.setTurno(false);
				vistaJuego.jugador1.setTurno(true);
				vistaJuego.setError(false);
				
				//puntos
				int puntos = vistaJuego.jugador2.getPuntos();
				puntos -=30;
				vistaJuego.jugador2.setPuntos(puntos);
				
				vistaJuego.repaint();
			}
			
			//Botones para jugador1
			int bordeIzquierdo=130;
			int bordeDerecho=170;
			
			int ultimaPosicion=0;
			int ultimoI=0;
			
			for(int i=0;i<vistaJuego.jugador1.getNumCartas();i++)
			{
				if((pulX>bordeIzquierdo)&&(pulX<bordeDerecho)&&(pulY<220)&&(pulY>60))
				{
					if(vistaJuego.jugador1.getTurno()==true)
					{
						//Carta de intermisión y retorno (Como solo hay 2 jugadores son iguales)
						if(((vistaJuego.jugador1.misCartas.get(i).getNumero()==10)||(vistaJuego.jugador1.misCartas.get(i).getNumero()==11))&&(vistaJuego.jugador1.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(i));
							vistaJuego.jugador1.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador1.getPuntos();
							puntos -=30;
							vistaJuego.jugador1.setPuntos(puntos);
							
							vistaJuego.repaint();
						}
						else if((vistaJuego.jugador1.misCartas.get(i).getNumero()==12)&&(vistaJuego.jugador1.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
						{
							vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(2));
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(i));
							vistaJuego.jugador1.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador1.getPuntos();
							puntos -=20;
							vistaJuego.jugador1.setPuntos(puntos);
							
							vistaJuego.repaint();
						}
						else if(vistaJuego.jugador1.misCartas.get(i).getNumero()==13)
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(i));
							vistaJuego.jugador1.quitarCarta(i);
							vistaJuego.setError(false);
							vistaJuego.dlgComodin.setVisible(true);
							
							//puntos
							int puntos = vistaJuego.jugador1.getPuntos();
							puntos -=50;
							vistaJuego.jugador1.setPuntos(puntos);
							
							vistaJuego.jugador1.setTurno(false);
							vistaJuego.jugador2.setTurno(true);
							vistaJuego.repaint();
						}
						else if(vistaJuego.jugador1.misCartas.get(i).getNumero()==14)
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(i));
							vistaJuego.jugador1.quitarCarta(i);
							vistaJuego.setError(false);
							vistaJuego.dlgComodin.setVisible(true);
							vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(4));
							
							//puntos
							int puntos = vistaJuego.jugador1.getPuntos();
							puntos -=50;
							vistaJuego.jugador1.setPuntos(puntos);
							
							vistaJuego.jugador1.setTurno(false);
							vistaJuego.jugador2.setTurno(true);
							vistaJuego.repaint();
						}
						else if(((vistaJuego.jugador1.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor())||(vistaJuego.jugador1.misCartas.get(i).getNumero()==vistaJuego.baraja.getCartaCentro().getNumero()))
								&&((vistaJuego.jugador1.misCartas.get(i).getNumero()!=10)&&(vistaJuego.jugador1.misCartas.get(i).getNumero()!=11)&&(vistaJuego.jugador1.misCartas.get(i).getNumero()!=12)))
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(i));
							vistaJuego.jugador1.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador1.getPuntos();
							puntos -=10;
							vistaJuego.jugador1.setPuntos(puntos);
							
							vistaJuego.jugador1.setTurno(false);
							vistaJuego.jugador2.setTurno(true);
							vistaJuego.repaint();
						}
						else
						{
							vistaJuego.setError(true);
							vistaJuego.repaint();
						}
					}
				}
				bordeIzquierdo = bordeIzquierdo + 40;
				bordeDerecho = bordeDerecho + 40;
				
				ultimaPosicion = bordeDerecho - 40;
				ultimoI = i;
			}
			//Habilitar el resto de la última carta para que se pueda pinchar en toda ella
			if((pulX>ultimaPosicion)&&(pulX<(ultimaPosicion+85))&&(pulY<220)&&(pulY>60))
			{
				if(vistaJuego.jugador1.getTurno()==true)
				{
					//Carta de intermisión y retorno (Como solo hay 2 jugadores son iguales)
					if(((vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==10)||(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==11))&&(vistaJuego.jugador1.misCartas.get(ultimoI).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI));
						vistaJuego.jugador1.quitarCarta(ultimoI);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador1.getPuntos();
						puntos -=30;
						vistaJuego.jugador1.setPuntos(puntos);
						
						vistaJuego.repaint();
					}
					else if((vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==12)&&(vistaJuego.jugador1.misCartas.get(ultimoI).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
					{
						vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(2));
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI));
						vistaJuego.jugador1.quitarCarta(ultimoI);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador1.getPuntos();
						puntos -=20;
						vistaJuego.jugador1.setPuntos(puntos);
						
						vistaJuego.repaint();
					}
					else if(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==13)
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI));
						vistaJuego.jugador1.quitarCarta(ultimoI);
						vistaJuego.setError(false);
						vistaJuego.dlgComodin.setVisible(true);
						
						//puntos
						int puntos = vistaJuego.jugador1.getPuntos();
						puntos -=50;
						vistaJuego.jugador1.setPuntos(puntos);
						
						vistaJuego.jugador1.setTurno(false);
						vistaJuego.jugador2.setTurno(true);
						vistaJuego.repaint();
					}
					else if(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==14)
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI));
						vistaJuego.jugador1.quitarCarta(ultimoI);
						vistaJuego.setError(false);
						vistaJuego.dlgComodin.setVisible(true);
						vistaJuego.jugador2.obtenerCarta(vistaJuego.baraja.darCartas(4));
						
						//puntos
						int puntos = vistaJuego.jugador1.getPuntos();
						puntos -=50;
						vistaJuego.jugador1.setPuntos(puntos);
						
						vistaJuego.jugador1.setTurno(false);
						vistaJuego.jugador2.setTurno(true);
						vistaJuego.repaint();
					}
					else if(((vistaJuego.jugador1.misCartas.get(ultimoI).getColor()==vistaJuego.baraja.getCartaCentro().getColor())||(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()==vistaJuego.baraja.getCartaCentro().getNumero()))
							&&((vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()!=10)&&(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()!=11)&&(vistaJuego.jugador1.misCartas.get(ultimoI).getNumero()!=12)))
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI));
						vistaJuego.jugador1.quitarCarta(ultimoI);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador1.getPuntos();
						puntos -=10;
						vistaJuego.jugador1.setPuntos(puntos);
						
						vistaJuego.jugador1.setTurno(false);
						vistaJuego.jugador2.setTurno(true);
						vistaJuego.repaint();
					}
					else
					{
						vistaJuego.setError(true);
						vistaJuego.repaint();
					}
				}
			}
			
			///////////////////////////////////////////////////////////////////////////////
			
			//Botones para jugador2
			int bordeIzquierdo2=130;
			int bordeDerecho2=170;
			
			int ultimaPosicion2=0;
			int ultimoI2=0;
			
			for(int i=0;i<vistaJuego.jugador2.getNumCartas();i++)
			{
				if((pulX>bordeIzquierdo2)&&(pulX<bordeDerecho2)&&(pulY<1000)&&(pulY>840))
				{
					if(vistaJuego.jugador2.getTurno()==true)
					{
						//Carta de intermisión y retorno (Como solo hay 2 jugadores son iguales)
						if(((vistaJuego.jugador2.misCartas.get(i).getNumero()==10)||(vistaJuego.jugador2.misCartas.get(i).getNumero()==11))&&(vistaJuego.jugador2.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(i));
							vistaJuego.jugador2.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador2.getPuntos();
							puntos -=30;
							vistaJuego.jugador2.setPuntos(puntos);
							
							vistaJuego.repaint();
						}
						else if((vistaJuego.jugador2.misCartas.get(i).getNumero()==12)&&(vistaJuego.jugador2.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
						{
							vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(2));
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(i));
							vistaJuego.jugador2.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador2.getPuntos();
							puntos -=20;
							vistaJuego.jugador2.setPuntos(puntos);
							
							vistaJuego.repaint();
						}
						else if(vistaJuego.jugador2.misCartas.get(i).getNumero()==13)
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(i));
							vistaJuego.jugador2.quitarCarta(i);
							vistaJuego.setError(false);
							vistaJuego.dlgComodin.setVisible(true);
							
							//puntos
							int puntos = vistaJuego.jugador2.getPuntos();
							puntos -=50;
							vistaJuego.jugador2.setPuntos(puntos);
							
							vistaJuego.jugador2.setTurno(false);
							vistaJuego.jugador1.setTurno(true);
							vistaJuego.repaint();
						}
						else if(vistaJuego.jugador2.misCartas.get(i).getNumero()==14)
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(i));
							vistaJuego.jugador2.quitarCarta(i);
							vistaJuego.setError(false);
							vistaJuego.dlgComodin.setVisible(true);
							vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(4));
							
							//puntos
							int puntos = vistaJuego.jugador2.getPuntos();
							puntos -=50;
							vistaJuego.jugador2.setPuntos(puntos);
							
							vistaJuego.jugador2.setTurno(false);
							vistaJuego.jugador1.setTurno(true);
							vistaJuego.repaint();
						}
						//Cartas numéricas
						else if(((vistaJuego.jugador2.misCartas.get(i).getColor()==vistaJuego.baraja.getCartaCentro().getColor())||(vistaJuego.jugador2.misCartas.get(i).getNumero()==vistaJuego.baraja.getCartaCentro().getNumero()))
								&&((vistaJuego.jugador2.misCartas.get(i).getNumero()!=10)&&(vistaJuego.jugador2.misCartas.get(i).getNumero()!=11)&&(vistaJuego.jugador2.misCartas.get(i).getNumero()!=12)))
						{
							vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(i));
							vistaJuego.jugador2.quitarCarta(i);
							vistaJuego.setError(false);
							
							//puntos
							int puntos = vistaJuego.jugador2.getPuntos();
							puntos -=10;
							vistaJuego.jugador2.setPuntos(puntos);
							
							vistaJuego.jugador2.setTurno(false);
							vistaJuego.jugador1.setTurno(true);
							vistaJuego.repaint();
						}
						else
						{
							vistaJuego.setError(true);
							vistaJuego.repaint();
						}
					}
				}
				bordeIzquierdo2 = bordeIzquierdo2 + 40;
				bordeDerecho2 = bordeDerecho2 + 40;
				
				ultimaPosicion2 = bordeDerecho2 - 40;
				ultimoI2 = i;
			}
			//Habilitar el resto de la última carta para que se pueda pinchar en toda ella
			if((pulX>ultimaPosicion2)&&(pulX<(ultimaPosicion2+85))&&(pulY<1000)&&(pulY>840))
			{
				if(vistaJuego.jugador2.getTurno()==true)
				{
					//Carta de intermisión y retorno (Como solo hay 2 jugadores son iguales)
					if(((vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==10)||(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==11))&&(vistaJuego.jugador2.misCartas.get(ultimoI2).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(ultimoI2));
						vistaJuego.jugador2.quitarCarta(ultimoI2);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador2.getPuntos();
						puntos -=30;
						vistaJuego.jugador2.setPuntos(puntos);
						
						vistaJuego.repaint();
					}
					else if((vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==12)&&(vistaJuego.jugador2.misCartas.get(ultimoI2).getColor()==vistaJuego.baraja.getCartaCentro().getColor()))
					{
						vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(2));
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador1.misCartas.get(ultimoI2));
						vistaJuego.jugador2.quitarCarta(ultimoI2);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador2.getPuntos();
						puntos -=20;
						vistaJuego.jugador2.setPuntos(puntos);
						
						vistaJuego.repaint();
					}
					else if(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==13)
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(ultimoI2));
						vistaJuego.jugador2.quitarCarta(ultimoI2);
						vistaJuego.setError(false);
						vistaJuego.dlgComodin.setVisible(true);
						
						//puntos
						int puntos = vistaJuego.jugador2.getPuntos();
						puntos -=50;
						vistaJuego.jugador2.setPuntos(puntos);
						
						vistaJuego.jugador2.setTurno(false);
						vistaJuego.jugador1.setTurno(true);
						vistaJuego.repaint();
					}
					else if(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==14)
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(ultimoI2));
						vistaJuego.jugador2.quitarCarta(ultimoI2);
						vistaJuego.setError(false);
						vistaJuego.dlgComodin.setVisible(true);
						vistaJuego.jugador1.obtenerCarta(vistaJuego.baraja.darCartas(4));
						
						//puntos
						int puntos = vistaJuego.jugador2.getPuntos();
						puntos -=50;
						vistaJuego.jugador2.setPuntos(puntos);
						
						vistaJuego.jugador2.setTurno(false);
						vistaJuego.jugador1.setTurno(true);
						vistaJuego.repaint();
					}
					else if(((vistaJuego.jugador2.misCartas.get(ultimoI2).getColor()==vistaJuego.baraja.getCartaCentro().getColor())||(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()==vistaJuego.baraja.getCartaCentro().getNumero()))
							&&((vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()!=10)&&(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()!=11)&&(vistaJuego.jugador2.misCartas.get(ultimoI2).getNumero()!=12)))
					{
						vistaJuego.baraja.setCartaCentro(vistaJuego.jugador2.misCartas.get(ultimoI2));
						vistaJuego.jugador2.quitarCarta(ultimoI2);
						vistaJuego.setError(false);
						
						//puntos
						int puntos = vistaJuego.jugador2.getPuntos();
						puntos -=10;
						vistaJuego.jugador2.setPuntos(puntos);
						
						vistaJuego.jugador2.setTurno(false);
						vistaJuego.jugador1.setTurno(true);
						vistaJuego.repaint();
					}
					else
					{
						vistaJuego.setError(true);
						vistaJuego.repaint();
					}
				}
			}
		}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
