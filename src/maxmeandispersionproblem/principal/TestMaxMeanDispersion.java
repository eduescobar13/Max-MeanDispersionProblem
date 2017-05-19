package maxmeandispersionproblem.principal;
/**
 * Clase para la implementación de test para MaxMeanDispersionProblem.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestMaxMeanDispersion {
	
	// DECLARACIÓN DE CONSTANTES.
	final static double VALOR_TEORICO = 3.763333333;

	// DECLARACIÓN DE ATRIBUTOS.
	private static MaxMeanDispersion maxMeanDispersionTest;
		
	@BeforeClass
	public static void setUpClass() throws IOException { // Método que se ejecuta antes de los test de la clase para asignar atributos.
		maxMeanDispersionTest = new MaxMeanDispersion("max-mean-div-4.txt");
	}
	
	/**
	 * Test para comprobar la correcta creación del objeto maxMeanDispersionTest.
	 */
	@Test
	public void inicializacion() { 
		assertNotNull(getMaxMeanDispersionTest()); // Comprobamos que el objeto maxMeanDispersionTest se ha creado correctamente.
	}
	
	@Test
	public void comprobarCalcularDispersionMedia() {
		ArrayList<Integer> subconjuntoS = new ArrayList<Integer>(3);
		subconjuntoS.add(new Integer(0));
		subconjuntoS.add(new Integer(1));
		subconjuntoS.add(new Integer(3));
		double dispersionMedia = getMaxMeanDispersionTest().getGrafo().obtenerAfinidad(1, 2);
		System.out.println(dispersionMedia);
		//assertEquals(VALOR_TEORICO, dispersionMedia, 0);
	}

	public static MaxMeanDispersion getMaxMeanDispersionTest() {
		return maxMeanDispersionTest;
	}

	public static void setMaxMeanDispersionTest(MaxMeanDispersion maxMeanDispersionTest) {
		TestMaxMeanDispersion.maxMeanDispersionTest = maxMeanDispersionTest;
	}
}
