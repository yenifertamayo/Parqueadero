package persistencia.builder;

import dominio.Recibo;
import persistencia.entity.ReciboEntity;
import persistencia.entity.VehiculoEntity;

public class ParqueaderoBuilder {
	
	private ParqueaderoBuilder(){
		
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
}
