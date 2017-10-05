package com.parqueadero.parqueadero;

import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import dominio.Carro;
import dominio.Parqueadero;
import dominio.Moto;
import dominio.Vehiculo;
import dominio.Vigilante;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioPaqueadero;
import dominio.repositorio.RepositorioVehiculo;
import persistencia.builder.ParqueaderoBuilder;
import reglas.ValidarCilindrajeMoto;
import reglas.ValidarDisponibilidad;
import reglas.ValidarPlaca;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParqueaderoApplicationTests {

	@Autowired
	Vigilante vigilante;
	
	@Autowired
	RepositorioPaqueadero repositorioPaqueadero;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void ingresarCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNull(repositorioPaqueadero.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}
	
	@Test
	public void ingresarMotoTest(){
		
		Vehiculo vehiculo = new Moto("SSJ7", 150);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNull(repositorioPaqueadero.obtenerVehiculoParqueadoPorPlaca(vehiculo.getPlaca()));
	}
	
	@Test
	public void ingresarCarroConPlacaATest(){
		
		Vehiculo vehiculo = new Carro("ASD345");
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaNoHabil(fechaIngreso);
		
		try {

			validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso);
			fail();

		} catch (ParqueaderoException e) {

			Assert.assertEquals("No puede ingresar porque no es un dia habil", e.getMessage());
		}
	}
	
	@Test
	public void ingresarMotoConPlacaATest(){
		
		Vehiculo vehiculo = new Moto("ASD345B", 125);
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaNoHabil(fechaIngreso);
		
		try {
			validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso);
			fail();

		} catch (ParqueaderoException e) {
			
			Assert.assertEquals("No puede ingresar porque no es un dia habil", e.getMessage());
		}
	}
	
	@Test
	public void ingresarCarroConPlacaADiaHabilTest(){
	
		Vehiculo vehiculo = new Carro("ASD345");
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabil(fechaIngreso);
		Assert.assertFalse(validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarMotoConPlacaADiaHabilTest(){
		
		Vehiculo vehiculo = new Moto("ASD345", 200);
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabil(fechaIngreso);
		Assert.assertFalse(validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarCarroSinPlacaATest(){
		
		Vehiculo vehiculo = new Carro("DAB346");
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabil(fechaIngreso);
		Assert.assertFalse(validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarMotoSinPlacaATest(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 150);
		ValidarPlaca validarPlaca = new ValidarPlaca();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabil(fechaIngreso);
		Assert.assertFalse(validarPlaca.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void motoConCilindrajeAlto(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 550);
		Parqueadero parqueadero = new Parqueadero(Calendar.getInstance(), null, vehiculo, 0);
		ValidarCilindrajeMoto validarCilindrajeMoto = new ValidarCilindrajeMoto();
		Assert.assertEquals(validarCilindrajeMoto.valorAPagar(parqueadero).getValorAPagar(), 2000, 0.0);
	}
	
	@Test
	public void motoSinCilindrajeAlto(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 150);
		Parqueadero parqueadero = new Parqueadero(Calendar.getInstance(), null, vehiculo, 0);
		ValidarCilindrajeMoto validarCilindrajeMoto = new ValidarCilindrajeMoto();
		Assert.assertEquals(validarCilindrajeMoto.valorAPagar(parqueadero).getValorAPagar(), 0, 0.0);
	}
	
	@Test
	public void disponibilidaMotoTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 125);
		ValidarDisponibilidad validarDisponibilidad = new ValidarDisponibilidad(repositorioPaqueadero);
		Assert.assertFalse(validarDisponibilidad.esPosibleIngreso(vehiculo, null));
		
	}
	
	@Test
	public void disponibilidaCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		ValidarDisponibilidad validarDisponibilidad = new ValidarDisponibilidad(repositorioPaqueadero);
		Assert.assertFalse(validarDisponibilidad.esPosibleIngreso(vehiculo, null));
	}
	
	private void asignarFechaNoHabil(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.SECOND, 0);
	}
	
	
	private void asignarFechaHabil(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.SECOND, 0);
	}
}
