package persistencia.repositorio;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import dominio.Vehiculo;
import dominio.repositorio.RepositorioVehiculo;
import persistencia.builder.VehiculoBuilder;
import persistencia.entity.VehiculoEntity;

@Repository
public class RepositorioVehiculoPersistente implements RepositorioVehiculo, RepositorioVehiculoEntity{
	
	private static final String PLACA = "placa";
	private static final String VEHICULO_POR_PLACA = "Vehiculo.obtenerPorPlaca";
	
	private EntityManager entityManager;

	public RepositorioVehiculoPersistente(EntityManager entityManager) {
		
		this.entityManager = entityManager;
	}

	@Override
	public void agregar(Vehiculo vehiculo) {

		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(vehiculo);
		entityManager.persist(vehiculoEntity);
	}

	@SuppressWarnings("rawtypes")
	public VehiculoEntity obtenerVehiculoEntityAParquear(String placa) {
		
		Query consulta = entityManager.createNamedQuery(VEHICULO_POR_PLACA);
		consulta.setParameter(PLACA, placa);
		
		List resultList = consulta.getResultList();
		return !(resultList).isEmpty() ? (VehiculoEntity) resultList.get(0) : null;
	}

	@Override
	public Vehiculo obtenerVehiculo(String placa) {
		VehiculoEntity vehiculoEntity = obtenerVehiculoEntityAParquear(placa);
		return VehiculoBuilder.convertirVehiculo(vehiculoEntity);
	}
}
