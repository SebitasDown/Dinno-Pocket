package com.dinno.pocket.infrastructure.port.out.persistence.repository;

import java.math.BigDecimal;

public interface CategorySum {
    String getCategory();
    BigDecimal getTotal();
}
