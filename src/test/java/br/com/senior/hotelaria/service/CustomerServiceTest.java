package br.com.senior.hotelaria.service;

import static br.com.senior.hotelaria.builder.CustomerEntityBuilder.oneCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.repository.customer.CustomerRepository;
import br.com.senior.hotelaria.repository.customer.CustomerRepositoryImpl;

/**
 * Testes para o gerenciamento de clientes.
 * 
 * @author João Heckmann
 */
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService service;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerRepositoryImpl customerRepositoryImpl;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_update_customer() {
		Long requestId = 1L;

		CustomerEntity foundEntity = oneCustomer().build();
		foundEntity.setId(1L);
		Optional<CustomerEntity> optCustomer = Optional.of(foundEntity);

		CustomerEntity dto = oneCustomer() //
				.withId(1L) //
				.withName("Paulão") //
				.withPhone("123456789") //
				.withDocument("RG") //
				.build();

		when(customerRepository.findById(requestId)).thenReturn(optCustomer);
		when(customerRepository.save(dto)).thenReturn(dto);

		CustomerEntity result = service.updateCustomer(requestId, dto);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getDocument()).isEqualTo("RG");
		assertThat(result.getName()).isEqualTo("Paulão");
		assertThat(result.getPhone()).isEqualTo("123456789");
	}

	@Test
	public void should_throw_exception_when_customer_not_found() {
		Long requestId = 1L;
		Optional<CustomerEntity> empty = Optional.empty();

		CustomerEntity dto = oneCustomer() //
				.withId(1L) //
				.withName("Paulão") //
				.withPhone("123456789") //
				.withDocument("RG") //
				.build();

		when(customerRepository.findById(requestId)).thenReturn(empty);

		try {
			service.updateCustomer(requestId, dto);
			failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
		} catch (EntityNotFoundException e) {
			assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
		}
	}

	@Test
	public void should_return_customers_by_fields() {
		String name = "Paulinha";
		CustomerEntity customerEntity = oneCustomer().withName(name).build();
		List<CustomerEntity> customers = Arrays.asList(customerEntity);

		when(customerRepositoryImpl.findCustomersByFields(name, null, null)).thenReturn(customers);

		List<CustomerEntity> result = service.findCustomersByNameDocumentOrCellphone(name, null, null);

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo(name);
	}

}
