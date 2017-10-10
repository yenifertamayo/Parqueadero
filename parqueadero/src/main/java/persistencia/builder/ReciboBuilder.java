package persistencia.builder;

import dominio.Recibo;
import dominio.Vehiculo;
import persistencia.entity.ReciboEntity;
import persistencia.entity.VehiculoEntity;

public class ReciboBuilder {
	
	private ReciboBuilder(){
		
	}
	
	public static ReciboEntity convertirAEntity(Recibo recibo){
		
		VehiculoEntity vehiculo = VehiculoBuilder.convertirAEntity(recibo.getVehiculo());
		ReciboEntity reciboEntity = new ReciboEntity();
		reciboEntity.setFechaIngreso(recibo.getFechaIngreso());
		reciboEntity.setFechaSalida(recibo.getFechaSalida());
		reciboEntity.setValorAPagar(recibo.getValorAPagar());
		reciboEntity.setVehiculoEntity(vehiculo);
		
		return reciboEntity;
	}
	
	public static Recibo convertirADominio(ReciboEntity reciboEntity){
		
		Recibo recibo = null;
		
		if(reciboEntity != null){
			
			Vehiculo vehiculo = VehiculoBuilder.convertirVehiculo(reciboEntity.getVehiculo());
			recibo = new Recibo(reciboEntity.getFechaIngreso(), reciboEntity.getFechaSalida(), vehiculo, reciboEntity.getValorAPagar());
		}
		
		return recibo;
		
	}
	
}
