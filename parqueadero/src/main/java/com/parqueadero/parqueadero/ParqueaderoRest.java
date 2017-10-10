package com.parqueadero.parqueadero;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dominio.Carro;
import dominio.ListaRecibo;
import dominio.Recibo;
import dominio.SalidaVehiculo;
import dominio.Moto;
import dominio.Vigilante;
import dominio.repositorio.RepositorioVehiculo;


@RestController
@Transactional
@RequestMapping(value = "/paqueadero")
public class ParqueaderoRest {
	
	@Autowired
	Vigilante vigilante;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;
	
	@RequestMapping(value = "/registro/carro", method = RequestMethod.POST)
	@ResponseBody
	public Recibo servicioRegistrarCarro(@RequestBody Carro carro) {
		Calendar fechaIngreso =  Calendar.getInstance();
		return vigilante.ingresarVehiculo(carro, fechaIngreso);
	}
	
	@RequestMapping(value = "/registro/moto", method = RequestMethod.POST)
	@ResponseBody
	public Recibo servicioRegistrarMoto(@RequestBody Moto moto) {
		Calendar fechaIngreso =  Calendar.getInstance();
		return vigilante.ingresarVehiculo(moto, fechaIngreso);
	}
	
	@RequestMapping(value = "/salida/vehiculo", method = RequestMethod.POST)
	@ResponseBody
	public Recibo servicioRegistrarMoto(@RequestBody SalidaVehiculo salidavehiculo) {
		
		return vigilante.salidaVehiculo(salidavehiculo.getPlaca());
	}
	
	@RequestMapping(value = "/lista/vehiculos", method = RequestMethod.GET)
	@ResponseBody
	
	public List<ListaRecibo> servicioListarLibros() {
		
		return vigilante.listaVehiculos();
	}
	
}
