package es.studium.JuegoUno;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UnoModelo 
{
	//Conexión a base de datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/uno?useSSL=false";
	String login = "root";
	String password = "Studium2020;";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
}