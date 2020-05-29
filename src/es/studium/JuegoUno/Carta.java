package es.studium.JuegoUno;

public class Carta 
{
	private String color;
	private int numero;
	private String enlace;
	private boolean especial;
	
	public Carta()
	{
		color="";
		numero=0;
		enlace="";
		especial=false;
	}
	public Carta(String c, int n, String en, boolean es)
	{
		color=c;
		numero=n;
		enlace=en;
		especial=es;
	}
	
	public String getColor ()
	{
		return color;
	}
	public void setColor (String c)
	{
		color = c;
	}
	public int getNumero ()
	{
		return numero;
	}
	public void setNumero (int n)
	{
		numero = n;
	}
	public String getEnlace()
	{
		return enlace;
	}
	public void setEnlace(String en)
	{
		enlace = en;
	}
	public boolean getEspecial()
	{
		return especial;
	}
	public void setEspecial (boolean es)
	{
		especial = es;
	}
}