package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

public class Alumno {
	private static final String ER_NOMBRE = "([a-zA-ZÁÉÍÓÚáéíóú]+)(\\s+([a-zA-ZÁÉÍÓÚáéíóú]+))+";
	private static final String PREFIJO_EXPEDIENTE = "SP_";
	private static final String ER_CORREO = "([\\w\\.]+[^.])@[\\w^\\_]+\\.[a-z]{2,3}";
	private static int ultimoIdentificador;
	private String nombre, correo, expediente;

	public Alumno(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
		setExpediente(expediente);

	}

	public Alumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(alumno.nombre);
		setCorreo(alumno.correo);
		this.expediente = alumno.expediente;
	}

	public static Alumno getAlumnoFicticio(String correo) {

		return new Alumno("Nombre Ficticio", correo);

	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {

		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	private String formateaNombre(String nombre) {
		nombre = nombre.replaceAll("( )+", " ");
		nombre = nombre.trim();

		String[] palabras = nombre.split(" ");
		StringBuilder copiaNombre = new StringBuilder();
		for (int i = 0; i <= palabras.length - 1; i++) {

			palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();

			copiaNombre.append(palabras[i] + " ");
		}

		nombre = copiaNombre.toString();
		return nombre.trim();

	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	public String getExpediente() {
		return expediente;
	}

	private void setExpediente(String expediente) {

		incrementaUltimoIdentificador();
		StringBuilder expedienteAsignado = new StringBuilder(PREFIJO_EXPEDIENTE);
		expedienteAsignado.append(getIniciales() + "_");
		expedienteAsignado.append(ultimoIdentificador);

		this.expediente = expedienteAsignado.toString();
	}

	private static void incrementaUltimoIdentificador() {
		++ultimoIdentificador;
	}

	private String getIniciales() {

		String iniciales = "";
		String[] palabras = getNombre().split(" ");
		for (int i = 0; i <= palabras.length - 1; i++) {

			iniciales = iniciales + palabras[i].charAt(0);
		}

		return iniciales.toUpperCase();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String mensaje = String.format("nombre=%s (%s), correo=%s, expediente=%s", getNombre(), getIniciales(),
				getCorreo(), getExpediente());
		return mensaje;
	}

}
