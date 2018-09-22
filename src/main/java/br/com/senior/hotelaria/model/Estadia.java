package br.com.senior.hotelaria.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "estadia")
public class Estadia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "estadia_seq")
	@SequenceGenerator(name = "estadia_seq", sequenceName = "estadia_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "hospede_id")
	private Hospede hospede;
	
	@NotNull
	private LocalDateTime dataEntrada;

	private LocalDateTime dataSaida;

	@NotNull
	private boolean adicionalVeiculo;
	
	@NotNull
	private boolean ativo;
	
	private double valorEstadia;
	
	public Estadia() {
		
	}
	
	public Estadia(@NotNull Hospede hospedeId, @NotNull LocalDateTime dataEntrada, LocalDateTime dataSaida,
			@NotNull boolean adicionalVeiculo, @NotNull boolean ativo) {
		this.hospede = hospedeId;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.adicionalVeiculo = adicionalVeiculo;
		this.ativo = ativo;
	}

	public void setHospedeId(Hospede hospedeId) {
		this.hospede = hospedeId;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}

	public boolean isAdicionalVeiculo() {
		return adicionalVeiculo;
	}

	public void setAdicionalVeiculo(boolean adicionalVeiculo) {
		this.adicionalVeiculo = adicionalVeiculo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}

	public double getValorEstadia() {
		return valorEstadia;
	}

	public void setValorEstadia(double valorEstadia) {
		this.valorEstadia = valorEstadia;
	}
	
	

}
