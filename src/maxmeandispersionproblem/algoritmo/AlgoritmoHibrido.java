/**
 * Clase para la resolución del problema mediante el Algoritmo GRASP.
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

public class AlgoritmoHibrido extends AlgoritmoResolutivo {
	
	// DECLARACIÓN DE CONSTANTES.
	final static int CANTIDAD_CANDIDATOS = 2;

	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que realizar el problema.
	 */
	public AlgoritmoHibrido(Grafo grafo) {
		super(grafo);
	}

	@Override
	public ArrayList<Integer> resolverProblema() {
		int i, k = 0, valorAleatorio;
		Random random = new Random(); 
		ArrayList<ArrayList<Integer>> N = new ArrayList<ArrayList<Integer>>(4);
		ArrayList<Integer> S1 = null, S2 = null, Sloc = null;
		ArrayList<Integer> mejorSolucion = new ArrayList<Integer>();
		insertarTodosVertices(mejorSolucion);
		ArrayList<Integer> subconjuntoS = new ArrayList<Integer>(obtenerSolucionGRASP());
		do {
			i = 0;
			ArrayList<Integer> N1 = new ArrayList<Integer>(subconjuntoS);
			eliminarVerticeAleatorio(N1);
			ArrayList<Integer> N2 = new ArrayList<Integer>(subconjuntoS);
			anadirVerticeAleatorio(N2);
			ArrayList<Integer> N3 = new ArrayList<Integer>(subconjuntoS);
			intercambiarVerticeAleatorio(N3);
			N.add(N1);
			N.add(N2);
			N.add(N3);
			valorAleatorio = (int)(random.nextDouble() * 3);
			S2 = new ArrayList<Integer>(N.get(valorAleatorio));
			do {
				valorAleatorio = (int)(random.nextDouble() * 3);
				S1 = new ArrayList<Integer>(N.get(valorAleatorio));
				Sloc = new ArrayList<Integer>(S1);
				realizarBusquedaLocal(Sloc);
				if (calcularDispersionMedia(Sloc) > calcularDispersionMedia(S2)) {
					S2 = new ArrayList<Integer>(Sloc);
				}
				i++;
			} while (i != 3);
			if (calcularDispersionMedia(S2) > calcularDispersionMedia(subconjuntoS)) {
				subconjuntoS = new ArrayList<Integer>(S2);
				k = 0;
			}
			else {
				k++;
			}	
		} while (k != 3);
		if (calcularDispersionMedia(subconjuntoS) > calcularDispersionMedia(mejorSolucion)) {
			mejorSolucion = new ArrayList<Integer>(subconjuntoS);
		}
		return mejorSolucion;
	}
	
	/**
	 * Función usada para aislar el uso de la búsqueda local, devolviendo la solución sin aplicar dicho proceso.
	 * @return Solución sin realización de búsqueda local.
	 */
	public ArrayList<Integer> obtenerSolucionGRASP() {
		getTemporizador().start();
		Integer verticeCandidatoMaximizar;
		ArrayList<Integer> subconjuntoS = new ArrayList<Integer>();
		ArrayList<Integer> subconjuntoSAuxiliar = new ArrayList<Integer>();
		ArrayList<Integer> subconjuntoSCandidato = new ArrayList<Integer>();
		insertarAristaMayorAfinidad(subconjuntoS);
		do {
			subconjuntoSAuxiliar = new ArrayList<Integer>(subconjuntoS);
			subconjuntoSCandidato = new ArrayList<Integer>(subconjuntoS);
			verticeCandidatoMaximizar = obtenerVerticeCandidatoMaximizar(subconjuntoS);
			subconjuntoSCandidato.add(verticeCandidatoMaximizar);
			if (calcularDispersionMedia(subconjuntoSCandidato) > calcularDispersionMedia(subconjuntoS)) {
				subconjuntoS.add(verticeCandidatoMaximizar);
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
		double afinidadTotal = 0;
		System.out.println("------------ ALGORITMO GRASP ------------");
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
		ficheroSalida.escribirLineaFichero("------------ ALGORITMO HÍBRIDO ------------");
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL GRAFO INICIAL: " + getGrafo().getNumeroVertices());
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL GRAFO INICIAL: " + getGrafo().getAfinidadTotal());
		ficheroSalida.escribirLineaFichero("NÚMERO DE VÉRTICES DEL SUBCONJUNTO: " + subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("VÉRTICES QUE FORMAN EL SUBCONJUNTO: " + subconjuntoS);
		ficheroSalida.escribirLineaFichero("AFINIDAD DEL SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS) * subconjuntoS.size());
		ficheroSalida.escribirLineaFichero("DISPERSIÓN MEDIA SUBCONJUNTO: " + calcularDispersionMedia(subconjuntoS));
		ficheroSalida.escribirLineaFichero("TIEMPO DE EJECUCIÓN: " + getTemporizador().getTiempoTranscurrido() * 0.000001 + "ms");
	}
	
	/**
	 * Función que obtiene la arista con mayor afinidad;
	 * @param subconjuntoActual. Subconjunto S actual.
	 */
	public void insertarAristaMayorAfinidad(ArrayList<Integer> subconjuntoActual) {
		int indiceVerticeInicial = 0, indiceVerticeFinal = 0;
		double afinidadMayor = AFINIDAD_MENOR_POR_DEFECTO;
		for(int i = 0; i < getGrafo().getNumeroVertices(); i++) {
			for (int j = 0; j < getGrafo().getNumeroVertices(); j++) {
				if (getGrafo().obtenerAfinidad(i, j) > afinidadMayor) {
					afinidadMayor = getGrafo().obtenerAfinidad(i, j);
					indiceVerticeInicial = i;
					indiceVerticeFinal = j;
				}
			}
		}
		subconjuntoActual.add(new Integer(indiceVerticeInicial));
		subconjuntoActual.add(new Integer(indiceVerticeFinal));
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
	 * Método que inserta en el subconjunto S todos los vertices del grafo.
	 * @param subconjuntoS. Subconjunto S donde insertar los vértices.
	 */
	public void insertarTodosVertices(ArrayList<Integer> subconjuntoS) {
		for (int i = 0; i < getGrafo().getNumeroVertices(); i++) {
			subconjuntoS.add(new Integer(i));
		}
	}
	
	public int eliminarVerticeAleatorio(ArrayList<Integer> subconjunto) {
		Random random = new Random(); 
		int indiceAleatorio = (int)(random.nextDouble() * subconjunto.size());
		subconjunto.remove(indiceAleatorio);
		return indiceAleatorio;
	}
	
	public void anadirVerticeAleatorio(ArrayList<Integer> subconjunto) {
		Random random = new Random(); 
		int valorAleatorio = (int)(random.nextDouble() * subconjunto.size());
		while (subconjunto.contains(new Integer(valorAleatorio))) {
			valorAleatorio = (int)(random.nextDouble() * subconjunto.size());
		}
		subconjunto.add(new Integer(valorAleatorio));
	}
	
	public void intercambiarVerticeAleatorio(ArrayList<Integer> subconjunto) {
		int posicionBorrada = eliminarVerticeAleatorio(subconjunto);
		Random random = new Random(); 
		int valorAleatorio = (int)(random.nextDouble() * subconjunto.size());
		while (subconjunto.contains(new Integer(valorAleatorio))) {
			valorAleatorio = (int)(random.nextDouble() * subconjunto.size());
		}
		subconjunto.add(posicionBorrada, new Integer(valorAleatorio));
	}
}
