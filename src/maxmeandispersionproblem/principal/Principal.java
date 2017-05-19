/**
 * Clase para la implementación del método principal.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.principal;

import java.io.IOException;

public class Principal {
	
	// DECLARACIÓN DE CONSTANTES.
	final static int ALGORITMO_CONSTRUCTIVO_VORAZ = 0;
	final static int ALGORITMO_DESTRUCTIVO_VORAZ = 1;
	final static int ALGORITMO_GRASP = 2;
	final static int ALGORITMO_MULTIARRANQUE = 3;
	final static int ALGORITMO_VNS = 4;
	final static int ALGORITMO_HIBRIDO = 5;
	
	public static void main(String[] args) throws IOException {
		MaxMeanDispersion problema = new MaxMeanDispersion(args[0]);
		problema.seleccionarAlgoritmo(ALGORITMO_HIBRIDO);
	}
}
