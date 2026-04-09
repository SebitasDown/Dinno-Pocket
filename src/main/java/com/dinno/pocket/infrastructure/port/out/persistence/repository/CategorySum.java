package com.dinno.pocket.infrastructure.port.out.persistence.repository;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CategorySum {
    private String category;
    private BigDecimal total;
}
