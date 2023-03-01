package net.nanxu.mall.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import net.nanxu.mall.domain.user.repository.IdentityRepository;
import net.nanxu.mall.domain.user.repository.entity.TIdentity;
import net.nanxu.mall.domain.user.repository.enums.IdentityType;
import net.nanxu.mall.domain.user.service.IdentityService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * IdentityServiceImpl.
 *
 * @author: pan
 **/
@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    private final IdentityRepository repository;

    @Override
    public Optional<TIdentity> fetchIdentity(String identifier, IdentityType identityType) {
        TIdentity identity = new TIdentity();
        identity.setIdentifier(identifier);
        identity.setIdentityType(identityType.getType());
        Example<TIdentity> example = Example.of(identity);
        return repository.findOne(example);
    }
}
