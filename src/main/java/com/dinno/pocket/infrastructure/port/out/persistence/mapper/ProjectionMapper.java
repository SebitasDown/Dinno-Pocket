package com.dinno.pocket.infrastructure.port.out.persistence.mapper;

import com.dinno.pocket.domain.model.Projection;
import com.dinno.pocket.infrastructure.port.out.persistence.entity.ProjectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectionMapper {

    @Mapping(target = "fixedExpensesRemaining", source = "remainingFixedExpenses")
    @Mapping(target = "estimatedVariableExpenses", source = "estimatedVariables")
    @Mapping(target = "projectionDate", source = "projectionDate")
    ProjectionEntity toEntity(Projection projection);

    @Mapping(target = "remainingFixedExpenses", source = "fixedExpensesRemaining")
    @Mapping(target = "estimatedVariables", source = "estimatedVariableExpenses")
    @Mapping(target = "balanceAtMoment", ignore = true)
    Projection toDomain(ProjectionEntity entity);
}
