package com.parqueadero.parqueadero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Parqueadero;
import dominio.Vigilante;
import dominio.repositorio.RepositorioRecibo;
import reglas.DisponibilidadVehiculos;
import reglas.PlacaEmpiezaPorA;
import reglas.ReglasIngreso;

@Configuration
public class ParqueaderoConfig {
	
	@Bean
	public Vigilante crearVigilante(RepositorioRecibo repositorioRecibo, Parqueadero parqueadero){
		return new Vigilante(repositorioRecibo, agregarReglas(repositorioRecibo, parqueadero), crearParqueadero());
		
	}
	
	@Bean
	public Parqueadero crearParqueadero() {
		int maximoCarros = 2;
		int maximoMotos = 1;
		return new Parqueadero(maximoCarros, maximoMotos);
	}

	public List<ReglasIngreso> agregarReglas(RepositorioRecibo repositorioRecibo, Parqueadero parqueadero) {
		List<ReglasIngreso> reglasParqueadero=new ArrayList<>();
		reglasParqueadero.add(new PlacaEmpiezaPorA());
		reglasParqueadero.add(new DisponibilidadVehiculos(repositorioRecibo, parqueadero));
		return reglasParqueadero;
	}
	
	
}
