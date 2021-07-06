package centroVacunacion;
import java.util.LinkedList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public  class  CentroVacunacion implements Interfaz {

	String nombreCentro ;
	int capacidadVacunacionDiaria;
	
	LinkedList<Persona>  inscriptos; //Personas inscriptas
	LinkedList<Turno> turnos; // turnos con su fecha , persona y vacuna reservada
	LinkedList<Vacuna> vacunas; 
	HashMap<Integer, String> vacunados; // dni y vacuna	
	HashMap<String, Integer> vacunasVencidas; // vacunas vencidas con el nombre y la cantidad 
	HashMap<String, Integer> cantidadPorTipo;  // nombre de la vacuna y la cantidad
	
	
	/**
	 * IREP 
	 * 
	 * Inscriptos : No permite  personas con un mismo DNI  y solo pueden estar inscriptos si no se
	 * vacunaron antes . La persona debe ser mayor a 18
	 * 
	 * Turnos : Los turnos no pueden tener una persona que no este inscripta  previamente o una persona que ya se vacuno
	 			- Los turnos deben estar ordenados por prioridad
	 			
	 * 
	 * Vacunas :
	 *   
	 *   vacunasVencidas : contiene vacunas con fecha de vencimiento < a la fecha de hoy . Solo contiene las vacunas anteriormente nombradas
	 *   La cantidad es >=  0 
	 *  
	 *         
	 *Vacunados : no se permite tener personas que no hayan estado inscriptas y que hayan tenido turno previamente
	 *
	 *capacidad  : capacidad de vacunatorio >0
	 *nombre del centro : nombre del centro distinto a null
	 *  
	 * 
	 * /
	
	
	
	
	/**
	 * Constructor. recibe el nombre del centro y la capacidad de vacunación diaria.
	 * Si la capacidad de vacunación no es positiva se debe generar una excepción.
	 * Si el nombre no está definido, se debe generar una excepción.
	 */
	  CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		
		  
		  
		if(nombreCentro!=null &&  capacidadVacunacionDiaria >0  ) {
			this.nombreCentro = nombreCentro;
			this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;
			this.vacunas=  new LinkedList<Vacuna>();
			this.inscriptos= new LinkedList<Persona>();
			this.turnos =  new LinkedList<Turno>();
			this.vacunados= new HashMap<Integer, String>();
			this.cantidadPorTipo= new HashMap<String, Integer>();
			this.vacunasVencidas =new HashMap<String,Integer>();
			
			// ingreso las vacunas con su cantidad 0
			cantidadPorTipo.put("AstraZeneca", 0);
			cantidadPorTipo.put("Sinopharm", 0);
			cantidadPorTipo.put("Moderna", 0);
			cantidadPorTipo.put("Sputnik", 0);
			cantidadPorTipo.put("Pfizer", 0);
			
			// vacunas vencidas
			vacunasVencidas.put("AstraZeneca", 0);
			vacunasVencidas.put("Sinopharm", 0);
			vacunasVencidas.put("Moderna", 0);
			vacunasVencidas.put("Sputnik", 0);
			vacunasVencidas.put("Pfizer", 0);
			
			}
		else {
			throw new RuntimeException("Datos incorrectos");
			
		}
		
	};
	
	

	@Override

	public String toString() { 
		StringBuilder sb = new StringBuilder();
		sb.append("---------------"+this.nombreCentro+"---------------");
		sb.append("\ncapacidad De Vacunacion Diaria: "+this.capacidadVacunacionDiaria);
		sb.append("\ncantidad de personas en lista de espera: "+ this.inscriptos.size());
		sb.append("\nCantidad de vacunas disponibles: "+vacunasDisponibles());
		sb.append("\nCantidad de vacunas aplicadas:"+vacunados.size());
		return sb.toString();
		}
	
	




	/**
	 * Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte. Si
	 * el nombre de la vacuna no coincidiera con los especificados se debe generar
	 * una excepción. También se genera excepción si la cantidad es negativa. La
	 * cantidad se debe sumar al stock existente, tomando en cuenta las vacunas ya
	 * utilizadas.
	 */
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso)
	{	
		
	if (cantidad>0) {	
		if((nombreVacuna.equals("AstraZeneca")||nombreVacuna.equals("Sinopharm")|| nombreVacuna.equals("Moderna"))) {	
			Integer cant =cantidadPorTipo.get(nombreVacuna)+(Integer)cantidad;
			cantidadPorTipo.put(nombreVacuna,cant);	
		
			for(int i =0; i<cantidad ; i++) {
				vacunas.add(new VacunaATP( nombreVacuna,fechaIngreso));
			}
		}
		if((nombreVacuna.equals("Sputnik")||nombreVacuna.equals("Pfizer"))) {
			Integer cantm =cantidadPorTipo.get(nombreVacuna)+(Integer)cantidad;
			cantidadPorTipo.put(nombreVacuna,cantm);
			
			for(int j =0; j<cantidad ; j++) {
				vacunas.add(new VacunaMayores(nombreVacuna,fechaIngreso));
			}
						
			
		}
	}
		else {
			throw new RuntimeException("las vacunas que se intenta ingresar no son correctas");
		}
		
	};




	



	/**
	 * total de vacunas disponibles no vencidas sin distinción por tipo.
	 */
	public int vacunasDisponibles() {
		
	
		return vacunas.size();
	};

	
	/**
	 * total de vacunas disponibles no vencidas que coincida con el nombre de vacuna
	 * especificado.
	 */
	public int vacunasDisponibles(String nombreVacuna) {
		
		return cantidadPorTipo.get(nombreVacuna);
	};

	/**
	 * Se inscribe una persona en lista de espera. Si la persona ya se encuentra
	 * inscripta o es menor de 18 años, se debe generar una excepción. Si la persona
	 * ya fue vacunada, también debe generar una excepción.
	 */
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud)
	{
	
		if(!estaInscripto(dni)&&nacimiento.diferenciaAnios(Fecha.hoy(),nacimiento)>18&& !estaVacunada(dni)) {
			Persona p =new Persona(dni,nacimiento,tienePadecimientos,esEmpleadoSalud);
			inscriptos.add(p);
		}
		else {
			throw new RuntimeException("ERROR la persona esta vacunada o ya esta inscripta en la lista de espera");

		}
		
		
	};

	/**
	 * Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron y
	 * que no tienen turno asignado. Si no quedan inscriptos sin vacunas debe
	 * devolver una lista vacía.
	 */
	public List<Integer> listaDeEspera(){
		LinkedList<Integer> lista = new LinkedList<Integer>();
		if(!inscriptos.isEmpty()) {
			for(Persona p : inscriptos) {
				if (!tieneTurno(p.getDNI())) {
					lista.add(p.getDNI());
					}
				}
			}
			return lista;
	};
	
	
	

	/**
	 * Primero se verifica si hay turnos vencidos. En caso de haber turnos vencidos,
	 * la persona que no asistió al turno debe ser borrada del sistema y la vacuna
	 * reservada debe volver a estar disponible.
	 *
	 * Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	 *
	 * Por último, se procede a asignar los turnos a partir de la fecha inicial
	 * recibida según lo especificado en la 1ra parte. Cada vez que se registra un
	 * nuevo turno, la vacuna destinada a esa persona dejará de estar disponible.
	 * Dado que estará reservada para ser aplicada el día del turno.
	 *
	 *
	 */
	public void generarTurnos(Fecha fechaInicial) {
		int turnosAsignados=0;
		
		
		if (Fecha.hoy().anterior(fechaInicial)) {
			quitarVencidas();
			quitarTurnos();
			
			//se ordenan por prioridad
			Collections.sort(inscriptos);

			for (Persona per : inscriptos) {
				

				Vacuna vac = asignarVacuna(per);
				
				if (vac != null  ) {
					if (this.capacidadVacunacionDiaria > turnosAsignados) {
						Turno tur = new Turno(fechaInicial,per ,vac);
						turnosAsignados++;
						turnos.add(tur);
						
						
					} else {
						Fecha siguienteDia = new Fecha(fechaInicial.dia(), fechaInicial.mes(), fechaInicial.anio());
						siguienteDia.avanzarUnDia();
						Turno turOtro = new Turno(siguienteDia, per, vac);
						turnosAsignados++;
						turnos.add(turOtro);
						
					}
				}
				else {
					throw new RuntimeException("ERROR no hay vacuna disponible para la persona.");
				}
			}
		} else {
			throw new RuntimeException("No se puede asignar un turno con una fecha pasada.");
		}
	
	};
	
	

	/**
	 * Devuelve una lista con los dni de las personas que tienen turno asignado para
	 * la fecha pasada por parámetro. Si no hay turnos asignados para ese día, se
	 * debe devolver una lista vacía. La cantidad de turnos no puede exceder la
	 * capacidad por día de la ungs.
	 */
	public List<Integer> turnosConFecha(Fecha fecha)
	{
		LinkedList<Integer> lista = new LinkedList<Integer>();
		if(!turnos.isEmpty()) {
			for(Turno tur : turnos) {
				if(tur.getF().compareTo(fecha)==0) {
					lista.add(tur.getPersona().getDNI());
					}
				}
			}
			return lista;
	};

	/**
	 * Dado el DNI de la persona y la fecha de vacunación se valida que esté
	 * inscripto y que tenga turno para ese dia. - Si tiene turno y está inscripto
	 * se debe registrar la persona como vacunada y la vacuna se quita del depósito.
	 * - Si no está inscripto o no tiene turno ese día, se genera una Excepcion.
	 */
	public void vacunarInscripto(int dni, Fecha fechaVacunacion)
	{

		if(estaInscripto(dni)&& tieneTurno(dni) && !vacunados.containsKey(dni)) {
			Iterator<Turno> it = turnos.iterator();

			while (it.hasNext()) {
				
				Turno temp = it.next();
				
				if(temp.getPersona().getDNI()==dni ) {
					if(temp.getF().compareTo(fechaVacunacion)==0 ) {
						
						//  se agrega  la vacuna a vacunados y sacamos a la persona de inscriptos y turnos
						vacunados.put(dni, temp.getVacu().getNombre());
						it.remove(); 
						inscriptos.remove(temp.getPersona());
						
						
					}
					else {
						throw new RuntimeException("ERROR la persona no tiene turno para la fecha indicada");

					}
				}
			}
		}
		else {
			throw new RuntimeException("ERROR la persona no esta inscripta o no tiene turno asignado");

		}
	};

	/**
	 * Devuelve un Diccionario donde - la clave es el dni de las personas vacunadas
	 * - Y, el valor es el nombre de la vacuna aplicada.
	 */
	public Map<Integer, String> reporteVacunacion(){
		return vacunados;
	};

	/**
	 * Devuelve en O(1) un Diccionario: - clave: nombre de la vacuna - valor:
	 * cantidad de vacunas vencidas conocidas hasta el momento.
	 */
	public Map<String, Integer> reporteVacunasVencidas()
	{
		return vacunasVencidas;
	}

	
	
	/***********************************************************
	 *              Metodos auxiliares                         *
	 * *********************************************************
	 * 
	 */
	

	
	public void quitarVencidas() {

		Iterator<Vacuna> it = vacunas.iterator();

		while (it.hasNext()) {
			Vacuna temp= it.next();

			if (temp.getVencimiento() != null ) {
				
				if(temp.getVencimiento().anterior(Fecha.hoy())) {
				
						Integer cant=vacunasVencidas.get(temp.getNombre())+1;
						vacunasVencidas.put(temp.getNombre(), cant);
						Integer restar = cantidadPorTipo.get(temp.getNombre())-1;
						cantidadPorTipo.put(temp.getNombre(), restar);
						it.remove();
						

				}
			} 

		
		     	}
			}	
	
		
	
	/**
	 * se asignan vacunas dependiendo si la persona es mayor o menor ***
	 * 
	 * 
	 */
	
	
