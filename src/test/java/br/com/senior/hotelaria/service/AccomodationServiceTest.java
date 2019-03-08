package br.com.senior.hotelaria.service;

import static br.com.senior.hotelaria.builder.AccomodationEntityBuilder.oneAccomodation;
import static br.com.senior.hotelaria.builder.CustomerEntityBuilder.oneCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
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
	public void should_do_check_out() {
		AccommodationEntity request = oneAccomodation().build();
		LocalDateTime twoDaysAgo = LocalDateTime.now().minus(2, ChronoUnit.DAYS);

		AccommodationEntity foundAccomodation = oneAccomodation() //
				.withCheckInDate(twoDaysAgo) //
				.build();

		when(accomodationRepository.findById(request.getId())).thenReturn(Optional.of(foundAccomodation));

		service.doCheckOut(request);

		ArgumentCaptor<AccommodationEntity> argCaptor = ArgumentCaptor.forClass(AccommodationEntity.class);
		verify(accomodationRepository).save(argCaptor.capture());
		AccommodationEntity result = argCaptor.getValue();

		assertThat(result.isActive()).isEqualTo(false);
		assertThat(result.getCheckOutDate()).isNotNull();
	}
	
	@Test
	public void should_not_do_checkout_when_already_has_checkout_date() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime twoDaysAgo = now.minus(2, ChronoUnit.DAYS);

		AccommodationEntity request = oneAccomodation() //
				.withCheckInDate(twoDaysAgo) //
				.withCheckOutDate(now) //
				.build();
		
		when(accomodationRepository.findById(request.getId())).thenReturn(Optional.of(request));
		
		try {
			service.doCheckOut(request);
			Assertions.failBecauseExceptionWasNotThrown(IllegalStateException.class);
		} catch (IllegalStateException e) {
			assertThat(e.getMessage()).isEqualTo("Estadia já finalizada");
		}
	}
	
	@Test
	public void should_throw_exception_when_entity_is_not_found() {
		LocalDateTime now = LocalDateTime.now();
		AccommodationEntity request = oneAccomodation() //
				.withCustomer(oneCustomer().withName("cliente").build()) //
				.withCheckOutDate(now) //
				.build();
		
		when(accomodationRepository.findById(request.getId())).thenReturn(Optional.empty());
		
		try {
			service.doCheckOut(request);
			Assertions.failBecauseExceptionWasNotThrown(EntityNotFoundException.class);
		} catch (EntityNotFoundException e) {
			assertThat(e.getMessage()).contains(request.getCustomer().getName());
		}
	}
	
	@Test
	public void should_add_one_more_day_if_checkout_is_after_noon() {
		LocalDateTime checkIn = LocalDateTime.of(LocalDate.of(2019, 3, 6), LocalTime.of(16, 00));
		LocalDateTime checkOutDate = LocalDateTime.of(LocalDate.of(2019, 3, 7), LocalTime.of(16, 31));
		
		double result = service.calculateTotalAccomodationValue(checkIn, checkOutDate, true);
		
		assertThat(result).isEqualTo(270);
	}
	
	@Test
	public void should_add_one_day_if_checkout_is_on_the_same_day() {
		LocalDateTime checkIn = LocalDateTime.of(LocalDate.of(2019, 3, 9), LocalTime.of(16, 00));
		LocalDateTime checkOutDate = LocalDateTime.of(LocalDate.of(2019, 3, 9), LocalTime.of(16, 31));
		
		double result = service.calculateTotalAccomodationValue(checkIn, checkOutDate, true);
		
		assertThat(result).isEqualTo(170);
	}
	
}
