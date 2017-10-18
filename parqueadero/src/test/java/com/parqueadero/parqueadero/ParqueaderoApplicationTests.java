package com.parqueadero.parqueadero;

import static org.junit.Assert.fail;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;

import dominio.Carro;
import dominio.ListaRecibo;
import dominio.Recibo;
import dominio.Moto;
import dominio.Parqueadero;
import dominio.Vehiculo;
import dominio.Vigilante;
import dominio.excepcion.ParqueaderoException;
import dominio.repositorio.RepositorioRecibo;
import dominio.repositorio.RepositorioVehiculo;
import persistencia.builder.ReciboBuilder;
import persistencia.repositorio.RepositorioReciboPersistente;
import reglas.CilindrajeMoto;
import reglas.DisponibilidadVehiculos;
import reglas.PlacaEmpiezaPorA;
import reglas.ValorAPagarVehiculo;


@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class ParqueaderoApplicationTests {

	@Autowired
	Vigilante vigilante;
	
	@Autowired
	RepositorioRecibo repositorioRecibo;
	
	@Autowired
	RepositorioVehiculo repositorioVehiculo;
	
	@Autowired
	Parqueadero parqueadero;

	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void ingresarCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNull(repositorioRecibo.obtenerVehiculoReciboPorPlaca(vehiculo.getPlaca()));
		Assert.assertEquals(vigilante.ingresarVehiculo(vehiculo, fechaIngreso).getVehiculo(), vehiculo);
	}	

	@Test
	public void ingresarCarroFallaTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 501);
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		repositorioVehiculo.agregar(vehiculo);
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
	
		try {

			vigilante.noEstaParqueado(vehiculo);
			fail();

		} catch (ParqueaderoException e) {

			Assert.assertEquals("El vehiculo ya se encuentra parqueado", e.getMessage());
		}
	}
	
	@Test
	public void salidaCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		repositorioVehiculo.agregar(vehiculo);
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		Assert.assertTrue(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNotNull(vigilante.obtenerRecibo(vehiculo.getPlaca()));
		Assert.assertEquals(vigilante.salidaVehiculo(vehiculo.getPlaca()).getVehiculo().getPlaca(), vehiculo.getPlaca());
	}
	
	
	@Test
	public void salidaMotoTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 501);
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		repositorioVehiculo.agregar(vehiculo);
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		Assert.assertTrue(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNotNull(vigilante.obtenerRecibo(vehiculo.getPlaca()));
		Assert.assertEquals(vigilante.salidaVehiculo(vehiculo.getPlaca()).getVehiculo().getPlaca(), vehiculo.getPlaca());
	}
	
	@Test
	public void listaVehiculosMotoTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 501);
		repositorioVehiculo.agregar(vehiculo);
		Calendar fechaIngreso = Calendar.getInstance();
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		Assert.assertNotNull(vigilante.listaVehiculos());
		Assert.assertNotNull(repositorioRecibo.obtenerListaParqueados());
	}
	
	@Test
	public void listaVehiculosCarroTest(){
		
		Vehiculo vehiculo = new Carro("FIS579");
		repositorioVehiculo.agregar(vehiculo);
		Calendar fechaIngreso = Calendar.getInstance();
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		repositorioRecibo.agregar(recibo);
		ListaRecibo listaRecibo = new ListaRecibo("FIS579", "Carro", fechaIngreso);
		Assert.assertNotNull(vigilante.listaVehiculos());
		Assert.assertNotNull(repositorioRecibo.obtenerListaParqueados());
		Assert.assertEquals(repositorioRecibo.obtenerListaParqueados().get(0).getPlaca(), listaRecibo.getPlaca());
		Assert.assertEquals(repositorioRecibo.obtenerListaParqueados().get(0).getTipo(), listaRecibo.getTipo());
		Assert.assertEquals(repositorioRecibo.obtenerListaParqueados().get(0).getFechaIngreso(), listaRecibo.getFechaIngreso());


	}
	
	
	@Test
	public void listaVehiculosVaciaTest(){
		
		try {

			repositorioRecibo.obtenerListaParqueados();
			fail();

		} catch (ParqueaderoException e) {

			Assert.assertEquals("No hay vehiculos parqueados", e.getMessage());
		}
	}
	
	@Test
	public void salidaCarroFallaTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNull(repositorioRecibo.obtenerVehiculoReciboPorPlaca(vehiculo.getPlaca()));
		
		try {

			vigilante.salidaVehiculo(vehiculo.getPlaca());
			fail();

		} catch (ParqueaderoException e) {

			Assert.assertEquals("El vehiculo no se encuntra parqueado actualmente", e.getMessage());
		}
	}
	
	
	@Test
	public void ingresarMotoTest(){
		
		Vehiculo vehiculo = new Moto("SSJ7", 150);
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, null));
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
		Assert.assertNull(repositorioRecibo.obtenerVehiculoReciboPorPlaca(vehiculo.getPlaca()));
		Assert.assertEquals(vigilante.ingresarVehiculo(vehiculo, fechaIngreso).getVehiculo(), vehiculo);
	}
	
	@Test
	public void ingresarCarroConPlacaATest(){
		
		Vehiculo vehiculo = new Carro("ASD345");
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		
		try {

			placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso);
			fail();

		} catch (ParqueaderoException e) {

			Assert.assertEquals("No puede ingresar porque no es un dia habil", e.getMessage());
		}
	}
	
	@Test
	public void ingresarMotoConPlacaATest(){
		
		Vehiculo vehiculo = new Moto("ASD345B", 125);
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		
		try {
			placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso);
			fail();

		} catch (ParqueaderoException e) {
			
			Assert.assertEquals("No puede ingresar porque no es un dia habil", e.getMessage());
		}
	}
	
	@Test
	public void ingresarCarroConPlacaADiaHabilLunesTest(){
	
		Vehiculo vehiculo = new Carro("ASD345");
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabilLunes(fechaIngreso);
		Assert.assertFalse(placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarCarroConPlacaADiaHabilDomingoTest(){
	
		Vehiculo vehiculo = new Carro("ASD345");
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabilDomingo(fechaIngreso);
		Assert.assertFalse(placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void esPosibleIngresoTest(){
		Vehiculo vehiculo = new Carro("BSD345");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		Assert.assertTrue(vigilante.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void noEsPosibleIngresoTest(){
		Vehiculo vehiculo = new Carro("ASD345");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		try {
			vigilante.esPosibleIngreso(vehiculo, fechaIngreso);
			fail();

		} catch (ParqueaderoException e) {
			
			Assert.assertEquals("No puede ingresar porque no es un dia habil", e.getMessage());
		}
	}

	
	@Test
	public void noEstaParqueadoMotoTest(){
		
		Vehiculo vehiculo = new Moto("SSD345B", 125);
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
	}
	
	@Test
	public void noEstaParqueadoCarroTest(){
		
		Vehiculo vehiculo = new Carro("SSD345B");
		Assert.assertFalse(vigilante.estaParqueado(vehiculo.getPlaca()));
	}

	@Test
	public void ingresarMotoConPlacaADiaHabilTest(){
		
		Vehiculo vehiculo = new Moto("ASD345", 200);
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabilLunes(fechaIngreso);
		Assert.assertFalse(placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarCarroSinPlacaATest(){
		
		Vehiculo vehiculo = new Carro("DAB346");
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaHabilLunes(fechaIngreso);
		Assert.assertFalse(placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	@Test
	public void ingresarMotoSinPlacaATest(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 150);
		PlacaEmpiezaPorA placaEmpiezaPorA = new PlacaEmpiezaPorA();
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaDiferenteLunesDomingo(fechaIngreso);
		Assert.assertFalse(placaEmpiezaPorA.esPosibleIngreso(vehiculo, fechaIngreso));
	}
	
	
	@Test
	public void carroCilindrajeAlto(){
		
		Vehiculo vehiculo = new Carro("FJM36A");
		Recibo recibo = new Recibo(Calendar.getInstance(), null, vehiculo, 0);
		CilindrajeMoto cilindrajeMoto = new CilindrajeMoto(parqueadero);
		Assert.assertEquals(0.0, cilindrajeMoto.valorAPagar(recibo), 2000.0);
	}
	
	@Test
	public void motoConCilindrajeAlto(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 550);
		Recibo recibo = new Recibo(Calendar.getInstance(), null, vehiculo, 0);
		CilindrajeMoto cilindrajeMoto = new CilindrajeMoto(parqueadero);
		Assert.assertEquals(2000, cilindrajeMoto.valorAPagar(recibo), 2000.0);
	}
	
	@Test
	public void motoSinCilindrajeAlto(){
		
		Vehiculo vehiculo = new Moto("FJM36A", 150);
		Recibo recibo = new Recibo(Calendar.getInstance(), null, vehiculo, 0);
		CilindrajeMoto cilindrajeMoto = new CilindrajeMoto(parqueadero);
		Assert.assertEquals(cilindrajeMoto.valorAPagar(recibo), 0.0, 0.0);
	}
		
	
	@Test
	public void disponibilidaMotoTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 125);
		DisponibilidadVehiculos disponibilidadVehiculos = new DisponibilidadVehiculos(repositorioRecibo, parqueadero);
		Assert.assertFalse(disponibilidadVehiculos.esPosibleIngreso(vehiculo, null));
	}
	
	@Test
	public void disponibilidaCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		DisponibilidadVehiculos disponibilidadVehiculos = new DisponibilidadVehiculos(repositorioRecibo, parqueadero);
		Assert.assertFalse(disponibilidadVehiculos.esPosibleIngreso(vehiculo, null));
	}
		
	@Test
	public void parqueaderoEntityTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 125);
		Calendar fechaIngreso = Calendar.getInstance();
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		Assert.assertNotNull(ReciboBuilder.convertirAEntity(recibo));
	}
	
	@Test
	public void parqueaderoEntityBuildTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 125);
		Calendar fechaIngreso = Calendar.getInstance();
		Recibo recibo = new Recibo(fechaIngreso, null, vehiculo, 0);
		RepositorioReciboPersistente repositorioReciboPersistente = new RepositorioReciboPersistente(null);
		Assert.assertNotNull(repositorioReciboPersistente.buildRecibo(recibo));
	}
	
	@Test
	public void valorAPagarCarroTest(){
		
		Vehiculo vehiculo = new Carro("BIS579");
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaEntrada(fechaIngreso);
		Calendar fechaSalida = Calendar.getInstance();
		asignarFechaSalida(fechaSalida);
		Recibo recibo = new Recibo(fechaIngreso, fechaSalida, vehiculo, 0);
		ValorAPagarVehiculo valorAPagarVehiculo =  new ValorAPagarVehiculo();
		Assert.assertEquals(valorAPagarVehiculo.valorAPagar(recibo),11000.0, 0.0);
		
	}
	
	@Test
	public void valorAPagarMotoTest(){
		
		Vehiculo vehiculo = new Moto("BIS579", 125);
		Calendar fechaIngreso = Calendar.getInstance();
		asignarFechaEntrada(fechaIngreso);
		Calendar fechaSalida = Calendar.getInstance();
		asignarFechaSalida(fechaSalida);
		Recibo recibo = new Recibo(fechaIngreso, fechaSalida, vehiculo, 0);
		ValorAPagarVehiculo valorAPagarVehiculo =  new ValorAPagarVehiculo();
		Assert.assertEquals(valorAPagarVehiculo.valorAPagar(recibo),2100.0, 0.0);
		
	}

	private void asignarFechaEntrada(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 6);
		fecha.set(Calendar.MINUTE, 00);
		fecha.set(Calendar.MILLISECOND, 00);
		fecha.set(Calendar.SECOND, 00);
		fecha.set(Calendar.ZONE_OFFSET, 00);
		fecha.set(Calendar.DST_OFFSET, 00);
	}
	
	private void asignarFechaSalida(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 18);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.MILLISECOND, 00);
		fecha.set(Calendar.SECOND, 00);
		fecha.set(Calendar.ZONE_OFFSET, 00);
		fecha.set(Calendar.DST_OFFSET, 00);
	}
	
	
	private void asignarFechaDiferenteLunesDomingo(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 13);
		fecha.set(Calendar.MINUTE, 59);
		fecha.set(Calendar.SECOND, 40);
		fecha.set(Calendar.MILLISECOND, 30);
	}
	
	private void asignarFechaHabilLunes(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.MILLISECOND, 10);
		fecha.set(Calendar.SECOND, 0);
	}
	
	private void asignarFechaHabilDomingo(Calendar fecha) {
		
		fecha.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		fecha.set(Calendar.MONTH,  Calendar.OCTOBER);
		fecha.set(Calendar.YEAR, 2017);
		fecha.set(Calendar.HOUR, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.SECOND, 0);
	}
}
