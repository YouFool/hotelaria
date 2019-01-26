package br.com.senior.hotelaria.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Representa uma estadia de um {@link CustomerEntity} cliente.
 * 
 * @author João Heckmann.
 *
 */
@Entity
@Table(name = "accommodation")
public class AccommodationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "accomodation_seq")
	@SequenceGenerator(name = "accomodation_seq", sequenceName = "accomodation_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

	@NotNull
	@DateTimeFormat
	@Column(name = "check_in_date")
	private LocalDateTime checkInDate;

	@DateTimeFormat
	@Column(name = "check_out_date")
	private LocalDateTime checkOutDate;

	@NotNull
	@Column(name = "garage_needed")
	private boolean isGarageNeeded;

	@NotNull
	@Column(name = "active")
	private boolean isActive;

	@Column(name = "accomodation_value")
	private double accomodationValue;

	public AccommodationEntity() {
	}

	public AccommodationEntity(@NotNull CustomerEntity hospedeId, @NotNull LocalDateTime dataEntrada, LocalDateTime dataSaida,
			@NotNull boolean adicionalVeiculo, @NotNull boolean ativo) {
		this.customer = hospedeId;
		this.checkInDate = dataEntrada;
		this.checkOutDate = dataSaida;
		this.isGarageNeeded = adicionalVeiculo;
		this.isActive = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public boolean isGarageNeeded() {
		return isGarageNeeded;
	}

	public void setGarageNeeded(boolean isGarageNeeded) {
		this.isGarageNeeded = isGarageNeeded;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public double getAccomodationValue() {
		return accomodationValue;
	}

	public void setAccomodationValue(double accomodationValue) {
		this.accomodationValue = accomodationValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AccommodationEntity other = (AccommodationEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Accommodation [id=" + id + ", customer=" + customer + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", isGarageNeeded=" + isGarageNeeded + ", isActive=" + isActive
				+ ", accomodationValue=" + accomodationValue + "]";
	}

}
