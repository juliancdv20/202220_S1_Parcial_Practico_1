package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Hotel.
 */

@Slf4j
@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
		
	/**
	 * Se encarga de crear un Hotel en la base de datos.
	 *
	 * @param hotel Objeto de HotelEntity con los datos nuevos
	 * @return Objeto de HotelEntity con los datos nuevos y su ID.
	 * @throws IllegalOperationException 
	 */
	@Transactional
	public HotelEntity createHotel(HotelEntity hotel) throws IllegalOperationException {
		log.info("Inicia proceso de creación del hotel");
		if (!hotelRepository.findByNombre(hotel.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El nombre de este hotel ya existe");
		}
		if (hotel.getNumEstrellas() < 2 || hotel.getNumEstrellas() > 6) {
			throw new IllegalOperationException("El número de estrellas del hotel debe estar entre 2 y 6");

		}
		return hotelRepository.save(hotel);
	}
}
