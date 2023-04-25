package ru.aburenin.testtask.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Company_Prod_Info")
public class CompanyProdInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String companyName;
	private Boolean isFact;
	private String dataType;
	private Double liq;
	private Double oil;
	private Date date;
}
