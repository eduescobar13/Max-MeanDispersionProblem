package maxmeandispersionproblem.algoritmo;
/**
 * Clase para la resolución del problema mediante el Algoritmo Constructivo Voraz.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

import java.util.ArrayList;
import java.util.Collections;

import maxmeandispersionproblem.externo.EscrituraFichero;
import maxmeandispersionproblem.externo.Grafo;

public class AlgoritmoConstructivoVoraz extends AlgoritmoResolutivo {

	/**
	 * Constructor.
	 * @param grafo. Grafo sobre el que aplicar el algoritmo.
	 */
	public AlgoritmoConstructivoVoraz(Grafo grafo) {
		super(grafo);
	}

	@Override
	public ArrayList<Integer> resolverProblema() {
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
		System.out.println("--------- ALGORITMO CONSTRUCTIVO VORAZ ---------");
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
		ficheroSalida.escribirLineaFichero("--------- ALGORITMO CONSTRUCTIVO VORAZ ---------");
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
					afinidadMayor =  getGrafo().obtenerAfinidad(i, j);
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
		for (int i = 0; i < getGrafo().getNumeroVertices(); i++) {
			if (!subconjuntoActual.contains(i)) {
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
		return new Integer(verticeCandidatoMayor);
	}
}
