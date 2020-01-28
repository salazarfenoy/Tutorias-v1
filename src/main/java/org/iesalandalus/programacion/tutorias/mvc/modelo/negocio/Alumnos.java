package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;

public class Alumnos {

	private int capacidad;
	private int tamano;
	private Alumno[] coleccionAlumnos;

	public Alumnos(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionAlumnos = new Alumno[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;

	}

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;

	}

	private int buscarIndice(Alumno alumno) {
		int indice = 0;
		boolean alumnoEncontrado = false;
		while (!tamanoSuperado(indice) && !alumnoEncontrado) {
			if (coleccionAlumnos[indice].equals(alumno)) {
				alumnoEncontrado = true;
			} else {
				indice++;
			}
		}
		return indice;

	}

	private Alumno[] copiaProfundaAlumnos() {
		Alumno[] copiaAlumnos = new Alumno[coleccionAlumnos.length];
		for (int i = 0; i < coleccionAlumnos.length && coleccionAlumnos[i] != null; i++) {
			copiaAlumnos[i] = new Alumno(coleccionAlumnos[i]);
		}

		return copiaAlumnos;

	}

	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}

		int indice = buscarIndice(alumno);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
		}

		if (tamanoSuperado(indice)) {
			coleccionAlumnos[indice] = new Alumno(alumno);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}

	}

	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		int indice = buscarIndice(alumno);
		if (tamanoSuperado(indice)) {
			alumno = null;
		} else {
			alumno = new Alumno(coleccionAlumnos[indice]);
		}
		return alumno;
	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionAlumnos[i] = coleccionAlumnos[i + 1];
		}
		tamano--;
	}

	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		int indice = buscarIndice(alumno);

		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		}

	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Alumno[] get() {
		return copiaProfundaAlumnos();
	}

}
