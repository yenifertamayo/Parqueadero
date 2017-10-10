package dominio;

public class Parqueadero {

	private int maximoCarros;
	private int maximoMotos;
	private int maximoCilindrajeMoto;
	private double excedenteCilindrajeAltoMoto;
	
	public Parqueadero(int maximoCarros, int maximoMotos, int maximoCilindrajeMoto, double excedenteCilindrajeAltoMoto) {
		this.maximoCarros = maximoCarros;
		this.maximoMotos = maximoMotos;
		this.maximoCilindrajeMoto = maximoCilindrajeMoto;
		this.excedenteCilindrajeAltoMoto = excedenteCilindrajeAltoMoto;
	}

	public int getMaximoCarros() {
		return maximoCarros;
	}

	public int getMaximoMotos() {
		return maximoMotos;
	}

	public double getExcedenteCilindrajeAltoMoto() {
		return excedenteCilindrajeAltoMoto;
	}

	public int getMaximoCilindrajeMoto() {
		return maximoCilindrajeMoto;
	}
	
	
}
