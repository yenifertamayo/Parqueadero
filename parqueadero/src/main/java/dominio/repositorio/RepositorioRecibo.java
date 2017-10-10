package dominio.repositorio;

import java.util.List;

import dominio.ListaRecibo;
import dominio.Recibo;
import dominio.Vehiculo;

public interface RepositorioRecibo {
	
	void agregar (Recibo recibo);

	Vehiculo obtenerVehiculoReciboPorPlaca(String placa);
		
	Long cantidadVehiculos(Vehiculo vehiculo);

	Recibo obtenerReciboPorPlaca(String placa);
	
	void actualizarRecibo(Recibo recibo);

	List<ListaRecibo> obtenerListaParqueados();

}