public Vacuna asignarVacuna(Persona p) {
	
	Vacuna vac =null;

	Iterator <Vacuna>it = vacunas.iterator() ;
	while (it.hasNext()) {
		Vacuna temp= it.next();
		if(vac==null) {
			if(!p.esMayor()) {
				
				if( temp instanceof VacunaATP) {
			
					vac= temp;
					it.remove();
					Integer tipo = cantidadPorTipo.get(vac.getNombre())-1;
					cantidadPorTipo.put(vac.getNombre(),tipo);
				}
			}
			
		else {
			vac= temp;
			it.remove();
			Integer tipo2 = cantidadPorTipo.get(vac.getNombre())-1;
			cantidadPorTipo.put(vac.getNombre(),tipo2);


			}
		}
	}
	
	
	return vac;
}
	


/**
 * Se quitan los turnos vencidos y a las personas que no asistieron se las borra del sistema , ademas se devuelven las vacunas al 
 * Stock ***
 * 
 * 
 */



public void quitarTurnos() {

	LinkedList<Turno> tur = new LinkedList<Turno>();
	LinkedList<Vacuna> vac = new LinkedList<Vacuna>();
	LinkedList<Persona> personas = new LinkedList<Persona>();
	
	for(int i=0; i<turnos.size(); i++) {
		
		if(turnos.get(i).getF().anterior(Fecha.hoy())) {
			
			tur.add(turnos.get(i));
			personas.add(turnos.get(i).getPersona());

			//agregamos la vacuna a la lista para luego agregarla al stock
			vac.add(turnos.get(i).getVacu());
			Integer cant = cantidadPorTipo.get(turnos.get(i).getVacu().getNombre())+1;
			cantidadPorTipo.put(turnos.get(i).getVacu().getNombre(),cant);
			
		}
	}

	turnos.removeAll(tur);
	inscriptos.removeAll(personas);
	vacunas.addAll(vac);
}	


	
	
public  boolean tieneTurno(int DNI) {
		
	for (Turno p : turnos) {
		if (p.getPersona().getDNI()==DNI) {
			return true;
		}
	}
	
	return false;
}

public boolean estaInscripto(int DNI) {
		
		for (Persona p : inscriptos) {
			if (p.getDNI()==DNI) {
				return true;
			}
		}
		
		return false;
	}

public boolean estaVacunada(Integer DNI) {
	
	return vacunados.containsKey(DNI);
}


}
