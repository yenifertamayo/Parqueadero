package persistencia.builder;

import dominio.Parqueadero;
import dominio.Vehiculo;
import persistencia.entity.ParqueaderoEntity;
import persistencia.entity.VehiculoEntity;

public class ParqueaderoBuilder {
	
	public static ParqueaderoEntity convertirAEntity(Parqueadero parqueadero){
		
		VehiculoEntity vehiculo = VehiculoBuilder.convertirAEntity(parqueadero.getVehiculo());
		ParqueaderoEntity parqueaderoEntity = new ParqueaderoEntity();
		parqueaderoEntity.setFechaIngreso(parqueadero.getFechaIngreso());
		parqueaderoEntity.setFechaSalida(parqueadero.getFechaSalida());
		parqueaderoEntity.setValorAPagar(parqueadero.getValorAPagar());
		parqueaderoEntity.setVehiculoEntity(vehiculo);
		
		return parqueaderoEntity;
	}
}
