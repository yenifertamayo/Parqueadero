package persistencia.repositorio;

import persistencia.entity.VehiculoEntity;

public interface RepositorioVehiculoEntity {

	VehiculoEntity obtenerVehiculoEntityAParquear(String placa);
}
