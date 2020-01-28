package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Sesiones {
	private int capacidad;
	private int tamano;
	private Sesion[] coleccionSesiones;

	public Sesiones(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionSesiones = new Sesion[capacidad];
		this.capacidad = capacidad;
		tamano = 0;
	}

	private boolean capacidadSuperada(int indice) {
		return indice >= capacidad;

	}

	private boolean tamanoSuperado(int indice) {
		return indice >= tamano;

	}

	private int buscarIndice(Sesion sesion) {
		int indice = 0;
		boolean sesionEncontrada = false;
		while (!tamanoSuperado(indice) && !sesionEncontrada) {
			if (coleccionSesiones[indice].equals(sesion)) {
				sesionEncontrada = true;
			} else {
				indice++;
			}
		}
		return indice;

	}

	private Sesion[] copiaProfundaSesiones() {
		Sesion[] copiaSesiones = new Sesion[coleccionSesiones.length];
		for (int i = 0; i < coleccionSesiones.length && coleccionSesiones[i] != null; i++) {
			copiaSesiones[i] = new Sesion(coleccionSesiones[i]);
		}

		return copiaSesiones;

	}

	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}

		int indice = buscarIndice(sesion);
		if (capacidadSuperada(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más sesiones.");
		}

		if (tamanoSuperado(indice)) {
			coleccionSesiones[indice] = new Sesion(sesion);
			tamano++;
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}

	}

	public Sesion buscar(Sesion sesion) {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}
		int indice = buscarIndice(sesion);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return new Sesion(coleccionSesiones[indice]);
		}

	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {

		for (int i = indice; !tamanoSuperado(i); i++) {
			coleccionSesiones[i] = coleccionSesiones[i + 1];
		}
		tamano--;
	}

	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		int indice = buscarIndice(sesion);

		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		}

	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public Sesion[] get(Tutoria tutoria) {
		int j = 0;
		Sesion[] copiaSesionesTutoria = new Sesion[tamano];
		for (int i = 0; i < tamano; i++) {
			if (coleccionSesiones[i].getTutoria().equals(tutoria)) {
				copiaSesionesTutoria[j] = new Sesion(coleccionSesiones[i]);
				j++;
			}
		}
		return copiaSesionesTutoria;

	}

	public Sesion[] get() {
		return copiaProfundaSesiones();
	}

}
