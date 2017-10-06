package dominio;

import java.util.Calendar;
import java.util.List;

import dominio.repositorio.RepositorioPaqueadero;
import reglas.ValidarReglasIngreso;

public class Vigilante {

	private RepositorioPaqueadero repositorioPaqueadero;
	private List<ValidarReglasIngreso> reglasParqueadero;
	
	public Vigilante(RepositorioPaqueadero repositorioPaqueadero, List<ValidarReglasIngreso> reglasParqueadero) {
		
		this.repositorioPaqueadero = repositorioPaqueadero; 
		this.reglasParqueadero = reglasParqueadero;
	}


	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		for (ValidarReglasIngreso reglaEsCorrecto : reglasParqueadero) {
			if(reglaEsCorrecto.esPosibleIngreso(vehiculo, fechaIngreso)){
				return false;
			}
		}
		return true;
	}
			
	
	public Parqueadero ingresarVehiculo(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		if(!estaParqueado(vehiculo.getPlaca()) && esPosibleIngreso(vehiculo, fechaIngreso)){
			
				
		}
		
		Parqueadero parqueadero =  new Parqueadero(fechaIngreso, null, vehiculo, 0);
		
		repositorioPaqueadero.agregar(parqueadero);
		
		return parqueadero;
		
	}
	
	
	public boolean estaParqueado(String placa) {
		
		Vehiculo vehiculoEstaParqueado = repositorioPaqueadero.obtenerVehiculoParqueadoPorPlaca(placa);
		
		return vehiculoEstaParqueado != null;
	}
}
