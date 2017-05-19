/**
 * Clase para la implementación del Max-mean Dispersion Problem.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.principal;

import maxmeandispersionproblem.externo.*;
import maxmeandispersionproblem.algoritmo.*;
import java.io.IOException;

public class MaxMeanDispersion {
	
	// DECLARACIÓN DE CONSTANTES.
	final static int ALGORITMO_CONSTRUCTIVO_VORAZ = 0;
	final static int ALGORITMO_DESTRUCTIVO_VORAZ = 1;
	final static int ALGORITMO_GRASP = 2;
	final static int ALGORITMO_MULTIARRANQUE = 3;
	final static int ALGORITMO_VNS = 4;
	final static int ALGORITMO_HIBRIDO = 5;
	final static String NOMBRE_FICHERO_SALIDA = "resultados.txt";
	
	// DECLARACIÓN DE ATRIBUTOS.
	private LecturaFichero ficheroEntrada;
	private EscrituraFichero ficheroSalida;
	private Grafo grafo;
	private AlgoritmoResolutivo algoritmo;
	
	/**
	 * Constructor.
	 * @param nombreFicheroEntrada. Nombre del fichero de inicialización del problema.
	 * @throws IOException
	 */
	public MaxMeanDispersion(String nombreFicheroEntrada) throws IOException {
		ficheroEntrada = new LecturaFichero(nombreFicheroEntrada);
		ficheroSalida = new EscrituraFichero(NOMBRE_FICHERO_SALIDA);
		grafo = new Grafo(Integer.parseInt(getFicheroEntrada().leerLineaFichero()));
		inicializarFicheroSalida();
		inicializarGrafo();
	}
	
	/**
	 * Método que inserta una cabecera en el fichero de salida.
	 */
	public void inicializarFicheroSalida() {
		ficheroSalida.escribirLineaFichero("AUTOR: Eduardo Escobar Alberto");
		ficheroSalida.escribirLineaFichero("ASIGNATURA: Diseño y Análisis de Algoritmos. Universidad de La Laguna");
		ficheroSalida.escribirLineaFichero("CONTENIDO: Soluciones para el Max-Mean Dispersion Problem");
		ficheroSalida.escribirLineaFichero("");
	}
	
	/**
	 * Método que inicializa el grafo a partir del fichero de entrada.
	 * @throws IOException
	 */
	public void inicializarGrafo() throws IOException {
		String lineaLeida = null; // Variables para ir almacenando las líneas y tratándolas.
		double costeLeido; // Variable para almacenar el coste leido parseado.
		double costeTotal = 0; // Variable para almacenar el coste total del grafo.
		int contadorVerticeInicial = 1;
		int contadorVerticeFinal = contadorVerticeInicial + 1;
		while ((lineaLeida = getFicheroEntrada().leerLineaFichero()) != null) {
			costeLeido = Double.parseDouble(lineaLeida);
			costeTotal += costeLeido; // El coste total del grafo será la suma de los costes leidos.
			getGrafo().insertarAfinidad(contadorVerticeInicial, contadorVerticeFinal, costeLeido);
			getGrafo().insertarAfinidad(contadorVerticeFinal, contadorVerticeInicial, costeLeido);
			if (contadorVerticeFinal == getGrafo().getNumeroVertices()) {
				contadorVerticeInicial++;
				contadorVerticeFinal = contadorVerticeInicial + 1;
			}
			else {
				contadorVerticeFinal++;
			}
		}
		getGrafo().setAfinidadTotal(costeTotal);
		getFicheroEntrada().cerrarFichero();
	}
	
	/**
	 * Método que resuelve el problema a través del algoritmo selecionado.
	 * @param tipoAlgoritmo. Algoritmo seleccionado para realizar el problema.
	 * @throws IOException
	 */
	public void seleccionarAlgoritmo(int tipoAlgoritmo) throws IOException {
		switch(tipoAlgoritmo) {
			case ALGORITMO_CONSTRUCTIVO_VORAZ:
				setAlgoritmo(new AlgoritmoConstructivoVoraz(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
			case ALGORITMO_DESTRUCTIVO_VORAZ:
				setAlgoritmo(new AlgoritmoDestructivoVoraz(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
			case ALGORITMO_GRASP:
				setAlgoritmo(new AlgoritmoGRASP(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
			case ALGORITMO_MULTIARRANQUE:
				setAlgoritmo(new AlgoritmoMultiarranque(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
			case ALGORITMO_VNS:
				setAlgoritmo(new AlgoritmoVNS(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
			case ALGORITMO_HIBRIDO:
				setAlgoritmo(new AlgoritmoHibrido(getGrafo()));
				getAlgoritmo().volcarSolucionFichero(getAlgoritmo().resolverProblema(), getFicheroSalida());
			break;
		}
		getFicheroSalida().cerrarFichero();
	}

	public LecturaFichero getFicheroEntrada() {
		return ficheroEntrada;
	}

	public void setFicheroEntrada(LecturaFichero ficheroEntrada) {
		this.ficheroEntrada = ficheroEntrada;
	}

	public EscrituraFichero getFicheroSalida() {
		return ficheroSalida;
	}

	public void setFicheroSalida(EscrituraFichero ficheroSalida) {
		this.ficheroSalida = ficheroSalida;
	}

	public Grafo getGrafo() {
		return grafo;
	}

	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}

	public AlgoritmoResolutivo getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(AlgoritmoResolutivo algoritmo) {
		this.algoritmo = algoritmo;
	}
}
