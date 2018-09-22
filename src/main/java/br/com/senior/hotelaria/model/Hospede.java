package br.com.senior.hotelaria.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "hospede")
public class Hospede {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "hospede_seq")
	@SequenceGenerator(name = "hospede_seq", sequenceName = "hospede_seq", allocationSize = 1, initialValue = 1) 
	private Long id;
	
	@NotNull
	@Size(max = 50)
	private String nome;
	
	@NotNull
	@Size(max = 50)
	private String documento;
	
	@NotNull
	@Size(max = 50)
	private String telefone;
	
	public Hospede() {
	}
	
	public Long getId() {
		return id;
	}

	public Hospede(String nome, String documento, String telefone) {
		this.nome = nome;
		this.documento = documento;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
