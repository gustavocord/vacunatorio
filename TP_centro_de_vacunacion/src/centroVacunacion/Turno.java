package centroVacunacion;

public class Turno {

	private Fecha fecha;
	private Persona persona;
	private Vacuna vacuna;
	
	
	
/* IREP
 * La fecha tiene que ser  Mayor a la fecha de hoy 
 * persona : Distinto a Null 
 * vacuna : Su valor valido  depende de la persona que contenga
 * 
 * 
 * 
 * */
	
	Turno (Fecha fecha , Persona p , Vacuna vac){
		this.fecha= fecha;
		this.persona = p;
		this.vacuna= vac;
	}

	public Fecha getF() {
		return fecha;
	}

	
	
	@Override
	public String toString() {
		return "Turno [f=" + fecha + ", p=" + persona.getDNI() + ", vacu=" + vacuna.getNombre() + "]";
	}

	public Persona getPersona() {
		return persona;
	}

	public Vacuna getVacu() {
		return vacuna;
	}
	
	
	
	
	
}
