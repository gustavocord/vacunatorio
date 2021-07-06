package centroVacunacion;

public class VacunaMayores extends Vacuna {
	
	
	/*
	 * IREP
	 * 
	 *Temperatura la temperatura para la Pfizer debee estar en -18 grados  y la sputknic en 3 grados
	 * 
	 * Nombre : las unicas dos vacunas permitidas son  Pfizer y sputknic
	 * 
	 * */

	public VacunaMayores( String nombre,Fecha ingreso ) {
		super( temperatura, nombre, ingreso );

		if(nombre.equals("Pfizer")) {
			this.temperatura=-18; 
		}
		
		else {
			this.temperatura=3;
		}
	
	}
	
	public Fecha getVencimiento(){
	if (nombre.equals("Pfizer")) {
		return ingreso.sumar(31);
	}
	
	return null;
	
	
}
	
	
	@Override
	public String toString() {
		return  super.toString();
	}
}