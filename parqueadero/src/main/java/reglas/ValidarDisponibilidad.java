package reglas;

import java.util.Calendar;

import dominio.Carro;
import dominio.Moto;
import dominio.Vehiculo;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioPaqueadero;


public class ValidarDisponibilidad implements ValidarReglasIngreso{
	
	private static final int MAXIMO_CARROS = 20;
	private static final int MAXIMO_MOTOS = 10;
	private RepositorioPaqueadero repositorioPaqueadero;
	
	
	
	public ValidarDisponibilidad(RepositorioPaqueadero repositorioPaqueadero) {
		this.repositorioPaqueadero = repositorioPaqueadero;
	}

	@Override
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		Long numeroVehiculos =repositorioPaqueadero.cantidadVehiculos(vehiculo);
		
		if((vehiculo instanceof Moto && numeroVehiculos < MAXIMO_MOTOS ) || (vehiculo instanceof Carro && numeroVehiculos < MAXIMO_CARROS)){
			
			return false;
		}
		throw new ParqueaderoException("No hay cupo");

	}
}
