package ru.aburenin.testtask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.aburenin.testtask.model.CompanyProdInfoDto;
import ru.aburenin.testtask.model.ProductionByDateDto;
import ru.aburenin.testtask.service.CompaniesProdService;

@RestController
@RequestMapping("/parsing")
public class CompaniesProdController {

	@Autowired
	private CompaniesProdService companiesProdService;

	/**
	 * Принимает файл формата .xlsx, парсит его и сохраняет результат в БД.
	 * Возвращает то, что сохранилось в БД.
	 */
	@PostMapping(path = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<CompanyProdInfoDto> parse(@RequestParam("file") MultipartFile file) {
		return companiesProdService.parse(file);
	}

	/**
	 * Возвращает суммы Qoil и Qliq, сгруппированные по датам и типам данных
	 * (data1/data2, fact/forecast).
	 */
	@GetMapping("/total-sum")
	public List<ProductionByDateDto> getTotalSum() {
		return companiesProdService.getTotalSum();
	}

}
