package dominio;

import java.util.Calendar;
import java.util.List;

import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioRecibo;
import reglas.ReglasIngreso;

public class Vigilante {

	private RepositorioRecibo repositorioRecibo;
	private List<ReglasIngreso> reglasParqueadero;
	private Parqueadero parqueadero;
	
	public Vigilante(RepositorioRecibo repositorioRecibo, List<ReglasIngreso> reglasParqueadero, Parqueadero parqueadero) {
		
		this.repositorioRecibo = repositorioRecibo; 
		this.reglasParqueadero = reglasParqueadero;
		this.parqueadero = parqueadero;
	}


	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		for (ReglasIngreso reglaEsCorrecto : reglasParqueadero) {

			if(reglaEsCorrecto.esPosibleIngreso(vehiculo, fechaIngreso)){
					
					return false;
			}
		}
		return true;
	}
			
	
	public Recibo ingresarVehiculo(Vehiculo vehiculo, Calendar fechaIngreso) {

		cumpleReglasParqueadero(vehiculo, fechaIngreso);
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		return recibo;
	}

	private Vehiculo cumpleReglasParqueadero(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		if(!esPosibleIngreso(vehiculo, fechaIngreso)){
			
			throw new ParqueaderoException("No es posible ingreso");
		}
				
		return noEstaParqueado(vehiculo);
	}


	private Vehiculo noEstaParqueado(Vehiculo vehiculo) {
		
		if(estaParqueado(vehiculo.getPlaca())){
			
			throw new ParqueaderoException("El vehiculo ya se encuentra parqueado");
		}
		
		return vehiculo;
	}
	
	
	public boolean estaParqueado(String placa) {
		
		Vehiculo vehiculoEstaParqueado = repositorioRecibo.obtenerVehiculoReciboPorPlaca(placa);
		
		return vehiculoEstaParqueado != null;
	}
}
