package reglas;

import java.util.Calendar;

import dominio.Moto;
import dominio.Parqueadero;
import dominio.Vehiculo;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioRecibo;


public class DisponibilidadVehiculos implements ReglasIngreso{
	
	
	private Parqueadero parqueadero;
	
	private RepositorioRecibo repositorioRecibo;
			
	public DisponibilidadVehiculos(RepositorioRecibo repositorioRecibo, Parqueadero parqueadero) {
		this.repositorioRecibo = repositorioRecibo;
		this.parqueadero = parqueadero;
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
		
		if(numeroVehiculos >= parqueadero.getMaximoCarros()){
			
			throw new ParqueaderoException("No hay cupo");
		}
		
		return false;
	}

	
	private boolean disponibilidadMoto(Long numeroVehiculos) {
		
		if(numeroVehiculos >= parqueadero.getMaximoMotos()){
			
			throw new ParqueaderoException("No hay cupo");
		}
		
		return false;
	}
}
