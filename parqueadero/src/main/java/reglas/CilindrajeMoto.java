package reglas;

import dominio.Recibo;

import org.springframework.beans.factory.annotation.Autowired;

import dominio.Moto;
import dominio.Parqueadero;

public class CilindrajeMoto implements ReglasSalida{

	@Autowired
	Parqueadero parqueadero;
	
	public CilindrajeMoto(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
	}

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
		
		if(((Moto) recibo.getVehiculo()).getCilindraje() > parqueadero.getMaximoCilindrajeMoto()){
			
			recibo.setValorAPagar(parqueadero.getExcedenteCilindrajeAltoMoto()+recibo.getValorAPagar());	
			
			return recibo.getValorAPagar();
		}
		
		return recibo.getValorAPagar();
	}
}