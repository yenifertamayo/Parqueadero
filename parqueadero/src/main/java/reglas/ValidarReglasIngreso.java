package reglas;

import java.util.Calendar;

import dominio.Vehiculo;

public interface ValidarReglasIngreso {
	
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso);

}
