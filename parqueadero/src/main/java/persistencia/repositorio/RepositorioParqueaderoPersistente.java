package persistencia.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import dominio.Parqueadero;
import dominio.Vehiculo;
import dominio.repositorio.RepositorioPaqueadero;
import persistencia.builder.VehiculoBuilder;
import persistencia.entity.ParqueaderoEntity;
import persistencia.entity.VehiculoEntity;

@Repository
public class RepositorioParqueaderoPersistente implements RepositorioPaqueadero{

	private static final String PLACA = "placa";
	private static final String PARQUEADO_POR_PLACA = "Parqueadero.findByPlaca";
	private static final String CARRO = "Carro";
	private static final String CANTIDAD_VEHICULOS = "Parqueadero.findVehiculo";

	
	private EntityManager entityManager;

	public RepositorioParqueaderoPersistente(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void agregar(Parqueadero parqueadero) {
		
		ParqueaderoEntity parqueaderoEntity = buildParqueadero(parqueadero);
		
		entityManager.persist(parqueaderoEntity);
				
	}
	
	private ParqueaderoEntity buildParqueadero(Parqueadero parqueadero) {
		
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(parqueadero.getVehiculo());
		ParqueaderoEntity parqueaderoEntity = new ParqueaderoEntity();
		parqueaderoEntity.setFechaIngreso(parqueadero.getFechaIngreso());
		parqueaderoEntity.setFechaSalida(parqueadero.getFechaSalida());
		parqueaderoEntity.setValorAPagar(parqueadero.getValorAPagar());
		parqueaderoEntity.setVehiculoEntity(vehiculoEntity);
		
		return parqueaderoEntity;
	}
	
	@Override
	public Vehiculo obtenerVehiculoParqueadoPorPlaca(String placa) {
	
		ParqueaderoEntity parqueaderoEntity = obtenerVehiculoParqueadoEntityPorPlaca(placa);
		
		return VehiculoBuilder.convertirVehiculo(parqueaderoEntity != null ? parqueaderoEntity.getVehiculo() : null);

	}

	@SuppressWarnings("rawtypes")
	private ParqueaderoEntity obtenerVehiculoParqueadoEntityPorPlaca(String placa) {
		
		Query query = entityManager.createNamedQuery(PARQUEADO_POR_PLACA);
		query.setParameter(PLACA, placa);

		List resultList = query.getResultList();
		
		return !(resultList).isEmpty() ? (ParqueaderoEntity) resultList.get(0) : null;

		
		
	}

	@Override
	public Long cantidadVehiculos(Vehiculo vehiculo) {
		
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(vehiculo);

		if (vehiculoEntity.getTipo().equals(CARRO)){
			
			Query query = entityManager.createNamedQuery(CANTIDAD_VEHICULOS );
			query.setParameter("tipo", vehiculoEntity.getTipo());
			
			return (Long) query.getSingleResult();
		}
		
		else{
			Query query = entityManager.createNamedQuery(CANTIDAD_VEHICULOS );
			query.setParameter("tipo", vehiculoEntity.getTipo());
			return (Long) query.getSingleResult();
		}
	}
}
