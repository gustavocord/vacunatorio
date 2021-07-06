package centroVacunacion;

public abstract class Vacuna {

	protected  static int temperatura;
	protected String nombre;
	protected Fecha ingreso;
	
	
	/* IREP
	 * Ingreso : deber ser menor a la fecha de hoy 
	 * Nombre : los valores que puede tomar son AstraZeneca ,Sinopharm ,Moderna , Sputnik y Pfizer
	 * ingreso : el ingreso tiene que ser <= a la fecha de hoy
	 * */
	
	public Vacuna( int temperatura , String nombre,Fecha ingreso  ) {
	
		this.temperatura = temperatura;
		this.nombre = nombre;
		this.ingreso= ingreso;
	
	}

	// la implementan todas las clases
	public abstract Fecha getVencimiento();
	
	


	@Override
	public String toString() {
		return "Vacuna [nombre=" + nombre + ", ingreso=" + ingreso + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ingreso == null) ? 0 : ingreso.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Vacuna other = (Vacuna) obj;
		if (ingreso == null) {
			if (other.ingreso != null)
				return false;
		} else if (!ingreso.equals(other.ingreso))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	

	
	 
	

	
}
