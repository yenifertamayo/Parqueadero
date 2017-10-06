package reglas;

import dominio.Parqueadero;
import dominio.Moto;

public class ValidarCilindrajeMoto implements ValidarReglasSalida{

	private static final int EXCEDENTE_ALTO_CILINDRAJE = 2000;
	private static final int MAXIMO_CILINDRAJE = 500;

	@Override
	public Parqueadero valorAPagar(Parqueadero parqueadero) {

		verificarSiEsMoto(parqueadero);
		
		return parqueadero;
	}

	private void verificarSiEsMoto(Parqueadero parqueadero) {
		if(parqueadero.getVehiculo() instanceof Moto){
			
			validarCilindraje(parqueadero);
		}
	}

	private void validarCilindraje(Parqueadero parqueadero) {
		
		if(((Moto) parqueadero.getVehiculo()).getCilindraje() > MAXIMO_CILINDRAJE){
			
			parqueadero.setValorAPagar(EXCEDENTE_ALTO_CILINDRAJE);
		}
	}
}