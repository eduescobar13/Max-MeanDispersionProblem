/**
 * Clase para la resolución del problema mediante el Algoritmo de Búsqueda por Entorno Variable.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import maxmeandispersionproblem.externo.EscrituraFichero;
import maxmeandispersionproblem.externo.Grafo;

public class AlgoritmoVNS extends AlgoritmoResolutivo {
	
	// DECLARACIÓN DE CONSTANTES.
	final static int VECINOS_INTERCAMBIAR_INICIAL = 1;
		
	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que obtener una solución.
	 */
	public AlgoritmoVNS(Grafo grafo) {
		super(grafo);
	}

	@Override
	public ArrayList<Integer> resolverProblema() {
		getTemporizador().start();
		int vecinosIntercambiar = VECINOS_INTERCAMBIAR_INICIAL;
		ArrayList<Integer> subconjuntoS = new ArrayList<Integer>(obtenerSolucionAleatoria());
		ArrayList<Integer> subconjuntoSAuxiliar = new ArrayList<Integer>(subconjuntoS);
		do {
			if (calcularDispersionMedia(subconjuntoSAuxiliar) > calcularDispersionMedia(subconjuntoS)) {
				subconjuntoS = new ArrayList<Integer>(subconjuntoSAuxiliar);
			}
			else {
				subconjuntoSAuxiliar = new ArrayList<Integer>(subconjuntoS);
				realizarBusquedaEntornoVariable(subconjuntoSAuxiliar, vecinosIntercambiar);
				vecinosIntercambiar++;
			}
		} while ((!subconjuntoS.equals(subconjuntoSAuxiliar)) && (vecinosIntercambiar < subconjuntoS.size()));
		getTemporizador().stop();
		return subconjuntoS;
	}

	/**
	 * Función que obtiene una solución aleatoria de tamaño aleatorio.
	 * @return Solucion aleatoria.
	 */
	public ArrayList<Integer> obtenerSolucionAleatoria() {
		Random random = new Random(); 
		int cantidadVerticesSolucion = (int)(random.nextDouble() * getGrafo().getNumeroVertices());
		ArrayList<Integer> solucionAleatoria = new ArrayList<Integer>();
		int indiceAleatorio = 0;
		random = new Random();
		while (solucionAleatoria.size() != cantidadVerticesSolucion) {
			indiceAleatorio = (int)(random.nextDouble() * getGrafo().getNumeroVertices());
			if (!solucionAleatoria.contains(new Integer(indiceAleatorio))) {
				solucionAleatoria.add(new Integer(indiceAleatorio));
			}
		}
		return solucionAleatoria;
	}
	
	/**
	 * Método para realizar una búsqueda por entorno variable sobre nuestra solución aleatoria.
	 * @param subconjuntoActual. Sunconjunto solución actual.
	 * @param verticesIntercambiar. Cantidad de vértices a intercambiar.
	 */
	public void realizarBusquedaEntornoVariable(ArrayList<Integer> subconjuntoActual, int verticesIntercambiar) {
		int indiceQuitar = 0;
		ArrayList<Integer> indicesVerticesAnadir = new ArrayList<Integer>();
		ArrayList<Integer> indicesVerticesQuitar = new ArrayList<Integer>();
		Random random;
		random = new Random(); 
		while (indicesVerticesAnadir.size() != verticesIntercambiar) {
			int indiceAleatorio = (int)(random.nextDouble() * getGrafo().getNumeroVertices());
			if ((!indicesVerticesAnadir.contains(indiceAleatorio)) && (!subconjuntoActual.contains(indiceAleatorio))) {
				indicesVerticesAnadir.add(indiceAleatorio);
			}
		}
		random = new Random(); 
		while (indicesVerticesQuitar.size() != verticesIntercambiar) {
			int indiceAleatorio = (int)(random.nextDouble() * getGrafo().getNumeroVertices());
			if ((!indicesVerticesQuitar.contains(indiceAleatorio)) && (subconjuntoActual.contains(indiceAleatorio))) {
				indicesVerticesQuitar.add(indiceAleatorio);
			}
		}
		for (int i = 0; i < verticesIntercambiar; i++) {
			indiceQuitar = subconjuntoActual.indexOf(indicesVerticesQuitar.get(i));
			subconjuntoActual.remove(indiceQuitar);
			subconjuntoActual.add(indiceQuitar, indicesVerticesAnadir.get(i));
		}
	}
	
	/**
	 * Método que muestra la solución bajo formato por consola. Sobreescribe de la clase padre.
	 * @param subconjuntoS. Subconjunto que contiene la solución.
	 */
	@Override
	public void mostrarSolucion(ArrayList<Integer> subconjuntoS) {
		double afinidadTotal = 0;
		System.out.println("------------ ALGORITMO VNS ------------");
		System.out.println("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		System.out.println("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		System.out.println("AFINIDAD DEL GRAFO INICIAL: " + getGrafo().getAfinidadTotal());
		System.out.println("AFINIDAD DEL SUBCONJUNTO: " + afinidadTotal);
		System.out.println("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
	} 
	
	/**
	 * Método para mostrar la solución bajo formato en un fichero. Sobreescribe de la clase padre.
	 */
	@Override
	public void volcarSolucionFichero(ArrayList<Integer> subconjuntoS, EscrituraFichero ficheroSalida) {
		Collections.sort(subconjuntoS);
		ficheroSalida.escribirLineaFichero("------------ ALGORITMO VNS ------------");
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL GRAFO INICIAL: " + getGrafo().getAfinidadTotal());
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("VÉRTICES QUE FORMAN EL SUBCONJUNTO: " + subconjuntoS);
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS) * subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
		ficheroSalida.escribirLineaFichero("TIEMPO DE EJECUCIÓN: " + getTemporizador().getTiempoTranscurrido() * 0.000001 + "ms");
	}
}
