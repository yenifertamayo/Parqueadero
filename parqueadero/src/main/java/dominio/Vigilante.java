package dominio;

import java.util.Calendar;
import java.util.List;

import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioRecibo;
import dominio.repositorio.RepositorioVehiculo;
import reglas.ReglasIngreso;
import reglas.ReglasSalida;

public class Vigilante {

	private RepositorioVehiculo repositorioVehiculo;
	private RepositorioRecibo repositorioRecibo;
	private List<ReglasIngreso> reglasIngreso;
	private List<ReglasSalida> reglasSalida;
	
	@SuppressWarnings("unused")
	private Parqueadero parqueadero;

	public Vigilante(RepositorioRecibo repositorioRecibo, RepositorioVehiculo repositorioVehiculo, List<ReglasIngreso> reglasIngreso, 
			List<ReglasSalida> reglasSalida, Parqueadero parqueadero) {

		this.repositorioRecibo = repositorioRecibo;
		this.repositorioVehiculo = repositorioVehiculo;
		this.reglasIngreso = reglasIngreso;
		this.reglasSalida = reglasSalida;
		this.parqueadero = parqueadero;
	}


	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		for (ReglasIngreso reglaEsCorrecto : reglasIngreso) {

			if(reglaEsCorrecto.esPosibleIngreso(vehiculo, fechaIngreso)){
					return false;
			}
		}
		return true;
	}
	
	
	public double valorACobrar(Recibo recibo){
		
		for (ReglasSalida reglaEsCorrecto : reglasSalida) {
			reglaEsCorrecto.valorAPagar(recibo) ;
			}
		return recibo.getValorAPagar();
	}
			
	
	public Recibo ingresarVehiculo(Vehiculo vehiculo, Calendar fechaIngreso) {

		cumpleReglasParqueadero(vehiculo, fechaIngreso);
		
		if(obtenerVehiculoPorPlaca(vehiculo.getPlaca())){
			repositorioVehiculo.agregar(vehiculo);
		}
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);	
		repositorioRecibo.agregar(recibo);
		return recibo;
	}
	

	public Recibo salidaVehiculo(String placa){
		
		if(estaParqueado(placa)){
			
			Recibo recibo = obtenerRecibo(placa);
			Calendar fechaSalida = Calendar.getInstance();
			recibo.setFechaSalida(fechaSalida);
			
			if(valorACobrar(recibo) != 0){
				
				repositorioRecibo.actualizarRecibo(recibo);
				
				return recibo;
			}
		}
		
		throw new ParqueaderoException("El vehiculo no se encuntra parqueado actualmente");		
	}


	public Recibo obtenerRecibo(String placa) {

		return repositorioRecibo.obtenerReciboPorPlaca(placa);
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
	
	public boolean obtenerVehiculoPorPlaca(String placa){
		
		return repositorioVehiculo.obtenerVehiculo(placa)== null;
	}


	public List<ListaRecibo> listaVehiculos() {
		
		return repositorioRecibo.obtenerListaParqueados();
	}

}
