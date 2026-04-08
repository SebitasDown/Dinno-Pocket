package com.dinno.pocket.infrastructure.port.in.web.mapper;

import com.dinno.pocket.domain.model.Projection;
import com.dinno.pocket.infrastructure.port.in.web.dto.ProjectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebProjectionMapper {

    @Mapping(target = "maneuverMargin", expression = "java(projection.calculateManeuverMargin())")
    ProjectionResponse toResponse(Projection projection);
}
