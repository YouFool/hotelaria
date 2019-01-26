package br.com.senior.hotelaria.converter;

/**
 * DTO que representa um cliente e seus gastos com hospedagem.
 * 
 * @author João Heckmann
 *
 */
public class CustomerDTO {

	private String name;
	private String document;
	private String phone;
	private Double lastLodgingValue;
	private Double totalRevenue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getLastLodgingValue() {
		return lastLodgingValue;
	}

	public void setLastLodgingValue(Double lastLodgingValue) {
		this.lastLodgingValue = lastLodgingValue;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

}
