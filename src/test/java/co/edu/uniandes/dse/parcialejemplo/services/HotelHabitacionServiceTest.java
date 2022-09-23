package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Hotel - Habitacions
 *
 * @hotel ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ HotelHabitacionService.class, HabitacionService.class })
class HotelHabitacionServiceTest {

	@Autowired
	private HotelHabitacionService hotelHabitacionService;

	@Autowired
	private HabitacionService habitacionService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private HotelEntity hotel = new HotelEntity();
	private HabitacionEntity habitacion = new HabitacionEntity();
	private List<HabitacionEntity> habitacionList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HotelEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from HabitacionEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		habitacion = factory.manufacturePojo(HabitacionEntity.class);
		entityManager.persist(habitacion);

		hotel = factory.manufacturePojo(HotelEntity.class);
		entityManager.persist(hotel);

		for (int i = 0; i < 3; i++) {
			HabitacionEntity entity = factory.manufacturePojo(HabitacionEntity.class);
			entity.setHotel(hotel);
			entityManager.persist(entity);
			habitacionList.add(entity);
			hotel.getHabitaciones().add(entity);
		}
	}

	/**
	 * Prueba para asociar un habitacion a un hotel.
	 *
	 */
	@Test
	void testAddHabitacion() throws EntityNotFoundException, IllegalOperationException {
		HabitacionEntity newHabitacion = factory.manufacturePojo(HabitacionEntity.class);
		habitacionService.createHabitacion(newHabitacion);

		HabitacionEntity habitacionEntity = hotelHabitacionService.addHabitacion(hotel.getId(), newHabitacion.getId());
		assertNotNull(habitacionEntity);

		assertEquals(habitacionEntity.getId(), newHabitacion.getId());
		assertEquals(habitacionEntity.getNumId(), newHabitacion.getNumId());
		assertEquals(habitacionEntity.getNumCamas(), newHabitacion.getNumCamas());
		assertEquals(habitacionEntity.getNumPersonas(), newHabitacion.getNumPersonas());
		assertEquals(habitacionEntity.getNumBanos(), newHabitacion.getNumBanos());
	}
	

	/**
	 * Prueba para asociar un habitacion a un hotel que no existe.
	 *
	 */

	@Test
	void testAddHabitacionHotelInvalido() {
		assertThrows(EntityNotFoundException.class, () -> {
			HabitacionEntity newHabitacion = factory.manufacturePojo(HabitacionEntity.class);
			habitacionService.createHabitacion(newHabitacion);
			hotelHabitacionService.addHabitacion(0L, newHabitacion.getId());
		});
	}

	/**
	 * Prueba para asociar un habitacion que no existe a un hotel.
	 *
	 */
	@Test
	void testAddInvalidHabitacion() {
		assertThrows(EntityNotFoundException.class, () -> {
			HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
			newEntity.setNumBanos(3);
			newEntity.setNumPersonas(3);
			hotelHabitacionService.addHabitacion(hotel.getId(), 0L);
		});
	}

}
