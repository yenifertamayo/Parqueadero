package dominio.repositorio;

import dominio.Recibo;
import dominio.Vehiculo;

public interface RepositorioRecibo {
	
	void agregar (Recibo recibo);

	Vehiculo obtenerVehiculoReciboPorPlaca(String placa);
	
	Long cantidadVehiculos(Vehiculo vehiculo);

}
