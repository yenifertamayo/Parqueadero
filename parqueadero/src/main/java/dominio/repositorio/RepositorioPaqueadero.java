package dominio.repositorio;

import dominio.Parqueadero;
import dominio.Vehiculo;

public interface RepositorioPaqueadero {
	
	void agregar (Parqueadero parqueadero);

	Vehiculo obtenerVehiculoParqueadoPorPlaca(String placa);
	
	Long cantidadVehiculos(Vehiculo vehiculo);

}
