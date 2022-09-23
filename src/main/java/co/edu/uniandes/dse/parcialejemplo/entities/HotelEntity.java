package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class HotelEntity extends BaseEntity{
	
	private String nombre;
	private String direccion;
	private Integer numEstrellas;
	
	@PodamExclude
	@ManyToOne
	private List<HabitacionEntity> habitaciones = new ArrayList<>();
}
