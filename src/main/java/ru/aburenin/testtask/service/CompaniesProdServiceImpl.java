package ru.aburenin.testtask.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.aburenin.testtask.model.CompanyProdInfoDto;
import ru.aburenin.testtask.model.CompanyProdInfoEntity;
import ru.aburenin.testtask.model.ProductionByDateDto;
import ru.aburenin.testtask.repository.CompanyProdInfoRepository;

@Service
public class CompaniesProdServiceImpl implements CompaniesProdService {

	private static final int YEAR = 2023;
	private static final int MONTH = 3;
	private static final int DAYS_IN_MONTH = YearMonth.of(YEAR, MONTH).lengthOfMonth();

	@Autowired
	private CompanyProdInfoRepository companyProdInfoRepository;

	@Override
	public List<CompanyProdInfoDto> parse(MultipartFile file) {
		try (InputStream is = file.getInputStream()) {
			List<CompanyProdInfoEntity> infoList = new ArrayList<>();
			XSSFWorkbook wb = null;
			wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);

			for (int i = 3; i <= sheet.getLastRowNum(); i++)
				extractDataFromRow(infoList, sheet.getRow(i));

			wb.close();

			return StreamSupport.stream(companyProdInfoRepository.saveAll(infoList).spliterator(), false)
					.map(entity -> new CompanyProdInfoDto(entity)).collect(Collectors.toList());
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<ProductionByDateDto> getTotalSum() {
		return companyProdInfoRepository.getSumProductionByDate();
	}

	/**
	 * Разбирает ряд из файла, группируя данные по признаку fact/forecast и по типу
	 * данных (data1/data2). Также генерирует для каждой строки исходного ряда
	 * случайную дату в рамках определённого месяца и добавляет её к полученным при
	 * разборе данным.
	 */
	private void extractDataFromRow(List<CompanyProdInfoEntity> destinationList, Row row) {
		int dataRowIndex = row.getRowNum() - 3;
		Date date = generateDate();
		for (int i = 0; i < 4; i++) {
			CompanyProdInfoEntity companyProdInfo = new CompanyProdInfoEntity();
			companyProdInfo.setCompanyName(row.getCell(1).getStringCellValue());
			companyProdInfo.setDate(date);
			destinationList.add(companyProdInfo);
		}

		destinationList.get(dataRowIndex * 4).setIsFact(true);
		destinationList.get(dataRowIndex * 4).setDataType("data1");
		destinationList.get(dataRowIndex * 4).setLiq(row.getCell(2).getNumericCellValue());
		destinationList.get(dataRowIndex * 4).setOil(row.getCell(4).getNumericCellValue());

		destinationList.get(dataRowIndex * 4 + 1).setIsFact(true);
		destinationList.get(dataRowIndex * 4 + 1).setDataType("data2");
		destinationList.get(dataRowIndex * 4 + 1).setLiq(row.getCell(3).getNumericCellValue());
		destinationList.get(dataRowIndex * 4 + 1).setOil(row.getCell(5).getNumericCellValue());

		destinationList.get(dataRowIndex * 4 + 2).setIsFact(false);
		destinationList.get(dataRowIndex * 4 + 2).setDataType("data1");
		destinationList.get(dataRowIndex * 4 + 2).setLiq(row.getCell(6).getNumericCellValue());
		destinationList.get(dataRowIndex * 4 + 2).setOil(row.getCell(8).getNumericCellValue());

		destinationList.get(dataRowIndex * 4 + 3).setIsFact(false);
		destinationList.get(dataRowIndex * 4 + 3).setDataType("data2");
		destinationList.get(dataRowIndex * 4 + 3).setLiq(row.getCell(7).getNumericCellValue());
		destinationList.get(dataRowIndex * 4 + 3).setOil(row.getCell(9).getNumericCellValue());
	}

	private Date generateDate() {
		int day = RandomGenerator.getDefault().nextInt(1, DAYS_IN_MONTH);
		return Date.valueOf(LocalDate.of(YEAR, MONTH, day));
	}
}
