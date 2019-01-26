package br.com.senior.hotelaria.repository.accomodation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.hotelaria.model.AccommodationEntity;

/**
 * Repositório de {@link AccommodationEntity} para métodos que utilizam Spring
 * Data JPA.
 * 
 * @author João Heckmann
 *
 */
@Repository
public interface AccomodationRepository extends JpaRepository<AccommodationEntity, Long> {

	/**
	 * Encontra todas as estadias de um cliente.
	 * 
	 * @param customerId Id do cliente.
	 * @return Lista de estadias.
	 */
	public List<AccommodationEntity> findAllByCustomerId(Long customerId);
	
	/**
	 * Encontra a última estadia do cliente.
	 * @param customerId Id do cliente. 
	 * @return A última estadia do cliente.
	 */
	public AccommodationEntity findTopByCustomerIdOrderByIdDesc(Long customerId);

}
