package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class HabitacionEntity extends BaseEntity{

	private Integer numId;
	private Integer numPersonas;
	private Integer numCamas;
	private Integer numBanos;
	
	@PodamExclude
	@OneToMany(mappedBy = "habitaciones")
	private HotelEntity hotel;
}