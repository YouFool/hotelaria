package br.com.senior.hotelaria.repository.customer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.model.QCustomerEntity;

/**
 * Repositório customizado para métodos que não utilizam Spring Data.
 * 
 * @author João Heckmann
 *
 */
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CustomerEntity> findCustomersByFields(String name, String document, String phone) {
		final QCustomerEntity customer = QCustomerEntity.customerEntity;
		BooleanBuilder predicate = new BooleanBuilder(); //

		if (name != null) {
			predicate.or(customer.name.toUpperCase().like("%" + name.toUpperCase() + "%"));
		}

		if (document != null) {
			predicate.or(customer.document.toUpperCase().like("%" + document.toUpperCase() + "%")); //
		}

		if (phone != null) {
			predicate.or(customer.phone.toUpperCase().like("%" + phone.toUpperCase() + "%"));
		}

		return new JPAQuery<CustomerEntity>(em).from(customer).where(predicate).fetch();
	}

}
