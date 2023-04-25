package ru.aburenin.testtask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.aburenin.testtask.model.CompanyProdInfoEntity;
import ru.aburenin.testtask.model.ProductionByDateDto;

@Repository
public interface CompanyProdInfoRepository extends CrudRepository<CompanyProdInfoEntity, Long> {
	
	@Query("SELECT NEW ru.aburenin.testtask.model.ProductionByDateDto(c.date, c.isFact, c.dataType, SUM(c.liq), SUM(c.oil)) "
			+ "FROM CompanyProdInfoEntity c "
			+ "GROUP BY c.date, c.isFact, c.dataType")
	public List<ProductionByDateDto> getSumProductionByDate();
}
