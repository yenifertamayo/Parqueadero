package persistencia.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import dominio.Recibo;
import dominio.Vehiculo;
import dominio.repositorio.RepositorioRecibo;
import persistencia.builder.VehiculoBuilder;
import persistencia.entity.ReciboEntity;
import persistencia.entity.VehiculoEntity;

@Repository
public class RepositorioReciboPersistente implements RepositorioRecibo{

	private static final String PLACA = "placa";
	private static final String PARQUEADO_POR_PLACA = "Recibo.findByPlaca";
	private static final String CANTIDAD_VEHICULOS = "Recibo.findVehiculo";

	
	private EntityManager entityManager;

	public RepositorioReciboPersistente(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void agregar(Recibo recibo) {
		
		ReciboEntity reciboEntity = buildRecibo(recibo);
		
		entityManager.persist(reciboEntity);
	
	}
	
	public ReciboEntity buildRecibo(Recibo recibo) {
		
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(recibo.getVehiculo());
		ReciboEntity reciboEntity = new ReciboEntity();
		reciboEntity.setFechaIngreso(recibo.getFechaIngreso());
		reciboEntity.setFechaSalida(recibo.getFechaSalida());
		reciboEntity.setValorAPagar(recibo.getValorAPagar());
		reciboEntity.setVehiculoEntity(vehiculoEntity);
		return reciboEntity;
	}
	
	@Override
	public Vehiculo obtenerVehiculoReciboPorPlaca(String placa) {
	
		ReciboEntity reciboEntity = obtenerVehiculoReciboEntityPorPlaca(placa);
		return VehiculoBuilder.convertirVehiculo(reciboEntity != null ? reciboEntity.getVehiculo() : null);
	}

	@SuppressWarnings("rawtypes")
	private ReciboEntity obtenerVehiculoReciboEntityPorPlaca(String placa) {
		
		Query query = entityManager.createNamedQuery(PARQUEADO_POR_PLACA);
		query.setParameter(PLACA, placa);

		List resultList = query.getResultList();
		
		return !(resultList).isEmpty() ? (ReciboEntity) resultList.get(0) : null;
	}

	@Override
	public Long cantidadVehiculos(Vehiculo vehiculo) {
		
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(vehiculo);
		Query query = entityManager.createNamedQuery(CANTIDAD_VEHICULOS );
		query.setParameter("tipo", vehiculoEntity.getTipo());
		return (Long) query.getSingleResult();
	}
}
