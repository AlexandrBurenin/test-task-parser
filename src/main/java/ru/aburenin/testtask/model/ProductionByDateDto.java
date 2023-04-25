package ru.aburenin.testtask.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionByDateDto {
	Date date;
	Boolean isFact;
	String dataType;
	Double liqTotal;
	Double oilTotal;
}
