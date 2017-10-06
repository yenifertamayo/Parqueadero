package dominio;

import java.util.Calendar;

public class Recibo {
	
	private Calendar fechaIngreso;
	private Calendar fechaSalida;
	private Vehiculo vehiculo;
	private double valorAPagar;

	
	public Recibo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Recibo(Calendar fechaIngreso, Calendar fechaSalida, Vehiculo vehiculo, double valorAPagar) {
		super();
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.vehiculo = vehiculo;
		this.valorAPagar = valorAPagar;
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public Calendar getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	
	public double getValorAPagar() {
		return valorAPagar;
	}
	
	public void setValorAPagar(double valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
}
