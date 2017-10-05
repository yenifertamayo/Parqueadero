package reglas;

import java.util.Calendar;

import dominio.Vehiculo;
import dominio.excepcion.ParqueaderoException;


public class ValidarPlaca implements ValidarReglasIngreso{

	@Override
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso) {
		
		char primeraLetraPlaca = vehiculo.getPlaca().toUpperCase().charAt(0); 
		String valorPrimeraLetra = Character.toString(primeraLetraPlaca);
		
		if(valorPrimeraLetra.equals("A")){
			
			if (fechaIngreso.get(Calendar.DAY_OF_WEEK)  == Calendar.MONDAY || fechaIngreso.get(Calendar.DAY_OF_WEEK)  == Calendar.SUNDAY){
				
				return false;
			}
			
			throw new ParqueaderoException("No puede ingresar porque no es un dia habil");
		}
		
		return false;
	}

}
