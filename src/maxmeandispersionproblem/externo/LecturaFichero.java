package maxmeandispersionproblem.externo;
/**
 * Clase para la implementación de la lectura de un fichero.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 26/04/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LecturaFichero {

	// DECLARACIÓN DE ATRIBUTOS.
	private FileReader ficheroEntrada;
	private BufferedReader bufferEntrada;
	
	/**
	 	* Constructor por defecto.
		*/
	public LecturaFichero() {
		ficheroEntrada = null;
		bufferEntrada  = null;
	}
	
	/**
			* Constructor.
			* @param nombreFichero Nombre del fichero para realizar la lectura.
			* @throws IOException 
	*/
	public LecturaFichero(String nombreFichero) throws IOException {
		ficheroEntrada = new FileReader(nombreFichero);
		bufferEntrada  = new BufferedReader(ficheroEntrada);
	}
	
	/**
		* Función para leer lineas del fichero de entrada.
		* @return Devuelve un String con el contenido de la línea. Si es el final del fichero devuelve null.
		* @throws IOException 
	*/
	public String leerLineaFichero() throws IOException {
		String lineaFichero;
		if ((lineaFichero = getBufferEntrada().readLine()) != null) { // Si lineaFichero no es igual a null.
			return lineaFichero;
		}
		else {
			return null;
		}
	}
	
	/**
	 	* Método para realizar el cierre del fichero.
		* @throws IOException 
	*/
	public void cerrarFichero() throws IOException {
		getFicheroEntrada().close();
	}
	 
	public FileReader getFicheroEntrada() { // Método getter del atributo ficheroEntrada.
		return ficheroEntrada;
	}
	
	public void setFicheroEntrada(FileReader ficheroEntrada) { // Método setter del atributo ficheroEntrada.
		this.ficheroEntrada = ficheroEntrada;
	}
	
	public BufferedReader getBufferEntrada() { // Método getter del atributo bufferEntrada.
		return bufferEntrada;
	}
	
	public void setBufferEntrada(BufferedReader bufferEntrada) { // Método setter del atributo bufferEntrada.
		this.bufferEntrada = bufferEntrada;
	}
}
