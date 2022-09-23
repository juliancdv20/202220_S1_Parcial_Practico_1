package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Hotel y Habitacion.
 *
 * @hotel ISIS2603
 */

@Slf4j
@Service
public class HotelHabitacionService {

	@Autowired
	private HabitacionRepository habitacionRepository;

	@Autowired
	private HotelRepository hotelRepository;
	
	/**
	 * Asocia una Habitacion existente a un Hotel
	 *
	 * @param hotelId Identificador de la instancia de Hotel
	 * @param habitacionId   Identificador de la instancia de Habitacion
	 * @return Instancia de HabitacionEntity que fue asociada a Hotel
	 */

	@Transactional
	public HabitacionEntity addHabitacion(Long hotelId, Long habitacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una habitacion al hotel con id = {0}", hotelId);
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
		Optional<HabitacionEntity> habitacionEntity = habitacionRepository.findById(habitacionId);

		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("Hotel no encontrado");

		if (habitacionEntity.isEmpty())
			throw new EntityNotFoundException("Especialidd no encontrada");

		hotelEntity.get().getHabitaciones().add(habitacionEntity.get());
		log.info("Termina proceso de asociarle una habitacion al hotel con id = {0}", hotelId);
		return habitacionEntity.get();
	}

}
