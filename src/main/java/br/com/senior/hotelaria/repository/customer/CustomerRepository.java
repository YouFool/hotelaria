package br.com.senior.hotelaria.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.hotelaria.model.CustomerEntity;

/**
 * Repositório de {@link CustomerEntity} para métodos que utilizam Spring Data
 * JPA.
 * 
 * @author João Heckmann
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
