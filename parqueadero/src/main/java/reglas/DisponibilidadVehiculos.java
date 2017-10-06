package reglas;

import java.util.Calendar;

import dominio.Moto;
import dominio.Parqueadero;
import dominio.Vehiculo;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioRecibo;


public class DisponibilidadVehiculos implements ReglasIngreso{
	
	private RepositorioRecibo repositorioRecibo;
		
	public DisponibilidadVehiculos(RepositorioRecibo repositorioRecibo) {
		this.repositorioRecibo = repositorioRecibo;
	}

	@Override
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		Long numeroVehiculos = repositorioRecibo.cantidadVehiculos(vehiculo);
		
		if(vehiculo instanceof Moto){
			
			return disponibilidadMoto(numeroVehiculos);
		}
		
		return disponibilidadCarros(numeroVehiculos);		
	}

	private boolean disponibilidadCarros(Long numeroVehiculos) {
		
		if(numeroVehiculos >= Parqueadero.MAXIMO_CARROS){
			
			throw new ParqueaderoException("No hay cupo");
		}
		
		return false;
	}

	private boolean disponibilidadMoto(Long numeroVehiculos) {
		
		if(numeroVehiculos >= Parqueadero.MAXIMO_MOTOS){
			
			throw new ParqueaderoException("No hay cupo");
		}
		
		return false;
	}
}
