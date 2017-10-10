package dominio;

import java.util.Calendar;

public class ListaRecibo {
	
	private Calendar fechaIngreso;
	private String tipo;
	private String placa;
	
	
	public ListaRecibo(String placa,  String tipo, Calendar fechaIngreso) {
		super();
		this.fechaIngreso = fechaIngreso;
		this.tipo = tipo;
		this.placa = placa;
	}
	
	public ListaRecibo() {
		
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public String getTipo() {
		return tipo;
	}

	public String getPlaca() {
		return placa;
	}

}
