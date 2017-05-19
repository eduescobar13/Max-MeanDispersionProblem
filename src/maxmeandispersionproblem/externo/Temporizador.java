/**
 * Clase para la implementación de un temporizador con el que medir los tiempos de ejecución.
 * @author: Eduardo Escobar Alberto
 * @version: 1.0 02/05/2017
 * Correo electrónico: eduescal13@gmail.com.
 * Asignatura: Diseño y Análisis de Algoritmos.
 * Centro: Universidad de La Laguna.
 */

package maxmeandispersionproblem.externo;

public class Temporizador {
	
	private long tiempoInicial;
	private long tiempoFinal;
	
	/**
	 * Constructor.
	 */
	public Temporizador() {
		tiempoInicial = 0;
		tiempoFinal = 0;
	};
	
	/**
	 * Método que inicializa el temporizador.
	 */
	public void start() {
		long tiempo = System.nanoTime();
		this.setTiempoInicial(tiempo);
	}
	
	/**
	 * Método que detiene el temporizaror.
	 */
	public void stop() {
		long tiempo = System.nanoTime();
		this.setTiempoFinal(tiempo);
	}
	
	/**
	 * Función que devuelve el tiempo transcurrido entre el start y el stop.
	 * @return Tiempo transcurrido en nanosegundos.
	 */
	public long getTiempoTranscurrido() {
		long tiempoTranscurrido;
		tiempoTranscurrido = this.getTiempoFinal() - this.getTiempoInicial();
		return tiempoTranscurrido;
	}

	public long getTiempoInicial() {
		return tiempoInicial;
	}

	public void setTiempoInicial(long tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}

	public long getTiempoFinal() {
		return tiempoFinal;
	}

	public void setTiempoFinal(long tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}
}
