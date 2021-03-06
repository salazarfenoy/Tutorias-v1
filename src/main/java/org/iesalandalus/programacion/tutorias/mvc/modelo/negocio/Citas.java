package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

public class Citas {

	private List<Cita> coleccionCitas;

	public Citas() {

		coleccionCitas = new ArrayList<>();

	}

	public int getTamano() {
		return coleccionCitas.size();
	}

	private List<Cita> copiaProfundaCitas() {
		List<Cita> copiaCitas = new ArrayList<>();

		for (Cita cita : coleccionCitas) {
			copiaCitas.add(new Cita(cita));
		}

		return copiaCitas;

	}

	public void insertar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}

		int indice = coleccionCitas.indexOf(cita);
		if (indice == -1) {
			coleccionCitas.add(new Cita(cita));

		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita con esa hora.");
		}

	}

	public Cita buscar(Cita cita) {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una cita nula.");
		}
		int indice = coleccionCitas.indexOf(cita);
		if (indice == -1) {
			return null;
		} else {
			return new Cita(coleccionCitas.get(indice));
		}

	}

	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice = coleccionCitas.indexOf(cita);

		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita con esa hora.");
		} else {
			coleccionCitas.remove(indice);
		}

	}

	public List<Cita> get(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		List<Cita> copiaCitasSesion = new ArrayList<>();

		for (Cita cita : coleccionCitas) {
			if (cita.getSesion().equals(sesion)) {
				copiaCitasSesion.add(new Cita(cita));
			}
		}

		copiaCitasSesion.sort(Comparator.comparing(Cita::getHora));

		return copiaCitasSesion;

	}

	public List<Cita> get(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		List<Cita> copiaCitasAlumno = new ArrayList<>();

		for (Cita cita : coleccionCitas) {
			if (cita.getAlumno().equals(alumno)) {
				copiaCitasAlumno.add(new Cita(cita));
			}
		}

		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getDni);
		Comparator<Tutoria> comparadorTutoria = Comparator.comparing(Tutoria::getProfesor, comparadorProfesor).thenComparing(Tutoria::getNombre);
		Comparator<Sesion> comparadorSesion = Comparator.comparing(Sesion::getTutoria,comparadorTutoria).thenComparing(Sesion::getFecha);
		copiaCitasAlumno.sort(Comparator.comparing(Cita::getSesion, comparadorSesion).thenComparing(Cita::getHora));

		return copiaCitasAlumno;

	}

	public List<Cita> get() {
		List<Cita> citasOrdenadas = copiaProfundaCitas();
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getDni);
		Comparator<Tutoria> comparadorTutoria = Comparator.comparing(Tutoria::getProfesor, comparadorProfesor).thenComparing(Tutoria::getNombre);
		Comparator<Sesion> comparadorSesion = Comparator.comparing(Sesion::getTutoria,comparadorTutoria).thenComparing(Sesion::getFecha);
		citasOrdenadas.sort(Comparator.comparing(Cita::getSesion, comparadorSesion).thenComparing(Cita::getHora));
		return citasOrdenadas;
	}

}
