package reglas;

import java.util.Calendar;

import dominio.Vehiculo;

public interface ReglasIngreso {
	
	public boolean esPosibleIngreso(Vehiculo vehiculo, Calendar fechaIngreso);

}
