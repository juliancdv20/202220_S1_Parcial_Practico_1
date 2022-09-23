package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Habitacion.
 */

@Slf4j
@Service
public class HabitacionService {

	@Autowired
	HabitacionRepository habitacionRepository;
	
	@Autowired
	HabitacionEntity habitacionEntity;
	
	/**
	 * Se encarga de crear un Habitacion en la base de datos.
	 *
	 * @param habitacion Objeto de HabitacionEntity con los datos nuevos
	 * @return Objeto de HabitacionEntity con los datos nuevos y su ID.
	 * @throws IllegalOperationException 
	 */
	@Transactional
	public HabitacionEntity createHabitacion(HabitacionEntity habitacion) throws IllegalOperationException {
		log.info("Inicia proceso de creación del habitacion");
		if (!(habitacionEntity.getNumBanos() <= habitacionEntity.getNumPersonas())) {
			throw new IllegalOperationException("El numero de banios de la habitacion no puede ser mayor al numero de personas");
		}
		
		return habitacionRepository.save(habitacion);
	}
}
