package org.iesalandalus.programacion.tutorias.mvc.modelo;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.AlumnoTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.CitaTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.ProfesorTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.SesionTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.TutoriaTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.AlumnosTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.CitasTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ProfesoresTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.SesionesTest;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.TutoriasTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AlumnoTest.class, CitaTest.class, ProfesorTest.class, SesionTest.class, TutoriaTest.class,
	AlumnosTest.class, CitasTest.class, ProfesoresTest.class, SesionesTest.class, TutoriasTest.class,
	ModeloTest.class })
public class AllTests {

}
