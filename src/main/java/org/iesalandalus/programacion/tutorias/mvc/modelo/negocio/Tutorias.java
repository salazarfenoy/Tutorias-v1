package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Tutorias {

	private int capacidad;
	private int tamano;
	private Tutoria[] coleccionTutorias;

	public Tutorias(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionTutorias = new Tutoria[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;

	}

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;

	}

	private int buscarIndice(Tutoria tutoria) {
		int indice = 0;
		boolean tutoriaEncontrada = false;
		while (!tamanoSuperado(indice) && !tutoriaEncontrada) {
			if (coleccionTutorias[indice].equals(tutoria)) {
				tutoriaEncontrada = true;
			} else {
				indice++;
			}
		}
		return indice;

	}

	private Tutoria[] copiaProfundaTutorias() {
		Tutoria[] copiaTutorias = new Tutoria[coleccionTutorias.length];
		for (int i = 0; i < coleccionTutorias.length && coleccionTutorias[i] != null; i++) {
			copiaTutorias[i] = new Tutoria(coleccionTutorias[i]);
		}

		return copiaTutorias;

	}

	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}

		int indice = buscarIndice(tutoria);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más tutorías.");
		}

		if (tamanoSuperado(indice)) {
			coleccionTutorias[indice] = new Tutoria(tutoria);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}

	}

	public Tutoria buscar(Tutoria tutoria) {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		int indice = buscarIndice(tutoria);
		if (tamanoSuperado(indice)) {
			tutoria = null;
		} else {
			tutoria = new Tutoria(coleccionTutorias[indice]);
		}

		return tutoria;

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionTutorias[i] = coleccionTutorias[i + 1];
		}
		tamano--;
	}

	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		int indice = buscarIndice(tutoria);

		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		}

	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Tutoria[] get(Profesor profesor) {
		int j = 0;
		Tutoria[] copiaTutoriasProfesor = new Tutoria[tamano];
		for (int i = 0; i < tamano; i++) {
			if (coleccionTutorias[i].getProfesor().equals(profesor)) {
				copiaTutoriasProfesor[j] = new Tutoria(coleccionTutorias[i]);
				j++;
			}
		}
		return copiaTutoriasProfesor;

	}

	public Tutoria[] get() {
		return copiaProfundaTutorias();
	}

}
