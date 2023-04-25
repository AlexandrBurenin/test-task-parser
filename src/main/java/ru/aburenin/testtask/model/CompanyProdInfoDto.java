package ru.aburenin.testtask.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProdInfoDto {
	private String companyName;
	private Boolean isFact;
	private String dataType;
	private Double liq;
	private Double oil;
	private Date date;

	public CompanyProdInfoDto(CompanyProdInfoEntity en) {
		this.companyName = en.getCompanyName();
		this.isFact = en.getIsFact();
		this.dataType = en.getDataType();
		this.liq = en.getLiq();
		this.oil = en.getOil();
		this.date = en.getDate();
	}
}
