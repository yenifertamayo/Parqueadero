package reglas;

import dominio.Parqueadero;
import dominio.Moto;

public class ValidarCilindrajeMoto implements ValidarReglasSalida{

	@Override
	public Parqueadero valorAPagar(Parqueadero parqueadero) {

		if(parqueadero.getVehiculo() instanceof Moto){
			
			parqueadero.getVehiculo().toString();
			if(((Moto) parqueadero.getVehiculo()).getCilindraje() > 500){
				
				parqueadero.setValorAPagar(2000);
			}
		}
		
		return parqueadero;
	}
}