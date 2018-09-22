package br.com.senior.hotelaria.converter;

public class EstadiaDTO {

	private String nome;
	private String documento;
	private String telefone;
	private Double valorUltimaEstadia;
	private Double valorGastoTotal;

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

	public Double getValorUltimaEstadia() {
		return valorUltimaEstadia;
	}

	public void setValorUltimaEstadia(Double valorUltimaEstadia) {
		this.valorUltimaEstadia = valorUltimaEstadia;
	}

	public Double getValorGastoTotal() {
		return valorGastoTotal;
	}

	public void setValorGastoTotal(Double valorGastoTotal) {
		this.valorGastoTotal = valorGastoTotal;
	}

}
