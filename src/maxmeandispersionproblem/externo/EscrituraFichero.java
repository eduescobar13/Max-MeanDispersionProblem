package maxmeandispersionproblem.externo;
/**
	* Clase para la implementación de escritura de un fichero.
 	* @author: Eduardo Escobar Alberto
 	* @version: 1.0 25/04/2017
 	* Correo electrónico: eduescal13@gmail.com.
 	* Asignatura: Programación de Aplicaciones Interactivas.
 	* Centro: Universidad de La Laguna.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EscrituraFichero {

	// DECLARACIÓN DE ATRIBUTOS.
	private FileWriter ficheroSalida;
	private PrintWriter writerSalida;
	
	/**
	 	* Constructor por defecto. 
	 */
	public EscrituraFichero() {
		ficheroSalida = null;
		writerSalida  = null;
	}
	
	/**
	 	* Constructor.
	 	* @param nombreFichero Nombre del fichero donde se realiza la escritura. 
	 	* @throws IOException 
	 */
	public EscrituraFichero(String nombreFichero) throws IOException {
		ficheroSalida = new FileWriter(nombreFichero);
		writerSalida  = new PrintWriter(ficheroSalida);
	}
	
	public void escribirLineaFichero(String linea) {
		writerSalida.println(linea);
	}
	
	public void escribirElementoFichero(String elemento) {
		writerSalida.print(elemento);
	}
	
	public void cerrarFichero() throws IOException {
		ficheroSalida.close();
	}

	public FileWriter getFicheroSalida() { // Método getter del atributo ficheroSalida.
		return ficheroSalida;
	}

	public void setFicheroSalida(FileWriter ficheroSalida) { // Método setter del atributo ficheroSalida.
		this.ficheroSalida = ficheroSalida;
	}

	public PrintWriter getWriterSalida() { // Método getter del atributo writerSalida.
		return writerSalida;
	}

	public void setWriterSalida(PrintWriter writerSalida) { // Método setter del atributo writerSalida.
		this.writerSalida = writerSalida;
	}
}

