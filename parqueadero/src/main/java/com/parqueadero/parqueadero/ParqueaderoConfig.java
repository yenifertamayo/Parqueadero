package com.parqueadero.parqueadero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Vigilante;
import dominio.repositorio.RepositorioRecibo;
import reglas.DisponibilidadVehiculos;
import reglas.PlacaEmpiezaPorA;
import reglas.ReglasIngreso;

@Configuration
public class ParqueaderoConfig {
	
	@Bean
	public Vigilante crearVigilante(RepositorioRecibo repositorioRecibo){
		return new Vigilante(repositorioRecibo, agregarReglas(repositorioRecibo));
		
	}
	
	public List<ReglasIngreso> agregarReglas(RepositorioRecibo repositorioRecibo) {
		
		List<ReglasIngreso> reglasParqueadero=new ArrayList<>();
		reglasParqueadero.add(new PlacaEmpiezaPorA());
		reglasParqueadero.add(new DisponibilidadVehiculos(repositorioRecibo));
		return reglasParqueadero;
	}
}
