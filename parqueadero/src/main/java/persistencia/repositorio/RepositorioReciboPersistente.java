package persistencia.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import dominio.ListaRecibo;
import dominio.Moto;
import dominio.Recibo;
import dominio.Vehiculo;
import dominio.repositorio.RepositorioRecibo;
import persistencia.builder.ReciboBuilder;
import persistencia.builder.VehiculoBuilder;
import persistencia.entity.ReciboEntity;
import persistencia.entity.VehiculoEntity;

@Repository
public class RepositorioReciboPersistente implements RepositorioRecibo {

	private static final String PLACA = "placa";
	private static final String PARQUEADO_POR_PLACA = "Recibo.findByPlaca";
	private static final String CANTIDAD_VEHICULOS = "Recibo.findVehiculo";
	private static final String PARQUEADOS_FIND = "Recibo.findAllParqueados";

	private EntityManager entityManager;

	public RepositorioReciboPersistente(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void agregar(Recibo recibo) {

		ReciboEntity reciboEntity = buildRecibo(recibo);
		RepositorioVehiculoPersistente repositorioVehiculoPersistente = new RepositorioVehiculoPersistente(entityManager);
		VehiculoEntity vehiculoEntity = repositorioVehiculoPersistente.obtenerVehiculoEntityAParquear(recibo.getVehiculo().getPlaca());
		reciboEntity.setVehiculoEntity(vehiculoEntity);

		entityManager.persist(reciboEntity);

	}

	public ReciboEntity buildRecibo(Recibo recibo) {

		ReciboEntity reciboEntity = new ReciboEntity();
		reciboEntity.setFechaIngreso(recibo.getFechaIngreso());
		reciboEntity.setFechaSalida(recibo.getFechaSalida());
		reciboEntity.setValorAPagar(recibo.getValorAPagar());
		return reciboEntity;
	}

	@Override
	public Vehiculo obtenerVehiculoReciboPorPlaca(String placa) {
		Vehiculo vehiculo=null;
		ReciboEntity reciboEntity = obtenerVehiculoReciboEntityPorPlaca(placa);
		if(reciboEntity!=null){
			Recibo recibo= ReciboBuilder.convertirADominio(reciboEntity);
			vehiculo= recibo.getVehiculo();
		}
		return vehiculo;
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
		Query query = entityManager.createNamedQuery(CANTIDAD_VEHICULOS);
		query.setParameter("tipo", vehiculoEntity.getTipo());
		return (Long) query.getSingleResult();
	}

	@Override
	public Recibo obtenerReciboPorPlaca(String placa) {
		ReciboEntity reciboEntity = obtenerVehiculoReciboEntityPorPlaca(placa);
		
		if (reciboEntity != null) {
			
			return new Recibo(reciboEntity.getFechaIngreso(), reciboEntity.getFechaSalida(), VehiculoBuilder.convertirVehiculo(reciboEntity.getVehiculo()), reciboEntity.getValorAPagar());
		}
		return null;
	}
	
	@Override
	public void actualizarRecibo(Recibo recibo) {

		ReciboEntity reciboEntity = obtenerVehiculoReciboEntityPorPlaca(recibo.getVehiculo().getPlaca());
		if (reciboEntity != null) {
			reciboEntity.setFechaSalida(recibo.getFechaSalida());
			reciboEntity.setValorAPagar(recibo.getValorAPagar());
		}
	}
	
	@Override
	public List<ListaRecibo> obtenerListaParqueados() {
		
		List<ReciboEntity> listaEntity = listarParqueados();
		List<ListaRecibo> listaParqueados = new ArrayList<>();
		
		if(listaEntity != null){
			
			ListaRecibo listaRecibo;
			String tipo;
			for (int i = 0; i < listaEntity.size(); ++i) {
				Recibo recibo = ReciboBuilder.convertirADominio(listaEntity.get(i));

				if(recibo.getVehiculo() instanceof Moto){
					tipo = "Moto";
				}
				
				else{
					tipo="Carro";
				}
				listaRecibo = new ListaRecibo(recibo.getVehiculo().getPlaca(), tipo, recibo.getFechaIngreso());
				listaParqueados.add(listaRecibo);
			}
			return listaParqueados;
		}
		
		return listaParqueados;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<ReciboEntity> listarParqueados() {
		
		Query query = entityManager.createNamedQuery(PARQUEADOS_FIND);
		List<ReciboEntity> resultList = query.getResultList(); 
		return !resultList.isEmpty() ? resultList : null;
	}
}
