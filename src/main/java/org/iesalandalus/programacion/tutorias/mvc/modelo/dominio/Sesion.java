package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Sesion {
	private static final LocalTime HORA_COMIENZO_CLASES = LocalTime.of(16, 00);
	private static final LocalTime HORA_FIN_CLASES = LocalTime.of(22, 15);
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalDate fecha;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int minutosDuracion;
	private Tutoria tutoria;

	public Sesion(Tutoria tutoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int minutosDuracion) {

		setTutoria(tutoria);
		setFecha(fecha);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setMinutosDuracion(minutosDuracion);
		comprobarValidezSesion();
	}

	public Sesion(Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No es posible copiar una sesión nula.");
		}

		setTutoria(sesion.tutoria);
		setFecha(sesion.fecha);
		setHoraInicio(sesion.horaInicio);
		setHoraFin(sesion.horaFin);
		setMinutosDuracion(sesion.minutosDuracion);

	}

	private void comprobarValidezSesion() {
		if (horaInicio.isAfter(HORA_FIN_CLASES) || horaInicio.isBefore(HORA_COMIENZO_CLASES)
				|| horaInicio.equals(HORA_FIN_CLASES)) {
			throw new IllegalArgumentException("ERROR: La hora de inicio no es válida.");
		}
		if (horaFin.isBefore(HORA_COMIENZO_CLASES) || horaFin.isAfter(HORA_FIN_CLASES)) {
			throw new IllegalArgumentException("ERROR: La hora de fin no es válida.");
		}

		if (horaFin.equals(horaInicio) || horaFin.isBefore(horaInicio)) {
			throw new IllegalArgumentException("ERROR: Las hora para establecer la sesión no son válidas.");
		}
		int minutosDuracionTotal = (int) horaInicio.until(horaFin, ChronoUnit.MINUTES);
		if (minutosDuracionTotal % minutosDuracion != 0) {
			throw new IllegalArgumentException(
					"ERROR: Los minutos de duración no es divisor de los minutos establecidos para toda la sesión.");
		}

	}

	public static Sesion getSesionFicticia(Tutoria tutoria, LocalDate fecha) {

		return new Sesion(tutoria, fecha, HORA_COMIENZO_CLASES, HORA_FIN_CLASES, 1);

	}

	public LocalDate getFecha() {
		return fecha;
	}

	private void setFecha(LocalDate fecha) {

		if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}

		if (!fecha.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: Las sesiones de deben planificar para fechas futuras.");
		}
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	private void setHoraInicio(LocalTime horaInicio) {

		if (horaInicio == null) {
			throw new NullPointerException("ERROR: La hora de inicio no puede ser nula.");
		}

		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	private void setHoraFin(LocalTime horaFin) {

		if (horaFin == null) {
			throw new NullPointerException("ERROR: La hora de fin no puede ser nula.");
		}

		this.horaFin = horaFin;
	}

	public int getMinutosDuracion() {
		return minutosDuracion;
	}

	private void setMinutosDuracion(int minutosDuracion) {

		if (minutosDuracion == 0) {
			throw new IllegalArgumentException("ERROR: Los minutos de duración no son válidos.");
		}

		this.minutosDuracion = minutosDuracion;
	}

	public Tutoria getTutoria() {
		return tutoria;
	}

	private void setTutoria(Tutoria tutoria) {

		if (tutoria == null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}

		this.tutoria = new Tutoria(tutoria);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((tutoria == null) ? 0 : tutoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (tutoria == null) {
			if (other.tutoria != null)
				return false;
		} else if (!tutoria.equals(other.tutoria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String mensaje = String.format("tutoria=%s, fecha=%s, horaInicio=%s, horaFin=%s, minutosDuracion=%s",
				getTutoria(), getFecha().format(FORMATO_FECHA), getHoraInicio(), getHoraFin(), getMinutosDuracion());
		return mensaje;
	}

}
