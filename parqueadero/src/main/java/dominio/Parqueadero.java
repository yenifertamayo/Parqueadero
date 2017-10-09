package dominio;

public class Parqueadero {

	private int maximoCarros;
	private int maximoMotos;
		
	public Parqueadero(int maximoCarros, int maximoMotos) {
		this.maximoCarros = maximoCarros;
		this.maximoMotos = maximoMotos;
	}

	public int getMaximoCarros() {
		return maximoCarros;
	}

	public int getMaximoMotos() {
		return maximoMotos;
	}

	
	
//	public static final int MAXIMO_CARROS = 20;
//	public static final int MAXIMO_MOTOS = 10;
//	public static final int EXCEDENTE_ALTO_CILINDRAJE = 2000;
//	public static final int MAXIMO_CILINDRAJE = 500;
	
}
