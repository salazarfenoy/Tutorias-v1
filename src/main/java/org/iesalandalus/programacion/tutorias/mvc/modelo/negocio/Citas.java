package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;

public class Citas {
	private int capacidad;
	private int tamano;
	private Cita[] coleccionCitas;

	public Citas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionCitas = new Cita[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;

	}

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;

	}

	private int buscarIndice(Cita cita) {
		int indice = 0;
		boolean citaEncontrada = false;
		while (!tamanoSuperado(indice) && !citaEncontrada) {
			if (coleccionCitas[indice].equals(cita)) {
				citaEncontrada = true;
			} else {
				indice++;
			}
		}
		return indice;

	}

	private Cita[] copiaProfundaCitas() {
		Cita[] copiaCitas = new Cita[coleccionCitas.length];
		for (int i = 0; i < coleccionCitas.length && coleccionCitas[i] != null; i++) {

			copiaCitas[i] = new Cita(coleccionCitas[i]);

		}

		return copiaCitas;

	}

	public void insertar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}

		int indice = buscarIndice(cita);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}

		if (tamanoSuperado(indice)) {
			coleccionCitas[indice] = new Cita(cita);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita con esa hora.");
		}

	}

	public Cita buscar(Cita cita) {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una cita nula.");
		}
		int indice = buscarIndice(cita);
		if (tamanoSuperado(indice)) {
			cita = null;
		} else {
			cita = new Cita(coleccionCitas[indice]);
		}

		return cita;

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionCitas[i] = coleccionCitas[i + 1];
		}
		tamano--;
	}

	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice = buscarIndice(cita);

		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita con esa hora.");
		}

	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Cita[] get(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		int j = 0;
		Cita[] copiaCitasSesion = new Cita[tamano];
		for (int i = 0; i < tamano; i++) {
			if (coleccionCitas[i].getSesion().equals(sesion)) {
				copiaCitasSesion[j] = new Cita(coleccionCitas[i]);
				j++;

			}
		}

		return copiaCitasSesion;

	}

	public Cita[] get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		int j = 0;
		Cita[] copiaCitasAlumno = new Cita[tamano];
		for (int i = 0; i < tamano; i++) {
			if (coleccionCitas[i].getAlumno().equals(alumno)) {
				copiaCitasAlumno[j] = new Cita(coleccionCitas[i]);
				j++;
			}
		}

		return copiaCitasAlumno;

	}

	public Cita[] get() {
		return copiaProfundaCitas();
	}

}
