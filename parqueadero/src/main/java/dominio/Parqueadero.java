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
}
