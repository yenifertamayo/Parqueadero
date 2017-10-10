package com.parqueadero.parqueadero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Parqueadero;
import dominio.Vigilante;
import dominio.repositorio.RepositorioRecibo;
import dominio.repositorio.RepositorioVehiculo;
import reglas.CilindrajeMoto;
import reglas.DisponibilidadVehiculos;
import reglas.PlacaEmpiezaPorA;
import reglas.ReglasIngreso;
import reglas.ReglasSalida;
import reglas.ValorAPagarVehiculo;

@Configuration
public class ParqueaderoConfig {

	@Bean
	public Vigilante crearVigilante(RepositorioRecibo repositorioRecibo, RepositorioVehiculo repositorioVehiculo, Parqueadero parqueadero) {
		return new Vigilante(repositorioRecibo, repositorioVehiculo, agregarReglas(repositorioRecibo, parqueadero), agregraReglasSalida(parqueadero), crearParqueadero());

	}

	@Bean
	public Parqueadero crearParqueadero() {
		
		int maximoCarros = 20;
		int maximoMotos = 10;
		int cilindrajeMaximo = 500;
		double excedenteCilindrajeAltoMoto = 2000.0;
		return new Parqueadero(maximoCarros, maximoMotos, cilindrajeMaximo, excedenteCilindrajeAltoMoto);
	}

	public List<ReglasIngreso> agregarReglas(RepositorioRecibo repositorioRecibo, Parqueadero parqueadero) {
		
		List<ReglasIngreso> reglasParqueadero = new ArrayList<>();
		reglasParqueadero.add(new PlacaEmpiezaPorA());
		reglasParqueadero.add(new DisponibilidadVehiculos(repositorioRecibo, parqueadero));
		return reglasParqueadero;
	}
	
	private List<ReglasSalida> agregraReglasSalida(Parqueadero parqueadero) {
		
		 List<ReglasSalida> reglasSalidas = new ArrayList<>();
		 reglasSalidas.add(new CilindrajeMoto(parqueadero));
		 reglasSalidas.add(new ValorAPagarVehiculo());
		 return reglasSalidas;
	}

}
