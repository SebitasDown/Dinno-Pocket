package com.dinno.pocket.infrastructure.port.out.persistence.mapper;

import com.dinno.pocket.domain.model.Wallet;
import com.dinno.pocket.infrastructure.port.out.persistence.entity.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {

    Wallet toDomain(WalletEntity entity);
    
    WalletEntity toEntity(Wallet domain);
}
