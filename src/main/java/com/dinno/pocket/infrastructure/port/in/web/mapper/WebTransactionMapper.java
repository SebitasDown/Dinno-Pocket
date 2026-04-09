package com.dinno.pocket.infrastructure.port.in.web.mapper;

import com.dinno.pocket.domain.model.Transaction;
import com.dinno.pocket.infrastructure.port.in.web.dto.TransactionRequest;
import com.dinno.pocket.infrastructure.port.in.web.dto.TransactionResponse;
import com.dinno.pocket.infrastructure.port.in.web.dto.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WebTransactionMapper {

    @Mapping(target = "type", expression = "java(com.dinno.pocket.domain.model.TransactionType.valueOf(request.getType().toUpperCase()))")
    Transaction toDomain(TransactionRequest request);

    @Mapping(target = "type", expression = "java(transaction.getType().name())")
    @Mapping(target = "createdAt", source = "createAt")
    TransactionResponse toResponse(Transaction transaction);
}
