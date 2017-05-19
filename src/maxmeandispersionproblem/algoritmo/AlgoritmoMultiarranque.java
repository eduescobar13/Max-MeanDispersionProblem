package maxmeandispersionproblem.algoritmo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import maxmeandispersionproblem.externo.EscrituraFichero;
import maxmeandispersionproblem.externo.Grafo;

/**
 * Clase para la resolución del problema mediante el Algoritmo del Método Multiarranque.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

public class AlgoritmoMultiarranque extends AlgoritmoResolutivo {
	
	// DECLARACIÓN DE CONSTANTES.
	final static int SOLUCIONES_GRASP = 5;
	final static int CANTIDAD_CANDIDATOS = 3;

	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que realizar el problema.
	 */
	public AlgoritmoMultiarranque(Grafo grafo) {
		super(grafo);
	}

	@Override
	public ArrayList<Integer> resolverProblema() {
		getTemporizador().start();
		AlgoritmoGRASP algoritmoGRASP = new AlgoritmoGRASP(getGrafo());
		ArrayList<Integer> solucionActual = new ArrayList<Integer>(algoritmoGRASP.resolverProblema());
		ArrayList<Integer> mejorSolucion = new ArrayList<Integer>(solucionActual);
		for (int i = 0; i < SOLUCIONES_GRASP; i++) {
			realizarBusquedaLocal(solucionActual);
			if (calcularDispersionMedia(solucionActual) > calcularDispersionMedia(mejorSolucion)) {
				mejorSolucion = new ArrayList<Integer>(solucionActual);
			}
			solucionActual = new ArrayList<Integer>(algoritmoGRASP.resolverProblema());
		}
		getTemporizador().stop();
		return mejorSolucion;
	}
	
	/**
	 * Método para realizar una búsqueda local en la solución.
	 * @param subconjuntoActual. Subconjunto actual con los vértices de la solución.
	 */
	public void realizarBusquedaLocal(ArrayList<Integer> subconjuntoActual) {
		ArrayList<Integer> subconjuntoActualAuxiliar = null;
		do {
			Integer candidatoMaximizar = obtenerVerticeCandidatoMaximizar(subconjuntoActual);
			Integer verticeMenorAfinidad = obtenerVerticeMenorAfinidad(subconjuntoActual);
			subconjuntoActualAuxiliar = new ArrayList<Integer>(subconjuntoActual);
			subconjuntoActualAuxiliar.remove(verticeMenorAfinidad);
			subconjuntoActualAuxiliar.add(candidatoMaximizar);
			if (calcularDispersionMedia(subconjuntoActualAuxiliar) > calcularDispersionMedia(subconjuntoActual)) {
				subconjuntoActual.remove(verticeMenorAfinidad);
				subconjuntoActual.add(candidatoMaximizar);
			}
		} while (subconjuntoActualAuxiliar.equals(subconjuntoActual));
	}
	
	/**
	 * Función que obtiene el vértice que aporta menor afinidad al subconjuntoActual.
	 * @param subconjuntoActual. Subconjunto actual con los vértices de la solución.
	 * @return Vértice que aporta menor afinidad.
	 */
	public Integer obtenerVerticeMenorAfinidad(ArrayList<Integer> subconjuntoActual) {
		Integer verticeMenorAfinidad = null;
		int contadorVertices = 0, indiceVerticeMenor = 0, vertice;
		double sumaAfinidades = 0, afinidadMenor = AFINIDAD_MAYOR_POR_DEFECTO, afinidad = 0;
		for (int i = 0;  i < subconjuntoActual.size(); i++) {
			vertice = subconjuntoActual.get(i);
			while (contadorVertices < subconjuntoActual.size()) {
				afinidad = getGrafo().obtenerAfinidad(vertice, subconjuntoActual.get(contadorVertices));
				sumaAfinidades += afinidad;
				contadorVertices++;
			}
			if (sumaAfinidades < afinidadMenor) {
				afinidadMenor = sumaAfinidades;
				indiceVerticeMenor = vertice;
			}
			sumaAfinidades = 0;
			contadorVertices = 0;
		}
		verticeMenorAfinidad = new Integer(indiceVerticeMenor);
		return verticeMenorAfinidad;
	}
	
	/**
	 * Método que obtiene el vértice candidato a maximizar la dispersión media del subconjunto actual.
	 * @param subconjuntoActual. Subconjunto S actual.
	 * @return Vértice candidato a maximizar.
	 */
	public Integer obtenerVerticeCandidatoMaximizar(ArrayList<Integer> subconjuntoActual) {
		double sumaAfinidades = 0, afinidad = 0, sumaAfinidadesMayor = AFINIDAD_MENOR_POR_DEFECTO;
		int verticeCandidato = 0, contadorVertices = 0, verticeCandidatoMayor = 0;
		ArrayList<Integer> verticesCandidatos = new ArrayList<Integer>(CANTIDAD_CANDIDATOS);
		for (int j = 0; j < CANTIDAD_CANDIDATOS; j++) {
			for (int i = 0; i < getGrafo().getNumeroVertices(); i++) {
				if ((!subconjuntoActual.contains(i)) && (!verticesCandidatos.contains(i))) {
					verticeCandidato = i;
					while (contadorVertices < subconjuntoActual.size()) {
						afinidad = getGrafo().getMatrizAfinidades().get(verticeCandidato).get(subconjuntoActual.get(contadorVertices));
						sumaAfinidades += afinidad;
						contadorVertices++;
					}
					if (sumaAfinidades > sumaAfinidadesMayor) {
						sumaAfinidadesMayor = sumaAfinidades;
						verticeCandidatoMayor = verticeCandidato;
					}
					sumaAfinidades = 0;
					contadorVertices = 0;
				}
			}
			sumaAfinidadesMayor = AFINIDAD_MENOR_POR_DEFECTO;
			if (!verticesCandidatos.contains(new Integer(verticeCandidatoMayor))) {
				verticesCandidatos.add(new Integer(verticeCandidatoMayor));
			}
		}
		Random random = new Random(); 
		int indiceAleatorio = (int)(random.nextDouble() * verticesCandidatos.size());
		return new Integer(verticesCandidatos.get(indiceAleatorio));
	}

	/**
	 * Método que muestra la solución bajo formato por consola. Sobreescribe de la clase padre.
	 * @param subconjuntoS. Subconjunto que contiene la solución.
	 */
	@Override
	public void mostrarSolucion(ArrayList<Integer> subconjuntoS) {
		double afinidadTotal = 0;
		System.out.println("------------ ALGORITMO MULTIARRANQUE ------------");
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
		ficheroSalida.escribirLineaFichero("------------ ALGORITMO MULTIARRANQUE ------------");
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL GRAFO INICIAL: " + getGrafo().getAfinidadTotal());
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("VÉRTICES QUE FORMAN EL SUBCONJUNTO: " + subconjuntoS);
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS) * subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
		ficheroSalida.escribirLineaFichero("TIEMPO DE EJECUCIÓN: " + getTemporizador().getTiempoTranscurrido() * 0.000001 + "ms");
	}
}
