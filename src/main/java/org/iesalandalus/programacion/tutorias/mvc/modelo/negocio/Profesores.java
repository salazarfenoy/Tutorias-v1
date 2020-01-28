package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Profesores {

	private int capacidad;
	private int tamano;
	private Profesor[] coleccionProfesores;

	public Profesores(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionProfesores = new Profesor[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;

	}

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;

	}

	private int buscarIndice(Profesor profesor) {
		int indice = 0;
		boolean profesorEncontrado = false;
		while (!tamanoSuperado(indice) && !profesorEncontrado) {
			if (coleccionProfesores[indice].equals(profesor)) {
				profesorEncontrado = true;
			} else {
				indice++;
			}
		}
		return indice;

	}

	private Profesor[] copiaProfundaProfesores() {
		Profesor[] copiaProfesores = new Profesor[coleccionProfesores.length];
		for (int i = 0; i < coleccionProfesores.length && coleccionProfesores[i] != null; i++) {
			copiaProfesores[i] = new Profesor(coleccionProfesores[i]);
		}

		return copiaProfesores;

	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}

		int indice = buscarIndice(profesor);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más profesores.");
		}

		if (tamanoSuperado(indice)) {
			coleccionProfesores[indice] = new Profesor(profesor);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}

	}

	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			profesor = null;
		} else {
			profesor = new Profesor(coleccionProfesores[indice]);
		}

		return profesor;

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionProfesores[i] = coleccionProfesores[i + 1];
		}
		tamano--;
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);

		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		}

	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Profesor[] get() {
		return copiaProfundaProfesores();
	}
}
