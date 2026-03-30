package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam (name = "minDate",required = false)
  	String data_ini, @RequestParam (name = "maxDate", required = false)
	String data_fim, @RequestParam(name="name",required = false) String name, Pageable pageable){

		Page<SaleMinDTO> result = service.relatorioVendas(data_ini,data_fim,name,pageable);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
	@RequestParam(name = "minDate",required = false) String minDate,
   	@RequestParam(name = "maxDate", required = false) String maxDate)

	{
		List<SaleSummaryDTO> result = service.summaryVendas(minDate,maxDate);
		return ResponseEntity.ok(result);
	}
}
