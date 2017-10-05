package persistencia.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "Parqueadero")

@NamedQueries({@NamedQuery(name = "Parqueadero.findByPlaca", query = "SELECT parqueadero from Parqueadero parqueadero where parqueadero.vehiculoEntity.placa = :placa"),
@NamedQuery(name = "Parqueadero.findAll", query = "SELECT parqueadero from Parqueadero parqueadero"),
@NamedQuery(name = "Parqueadero.findVehiculo", query = "SELECT COUNT(*) from Parqueadero parqueadero where parqueadero.vehiculoEntity.tipo = :tipo")})

public class ParqueaderoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ID_VEHICULO")
	private VehiculoEntity vehiculoEntity;
	
	private Calendar fechaIngreso;
	private Calendar fechaSalida;
	private double valorAPagar;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public VehiculoEntity getVehiculo() {
		return getVehiculo();
	}
	public void setVehiculoEntity(VehiculoEntity vehiculoEntity) {
		this.vehiculoEntity = vehiculoEntity;
	}
	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Calendar getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public double getValorAPagar() {
		return valorAPagar;
	}
	public void setValorAPagar(double valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
	
}
