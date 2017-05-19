/**
 * Clase abstracta para la herencia de algoritmos resolutivos.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.algoritmo;

import java.util.ArrayList;
import maxmeandispersionproblem.externo.EscrituraFichero;
import maxmeandispersionproblem.externo.Grafo;
import maxmeandispersionproblem.externo.Temporizador;

public abstract class AlgoritmoResolutivo {
	
	// DECLARACIÓN CONSTANTES.
	final static double AFINIDAD_MAYOR_POR_DEFECTO = Double.POSITIVE_INFINITY;
	final static double AFINIDAD_MENOR_POR_DEFECTO = Double.NEGATIVE_INFINITY;
	final static double AFINIDAD_CERO = 0;

	// DECLARACIÓN DE ATRIBUTOS.
	protected Grafo grafo;
	protected Temporizador temporizador;
	
	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que aplicar el algoritmo.
	 */
	public AlgoritmoResolutivo(Grafo grafo) {
		this.grafo = grafo;
		temporizador = new Temporizador();
	}
	
	public abstract ArrayList<Integer> resolverProblema();
	public abstract void mostrarSolucion(ArrayList<Integer> subconjuntoS);
	public abstract void volcarSolucionFichero(ArrayList<Integer> subconjuntoS, EscrituraFichero ficheroSalida);
	
	/**
	 * Función que devuelve el valor de la dispersion media de un subconjunto.
	 * @param subconjunto. Subconjunto para el que se calcula la dispersion media.
	 * @return Valor de dispersión media.
	 */
	public double calcularDispersionMedia(ArrayList<Integer> subconjunto) {
		double sumatorioAfinidades = 0;
		double dispersionMedia = 0;
		int contadorVertice = 0, vertice = 0;
		while (contadorVertice < subconjunto.size()) {
			vertice = subconjunto.get(contadorVertice);
			for (int i = 0; i < subconjunto.size(); i++) {
				if (vertice < subconjunto.get(i)) {
					sumatorioAfinidades += getGrafo().obtenerAfinidad(vertice, subconjunto.get(i));
				}
			}
			contadorVertice++;
		}
		dispersionMedia = sumatorioAfinidades / (double)(subconjunto.size());
		return dispersionMedia;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}

	public Temporizador getTemporizador() {
		return temporizador;
	}

	public void setTemporizador(Temporizador temporizador) {
		this.temporizador = temporizador;
	}
}
