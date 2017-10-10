package reglas;

import java.util.Calendar;

import dominio.Moto;
import dominio.Recibo;

public class ValorAPagarVehiculo implements ReglasSalida{

	
	private static final int VALOR_HORA_MOTO = 500;
	private static final int VALOR_DIA_MOTO = 600;
	private static final int VALOR_HORA_CARRO = 1000;
	private static final int VALOR_DIA_CARRO = 8000;

	@Override
	public double valorAPagar(Recibo recibo) {
			
		double numeroHoras = cantidadHoras(recibo.getFechaIngreso(), recibo.getFechaSalida());
		double numeroDias = cantidadDias(recibo.getFechaIngreso(), recibo.getFechaSalida());
		
		if(recibo.getVehiculo() instanceof Moto){
			
			double valorMoto = valorAPagarMoto(numeroHoras, numeroDias);
			
			recibo.setValorAPagar(valorMoto+recibo.getValorAPagar());
			return recibo.getValorAPagar();
		}
		
		double valorCarro = valorAPagarCarro(numeroHoras, numeroDias);
		
		recibo.setValorAPagar(valorCarro+recibo.getValorAPagar());
		return recibo.getValorAPagar();
	}

	
	private double valorAPagarMoto(double numeroHoras, double numeroDias) {
		
		return numeroDias*VALOR_DIA_MOTO + numeroHoras*VALOR_HORA_MOTO;
	}
	
	
	private double valorAPagarCarro(double numeroHoras, double numeroDias) {
		
		return numeroDias*VALOR_DIA_CARRO + numeroHoras*VALOR_HORA_CARRO;
	}
	
	
	
	public double cantidadHoras(Calendar fechaIngreso, Calendar fechaSalida) {

		double fechaInicio = fechaIngreso.getTimeInMillis();
		double fechaFin = fechaSalida.getTimeInMillis();
		double tiempoExacto = fechaFin - fechaInicio;

		double horas = Math.ceil((tiempoExacto / (60 * 60 * 1000)));
		for(int i = 9; horas >= i; i+=9){
			
			horas-=9;
		}
		
		return horas;
	}
	
	public int cantidadDias(Calendar fechaIngreso, Calendar fechaSalida){
		
		double fechaInicio = fechaIngreso.getTimeInMillis();
		double fechaFin = fechaSalida.getTimeInMillis();
		double tiempoExacto = fechaFin - fechaInicio;
		return (int) Math.floor((tiempoExacto / (9 * 60 * 60 * 1000))) % 9;
	}
	
}
