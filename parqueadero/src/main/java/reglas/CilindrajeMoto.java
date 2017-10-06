package reglas;

import dominio.Recibo;
import dominio.Moto;
import dominio.Parqueadero;

public class CilindrajeMoto implements ReglasSalida{

	@Override
	public double valorAPagar(Recibo recibo) {
		
		return verificarSiEsMoto(recibo);
	}

	private double verificarSiEsMoto(Recibo recibo) {
		
		if(recibo.getVehiculo() instanceof Moto){
			
			validarCilindraje(recibo);
		}
		
		return 0;
	}

	private double validarCilindraje(Recibo recibo) {
		
		if(((Moto) recibo.getVehiculo()).getCilindraje() > Parqueadero.MAXIMO_CILINDRAJE){
			
			recibo.setValorAPagar(Parqueadero.EXCEDENTE_ALTO_CILINDRAJE);
			
			return recibo.getValorAPagar();
		}
		
		return 0;
	}
}