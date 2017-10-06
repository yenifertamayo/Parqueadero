package com.parqueadero.parqueadero;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dominio.Vigilante;
import dominio.repositorio.RepositorioPaqueadero;
import reglas.ValidarDisponibilidad;
import reglas.ValidarPlaca;
import reglas.ValidarReglasIngreso;

@Configuration
public class ParqueaderoConfig {
	
	@Bean
	public Vigilante crearVigilante(RepositorioPaqueadero repositorioPaqueadero){
		return new Vigilante(repositorioPaqueadero, agregarReglas(repositorioPaqueadero));
		
	}
	
	public List<ValidarReglasIngreso> agregarReglas(RepositorioPaqueadero repositorioPaqueadero) {
		
		List<ValidarReglasIngreso> reglasParqueadero=new ArrayList<>();
		reglasParqueadero.add(new ValidarPlaca());
		reglasParqueadero.add(new ValidarDisponibilidad(repositorioPaqueadero));
		return reglasParqueadero;
	}

}
