package reglas;

import java.util.Calendar;

import dominio.Carro;
import dominio.Moto;
import dominio.Vehiculo;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioPaqueadero;


public class ValidarDisponibilidad implements ValidarReglasIngreso{
	
	private RepositorioPaqueadero repositorioPaqueadero;
	
	
	
	public ValidarDisponibilidad(RepositorioPaqueadero repositorioPaqueadero) {
		this.repositorioPaqueadero = repositorioPaqueadero;
	}

	@Override
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		
		if(vehiculo instanceof Moto && repositorioPaqueadero.cantidadVehiculos(vehiculo) < 1){
			
			return false;
		}
		
		else if (vehiculo instanceof Carro && repositorioPaqueadero.cantidadVehiculos(vehiculo) < 2) {
			return false;
		}
		
		throw new ParqueaderoException("No hay cupo");

	}
}
