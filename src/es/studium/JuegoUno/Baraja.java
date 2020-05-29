package es.studium.JuegoUno;


public class Baraja 
{
	private Carta[] carta = new Carta[54];
	private int posSiguienteCarta;
	private Carta cartaCentro;
	private boolean finCartas;
	
	private static final int NUMERO_TOTAL_CARTAS=54;
	
	public Baraja()
	{
		posSiguienteCarta = 0;
		
		//Crear las cartas
				//Azules
				carta[0] = new Carta("azul",0,"cartas\\az0.png",false);
				carta[1] = new Carta("azul",1,"cartas\\az1.png",false);
				carta[2] = new Carta("azul",2,"cartas\\az2.png",false);
				carta[3] = new Carta("azul",3,"cartas\\az3.png",false);
				carta[4] = new Carta("azul",4,"cartas\\az4.png",false);
				carta[5] = new Carta("azul",5,"cartas\\az5.png",false);
				carta[6] = new Carta("azul",6,"cartas\\az6.png",false);
				carta[7] = new Carta("azul",7,"cartas\\az7.png",false);
				carta[8] = new Carta("azul",8,"cartas\\az8.png",false);
				carta[9] = new Carta("azul",9,"cartas\\az9.png",false);
				carta[10] = new Carta("azul",10,"cartas\\ab.png",true);
				carta[11] = new Carta("azul",11,"cartas\\azs.png",true);
				carta[12] = new Carta("azul",12,"cartas\\am.png",true);
				//Amarillas
				carta[13] = new Carta("amarilla",0,"cartas\\am0.png",false);
				carta[14] = new Carta("amarilla",1,"cartas\\am1.png",false);
				carta[15] = new Carta("amarilla",2,"cartas\\am2.png",false);
				carta[16] = new Carta("amarilla",3,"cartas\\am3.png",false);
				carta[17] = new Carta("amarilla",4,"cartas\\am4.png",false);
				carta[18] = new Carta("amarilla",5,"cartas\\am5.png",false);
				carta[19] = new Carta("amarilla",6,"cartas\\am6.png",false);
				carta[20] = new Carta("amarilla",7,"cartas\\am7.png",false);
				carta[21] = new Carta("amarilla",8,"cartas\\am8.png",false);
				carta[22] = new Carta("amarilla",9,"cartas\\am9.png",false);
				carta[23] = new Carta("amarilla",10,"cartas\\amb.png",true);
				carta[24] = new Carta("amarilla",11,"cartas\\ams.png",true);
				carta[25] = new Carta("amarilla",12,"cartas\\amm.png",true);
				//Rojas
				carta[26] = new Carta("roja",0,"cartas\\r0.png",false);
				carta[27] = new Carta("roja",1,"cartas\\r1.png",false);
				carta[28] = new Carta("roja",2,"cartas\\r2.png",false);
				carta[29] = new Carta("roja",3,"cartas\\r3.png",false);
				carta[30] = new Carta("roja",4,"cartas\\r4.png",false);
				carta[31] = new Carta("roja",5,"cartas\\r5.png",false);
				carta[32] = new Carta("roja",6,"cartas\\r6.png",false);
				carta[33] = new Carta("roja",7,"cartas\\r7.png",false);
				carta[34] = new Carta("roja",8,"cartas\\r8.png",false);
				carta[35] = new Carta("roja",9,"cartas\\r9.png",false);
				carta[36] = new Carta("roja",10,"cartas\\rb.png",true);
				carta[37] = new Carta("roja",11,"cartas\\rs.png",true);
				carta[38] = new Carta("roja",12,"cartas\\rm.png",true);
				//Verdes
				carta[39] = new Carta("verde",0,"cartas\\v0.png",false);
				carta[40] = new Carta("verde",1,"cartas\\v1.png",false);
				carta[41] = new Carta("verde",2,"cartas\\v2.png",false);
				carta[42] = new Carta("verde",3,"cartas\\v3.png",false);
				carta[43] = new Carta("verde",4,"cartas\\v4.png",false);
				carta[44] = new Carta("verde",5,"cartas\\v5.png",false);
				carta[45] = new Carta("verde",6,"cartas\\v6.png",false);
				carta[46] = new Carta("verde",7,"cartas\\v7.png",false);
				carta[47] = new Carta("verde",8,"cartas\\v8.png",false);
				carta[48] = new Carta("verde",9,"cartas\\v9.png",false);
				carta[49] = new Carta("verde",10,"cartas\\vb.png",true);
				carta[50] = new Carta("verde",11,"cartas\\vs.png",true);
				carta[51] = new Carta("verde",12,"cartas\\vm.png",true);
				//Comodín
				carta[52] = new Carta("Elige color",13,"cartas\\comodin.png",true);
				carta[53] = new Carta("4 colores",14,"cartas\\comodin2.png",true);
				
				barajar();
	}
	
	public void barajar()
	{
		int posAleatoria;
		Carta c;
		for(int i=0;i<NUMERO_TOTAL_CARTAS;i++)
		{
			//Asigna un número aleatorio entre los dos números
			posAleatoria = UnoModelo.numeroAleatorio(1,NUMERO_TOTAL_CARTAS-1);
			//La carta aleatoria se intercambia con la carta de la posicion del bucle
			c = carta[i];
			carta[i] = carta[posAleatoria];
            carta[posAleatoria] = c;
 
        }
 
        //La posición vuelve al inicio
        posSiguienteCarta = 0;
	}
	
	public Carta siguienteCarta() 
	{
        Carta c = null;
 
        if (posSiguienteCarta == NUMERO_TOTAL_CARTAS) 
        {
            //Ya no hay más cartas
        	finCartas=true;
        } 
        else 
        {
            c = carta[posSiguienteCarta++];
        }
 
        return c;
    }
	
	public int cartasDisponible() 
	{
        return (NUMERO_TOTAL_CARTAS - posSiguienteCarta);
    }
	
	public Carta[] darCartas(int numCartas) 
	{
		 
        if (numCartas > NUMERO_TOTAL_CARTAS) 
        {
        	//Entra aquí si se dan más cartas de las que hay
        	finCartas=true;
        }
        else if (cartasDisponible() < numCartas) 
        {
        	finCartas=true;
        } 
        else 
        {
 
            Carta[] cartasDar = new Carta[numCartas];
 
            //recorro el array que acabo de crear para rellenarlo
            for (int i = 0; i < numCartas; i++) 
            {
                cartasDar[i] = siguienteCarta();//uso el metodo anterior
            }
             
            //Lo devuelvo
            return cartasDar;
 
        }
 
        //solo en caso de error
        return null;
 
    }
	
	public Carta getCartaCentro()
	{
		return cartaCentro;
	}
	public void setCartaCentro(Carta c)
	{
		cartaCentro = c;
	}
	public void setColorCartaCentro(String color)
	{
		cartaCentro.setColor(color);
	}
	public boolean getFinCartas()
	{
		return finCartas;
	}
	public void setFinCartas(boolean b)
	{
		finCartas = b;
	}
	public void setPosSiguienteCarta(int x)
	{
		posSiguienteCarta = x;
	}
}