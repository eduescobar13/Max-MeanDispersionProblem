/**
 * Clase para la implementación de un grafo completo.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.externo;

import java.util.Vector;

public class Grafo {
	
	// DECLARACIÓN DE ATRIBUTOS.
	private int numeroVertices;
	private Vector<Vector<Double>> matrizAfinidades;
	private double afinidadTotal;
	
	/**
	 * Constructor.
	 * @param numeroVertices. Número de vértices del grafo.
	 */
	public Grafo(int numeroVertices) {
		this.numeroVertices = numeroVertices;
		matrizAfinidades = new Vector<Vector<Double>>(numeroVertices);
		for (int i = 0; i < numeroVertices; i++) {
			matrizAfinidades.add(i, new Vector<Double>());
			matrizAfinidades.get(i).setSize(numeroVertices);
			for (int j = 0; j < numeroVertices; j++) {
				matrizAfinidades.get(i).add(j, new Double(0));
			}
		}
		afinidadTotal = 0;
	}
	
	/**
	 * Método para insertar valores en la matriz del afinidades.
	 * @param verticeInicial. Vértice inicial de la arista.
	 * @param verticeFinal. Vértice final de la arista.
	 * @param afinidad. Afinidad a insertar.
	 */
	public void insertarAfinidad(int verticeInicial, int verticeFinal, double afinidad) {
		getMatrizAfinidades().get(verticeInicial - 1).add(verticeFinal - 1, new Double(afinidad));
	}
	
	/**
	 * Función que retorna la afinidad de una arista determinada.
	 * @param verticeInicial. Vértice inicial de la arista.
	 * @param verticeFinal. Vértice final de la arista.
	 * @return Valor de la afinidad de la arista.
	 */
	public double obtenerAfinidad(int verticeInicial, int verticeFinal) {
		return getMatrizAfinidades().get(verticeInicial).get(verticeFinal).doubleValue();
	}

	public int getNumeroVertices() {
		return numeroVertices;
	}

	public void setNumeroVertices(int numeroVertices) {
		this.numeroVertices = numeroVertices;
	}

	public Vector<Vector<Double>> getMatrizAfinidades() {
		return matrizAfinidades;
	}

	public void setMatrizAfinidades(Vector<Vector<Double>> matrizAfinidades) {
		this.matrizAfinidades = matrizAfinidades;
	}

	public double getAfinidadTotal() {
		return afinidadTotal;
	}

	public void setAfinidadTotal(double afinidadTotal) {
		this.afinidadTotal = afinidadTotal;
	}
}
