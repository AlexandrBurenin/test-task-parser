package ru.aburenin.testtask.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.aburenin.testtask.model.CompanyProdInfoDto;
import ru.aburenin.testtask.model.ProductionByDateDto;

public interface CompaniesProdService {

	List<CompanyProdInfoDto> parse(MultipartFile file);

	List<ProductionByDateDto> getTotalSum();
}
