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

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Habitacion
 *
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)
class HabitacionServiceTest {

	@Autowired
	private HabitacionService habitacionService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

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
		entityManager.getEntityManager().createQuery("delete from HabitacionEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			HabitacionEntity habitacionEntity = factory.manufacturePojo(HabitacionEntity.class);
			entityManager.persist(habitacionEntity);
			habitacionList.add(habitacionEntity);
		}

	}

	/**
	 * Prueba para crear un Habitacion
	 */
	@Test
	void testCreateHabitacion() throws EntityNotFoundException, IllegalOperationException {
		HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
		newEntity.setNumBanos(3);
		newEntity.setNumPersonas(3);
		HabitacionEntity result = habitacionService.createHabitacion(newEntity);
		assertNotNull(result);
		HabitacionEntity entity = entityManager.find(HabitacionEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNumId(), entity.getNumId());
		assertEquals(newEntity.getNumPersonas(), entity.getNumPersonas());
		assertEquals(newEntity.getNumCamas(), entity.getNumCamas());
		assertEquals(newEntity.getNumBanos(), entity.getNumBanos());
		assertEquals(newEntity.getNumId(), entity.getNumId());
	}
	
	/**
	 * Prueba para crear un habitacion con registro invalido
	 */
	@Test
	void testCreateHabitacionConBanosInvalidos() {
		assertThrows(IllegalOperationException.class, () -> {
			HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
			newEntity.setNumBanos(4);
			newEntity.setNumPersonas(3);
			habitacionService.createHabitacion(newEntity);
		});
	}
}
