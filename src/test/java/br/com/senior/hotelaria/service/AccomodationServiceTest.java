package br.com.senior.hotelaria.service;

import static br.com.senior.hotelaria.builder.AccomodationEntityBuilder.oneAccomodation;
import static br.com.senior.hotelaria.builder.CustomerEntityBuilder.oneCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.senior.hotelaria.model.AccommodationEntity;
import br.com.senior.hotelaria.model.CustomerEntity;
import br.com.senior.hotelaria.repository.accomodation.AccomodationRepository;
import br.com.senior.hotelaria.repository.customer.CustomerRepository;

/**
 * Testes para o serviço de gerenciamento de estadias.
 * 
 * @author João Heckmann
 */
public class AccomodationServiceTest {

	@InjectMocks
	private AccomodationService service;

	@Mock
	private AccomodationRepository accomodationRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_do_check_in() {
		AccommodationEntity requestAccomodation = oneAccomodation().build();
		CustomerEntity requestCustomer = oneCustomer().build();
		requestAccomodation.setCustomer(requestCustomer);
		CustomerEntity foundCustomer = oneCustomer().withName("Paulão o bárbaro").build();

		AccommodationEntity savedAccomodation = oneAccomodation() //
				.withCustomer(foundCustomer) //
				.withActive(true).withAccomodationValue(0).build();

		when(customerRepository.findById(requestCustomer.getId())).thenReturn(Optional.ofNullable(foundCustomer));
		when(accomodationRepository.save(Mockito.any(AccommodationEntity.class))).thenReturn(savedAccomodation);

		AccommodationEntity result = service.doCheckIn(requestAccomodation);

		assertThat(result.getCheckInDate()).isNotNull();
		assertThat(result.getCheckOutDate()).isNull();
		assertThat(result.isGarageNeeded()).isEqualTo(false);
		assertThat(result.isActive()).isEqualTo(true);
		assertThat(result.getAccomodationValue()).isEqualTo(0);
		assertThat(result.getCustomer().getId()).isEqualTo(foundCustomer.getId());
	}

	@Test
	public void should_do_check_out_() {
		// cenario
		AccommodationEntity request = oneAccomodation().build();
		LocalDateTime doisDiasAtras = LocalDateTime.now().minus(2, ChronoUnit.DAYS);

		AccommodationEntity foundAccomodation = oneAccomodation() //
				.withCheckInDate(doisDiasAtras) //
				.build();

		when(accomodationRepository.findById(request.getId())).thenReturn(Optional.of(foundAccomodation));

		// acao
		service.doCheckOut(request);

		ArgumentCaptor<AccommodationEntity> argCaptor = ArgumentCaptor.forClass(AccommodationEntity.class);
		verify(accomodationRepository).save(argCaptor.capture());
		AccommodationEntity result = argCaptor.getValue();

		// validacao
		assertThat(result.isActive()).isEqualTo(false);
		assertThat(result.getCheckOutDate()).isNotNull();
	}

}
