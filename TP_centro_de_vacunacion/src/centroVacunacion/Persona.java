package centroVacunacion;

import java.util.Comparator;

public  class Persona implements Comparable<Persona> {

	private int DNI;
	private Fecha fechaNac;
	private boolean tienePadecimientos;
	private boolean trabajaenSalud;
	
	
	
	
	/*IREP
	 * 
	 * DNI : Deber ser mayor a 0
	 * fechaNac : Mayor a 1900
	 * 
	 * 
	 * */
	
	
	

	public Persona (int DNI, Fecha fechaNac, boolean tienePadecimientos, boolean trabajaenSalud) {

		this.DNI = DNI;
		this.fechaNac = fechaNac;
		this.tienePadecimientos = tienePadecimientos;
		this.trabajaenSalud = trabajaenSalud;
	}

	Integer getPrioridad() {
		if(this.trabajaenSalud) {
			return 1;
		}
		if(esMayor()) {
			return 2;
		}
		if(this.tienePadecimientos) {
			
			return 3;
		}
		
		else {
			return 4;
		}
	}

	
	
	
	public int getDNI() {
		return DNI;
	}

	boolean esMayor() {
		return fechaNac.diferenciaAnios(Fecha.hoy(),fechaNac)>60;
	}
	

	
	
//esto nos va a servir para poder ordenar por prioridad cuando necesitemos asignar turnos
	@Override
	public int compareTo(Persona p) {
		// TODO Auto-generated method stub
	return this.getPrioridad().compareTo(p.getPrioridad());
	}
	
	
	

	@Override
	public String toString() {
		return "Persona [DNI=" + DNI + ", fechaNac=" + fechaNac + ", tienePadecimientos=" + tienePadecimientos
				+ ", trabajaenSalud=" + trabajaenSalud + "]";
	}
	
	
}



