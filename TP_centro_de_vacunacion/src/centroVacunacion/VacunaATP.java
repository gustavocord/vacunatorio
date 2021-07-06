package centroVacunacion;

public class VacunaATP extends Vacuna {
	
	
	/*
	 * IREP
	 * 
	 *Temperatura la temperatura para la Moderna debee estar en -18 grados  y la Pfizer en 3 grados
	 * 
	 * Nombre : puede tomar valores   Moderna y Pfizer
	 * 
	 * */

	public VacunaATP(String nombre,Fecha ingreso ) {
		super( temperatura,  nombre, ingreso);
		
		if(nombre.equals("Moderna")) {
			this.temperatura=-18; 
		}
		
		else {
			this.temperatura=3;
		}
		
	}
	
		
	public Fecha getVencimiento(){
		if (this.nombre.equals("Moderna")) {
			return ingreso.sumar(61);
		}
		return null;

	}	

	@Override
	public String toString() {
		return  super.toString();
	}
}
