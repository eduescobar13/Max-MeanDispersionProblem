/**
 * Clase para la resolución del problema mediante el Algoritmo Destructivo Voraz.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.algoritmo;

import java.util.ArrayList;
import java.util.Collections;

import maxmeandispersionproblem.externo.EscrituraFichero;
import maxmeandispersionproblem.externo.Grafo;

public class AlgoritmoDestructivoVoraz extends AlgoritmoResolutivo {

	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que aplicar el algoritmo.
	 */
	public AlgoritmoDestructivoVoraz(Grafo grafo) {
		super(grafo);
	}

	@Override
	public ArrayList<Integer> resolverProblema() {
		getTemporizador().start();
		Integer verticeCandidatoMaximizar = null;
		ArrayList<Integer> subconjuntoS = new ArrayList<Integer>();
		ArrayList<Integer> subconjuntoSAuxiliar = new ArrayList<Integer>();
		ArrayList<Integer> subconjuntoSCandidato = new ArrayList<Integer>();
		insertarTodosVertices(subconjuntoS);
		do {
			subconjuntoSAuxiliar = new ArrayList<Integer>(subconjuntoS);
			subconjuntoSCandidato = new ArrayList<Integer>(subconjuntoS);
			verticeCandidatoMaximizar = obtenerVerticeCandidatoMaximizar(subconjuntoS);
			subconjuntoSCandidato.remove(verticeCandidatoMaximizar);
			if (calcularDispersionMedia(subconjuntoSCandidato) > calcularDispersionMedia(subconjuntoS)) {
				subconjuntoS.remove(verticeCandidatoMaximizar);
			}
		} while(!subconjuntoSAuxiliar.equals(subconjuntoS));
		getTemporizador().stop();
		return subconjuntoS;
	}

	/**
	 * Método que muestra la solución bajo formato por consola. Sobreescribe de la clase padre.
	 * @param subconjuntoS. Subconjunto que contiene la solución.
	 */
	@Override
	public void mostrarSolucion(ArrayList<Integer> subconjuntoS) {
		System.out.println("--------- ALGORITMO DESTRUCTIVO VORAZ ---------");
		System.out.println("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		System.out.println("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		System.out.println("VÉRTICES QUE FORMAN EL SUBCONJUNTO: " + subconjuntoS);
		System.out.println("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
	} 
	
	/**
	 * Método para mostrar la solución bajo formato en un fichero. Sobreescribe de la clase padre.
	 */
	@Override
	public void volcarSolucionFichero(ArrayList<Integer> subconjuntoS, EscrituraFichero ficheroSalida) {
		Collections.sort(subconjuntoS);
		ficheroSalida.escribirLineaFichero("--------- ALGORITMO DESTRUCTIVO VORAZ ---------");
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL GRAFO INICIAL: " + getGrafo().getAfinidadTotal());
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("VÉRTICES QUE FORMAN EL SUBCONJUNTO: " + subconjuntoS);
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS) * subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
		ficheroSalida.escribirLineaFichero("TIEMPO DE EJECUCIÓN: " + getTemporizador().getTiempoTranscurrido() * 0.000001 + "ms");
	}
	
	/**
	 * Método que obtiene el vértice candidato a maximizar la dispersión media del subconjunto actual.
	 * @param subconjuntoActual. Subconjunto S actual.
	 * @return Vértice candidato a maximizar.
	 */
	public Integer obtenerVerticeCandidatoMaximizar(ArrayList<Integer> subconjuntoActual) {
		double sumaAfinidades = 0, afinidad = 0, sumaAfinidadesMenor = AFINIDAD_MAYOR_POR_DEFECTO;
		int verticeCandidato = 0, contadorVertices = 0, verticeCandidatoMenor = 0;
		for (int i = 0; i < getGrafo().getNumeroVertices(); i++) {
			if (subconjuntoActual.contains(i)) {
				verticeCandidato = i;
				while (contadorVertices < subconjuntoActual.size()) {
					afinidad = getGrafo().getMatrizAfinidades().get(verticeCandidato).get(subconjuntoActual.get(contadorVertices));
					sumaAfinidades += afinidad;
					contadorVertices++;
				}
				if (sumaAfinidades < sumaAfinidadesMenor) {
					sumaAfinidadesMenor = sumaAfinidades;
					verticeCandidatoMenor = verticeCandidato;
				}
				sumaAfinidades = 0;
				contadorVertices = 0;
			}
		}
		return new Integer(verticeCandidatoMenor);
	}
	
	/**
	 * Método que inserta en el subconjunto S todos los vertices del grafo.
	 * @param subconjuntoS. Subconjunto S donde insertar los vértices.
	 */
	public void insertarTodosVertices(ArrayList<Integer> subconjuntoS) {
		for (int i = 0; i < getGrafo().getNumeroVertices(); i++) {
			subconjuntoS.add(new Integer(i));
		}
	}

}
