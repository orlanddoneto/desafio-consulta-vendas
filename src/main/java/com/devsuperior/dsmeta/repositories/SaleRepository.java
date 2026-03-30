package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(
            value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(sa.id, se.name, sa.amount, sa.date) " +
                    "FROM Sale sa JOIN sa.seller se " +
                    "WHERE sa.date BETWEEN :data_ini AND :data_fim " +
                    "AND UPPER(se.name) LIKE UPPER(CONCAT('%',:nome,'%'))"
    )
    Page<SaleMinDTO> exibirRelatorioVendas(LocalDate data_ini, LocalDate data_fim, String nome, Pageable pageable);

    @Query(
            value = "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(se.name, SUM(sa.amount)) " +
                    "FROM Sale sa JOIN sa.seller se " +
                    "WHERE sa.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY se.name"

    )
    List<SaleSummaryDTO> exibirSummaryVendas(LocalDate minDate, LocalDate maxDate);

}
