package br.com.senior.hotelaria.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.senior.hotelaria.model.Estadia;
import br.com.senior.hotelaria.service.EstadiaService;

@Component
@Scope("prototype")
public class HospedeConverter {
	
	@Autowired
	private EstadiaService estadiaService;

	public List<EstadiaDTO> toRepresentation(List<Estadia> estadia) {
		return estadia.stream() //
				.map(this::toDTO) //
				.collect(Collectors.toList());

	}

	private EstadiaDTO toDTO(Estadia estadia) {
		EstadiaDTO estadiaDTO = new EstadiaDTO();
		estadiaDTO.setNome(estadia.getHospede().getNome());
		estadiaDTO.setDocumento(estadia.getHospede().getDocumento());
		estadiaDTO.setTelefone(estadia.getHospede().getTelefone());
		estadiaDTO.setValorGastoTotal(estadiaService.findValorTotalEstadias(estadia.getHospede().getId()));
		estadiaDTO.setValorUltimaEstadia(estadiaService.findValorUltimaEstadia(estadia.getHospede().getId()));
		return estadiaDTO;
	}

}
