package dominio.repositorio;

import dominio.Vehiculo;

public interface RepositorioVehiculo {
	
	void agregar(Vehiculo vehiculo);

	Vehiculo obtenerVehiculo(String placa);

}
