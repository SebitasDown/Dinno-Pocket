package com.dinno.pocket.infrastructure.port.in.web.mapper;

import com.dinno.pocket.domain.model.Wallet;
import com.dinno.pocket.infrastructure.port.in.web.dto.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WebWalletMapper {

    @Mapping(target = "monthProgress", expression = "java(wallet.calculateMonthProgress())")
    WalletResponse toResponse(Wallet wallet);
}
