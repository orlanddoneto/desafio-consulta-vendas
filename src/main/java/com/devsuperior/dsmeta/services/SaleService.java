package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> relatorioVendas (String data_ini, String data_fim, String name, Pageable pageable){

		LocalDate ini;
		LocalDate fim;

		fim = logicaDataFim(data_fim);
		ini = logicaDataIni(data_ini,fim);
		name = logicaNome(name);
		validarDatas(ini,fim);

		Page<SaleMinDTO> result = repository.exibirRelatorioVendas(ini,fim,name,pageable);
		return result;
	}


	public List<SaleSummaryDTO> summaryVendas(String minDate, String maxDate){
		LocalDate ini;
		LocalDate fim;

		fim = logicaDataFim(maxDate);
		ini = logicaDataIni(minDate,fim);
		validarDatas(ini,fim);

		return  repository.exibirSummaryVendas(ini,fim);

	}


	private LocalDate logicaDataFim(String data_fim){
		LocalDate fim;
		if(data_fim == null || data_fim.isEmpty())
			fim = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		else
			fim = LocalDate.parse(data_fim);
		return fim;
	}

	private LocalDate logicaDataIni(String data_ini, LocalDate fim){
		LocalDate ini;
		if(data_ini == null || data_ini.isEmpty())
			ini = fim.minusYears(1L);
		else
			ini = LocalDate.parse(data_ini);
		return ini;
	}

	private String logicaNome (String name){
		if (name == null)
			name="";
		return name;
	}

	private void validarDatas(LocalDate ini, LocalDate fim) {
		if (ini.isAfter(fim)) {
			throw new IllegalArgumentException("Data inicial não pode ser maior que a data final");
		}
	}
}
