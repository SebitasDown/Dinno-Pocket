package com.dinno.pocket.infrastructure.port.out.persistence.mapper;

import com.dinno.pocket.domain.model.Transaction;
import com.dinno.pocket.infrastructure.port.out.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "type", expression = "java(transaction.getType().name())")
    @Mapping(target = "createdAt", source = "createAt")
    @Mapping(target = "description", source = "description")
    TransactionEntity toEntity(Transaction transaction);

    @Mapping(target = "type", expression = "java(com.dinno.pocket.domain.model.TransactionType.valueOf(entity.getType()))")
    @Mapping(target = "createAt", source = "createdAt")
    @Mapping(target = "description", source = "description")
    Transaction toDomain(TransactionEntity entity);
}
