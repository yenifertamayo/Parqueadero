package persistencia.builder;



import dominio.Carro;
import dominio.Moto;
import dominio.Vehiculo;
import persistencia.entity.VehiculoEntity;

public class VehiculoBuilder {
	
	private VehiculoBuilder(){}
	
	public static VehiculoEntity convertirAEntity(Vehiculo vehiculo){
		
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		
		if(vehiculo instanceof Moto){
			
			vehiculoEntity.setTipo("Moto");
			vehiculoEntity.setPlaca(vehiculo.getPlaca());
			vehiculoEntity.setCilindraje(((Moto) vehiculo).getCilindraje());
		}
		
		else {
			vehiculoEntity.setTipo("Carro");
			vehiculoEntity.setPlaca(vehiculo.getPlaca());
			vehiculoEntity.setCilindraje(0);
		}
		return vehiculoEntity;
	}

	public static Vehiculo convertirVehiculo(VehiculoEntity vehiculoEntity){
		
		Vehiculo vehiculo = null;
		
		if (vehiculoEntity != null) {
			
			if (vehiculoEntity.getTipo().equals("Moto")){
				
				vehiculo = new Moto(vehiculoEntity.getPlaca(), vehiculoEntity.getCilindraje());
			}
			
			else {
				
				vehiculo = new Carro(vehiculoEntity.getPlaca());
			}
		}
		
		return vehiculo;
	}
}
