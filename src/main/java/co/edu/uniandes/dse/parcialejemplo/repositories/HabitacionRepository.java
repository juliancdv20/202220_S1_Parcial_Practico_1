package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;

public interface HabitacionRepository extends JpaRepository<HabitacionEntity, Long> {
	List<HabitacionEntity> findByNumId(Integer numId);

}
