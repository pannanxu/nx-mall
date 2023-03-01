package net.nanxu.mall.domain.user.service;

import net.nanxu.mall.domain.user.repository.entity.TIdentity;
import net.nanxu.mall.domain.user.repository.enums.IdentityType;

import java.util.Optional;

/**
 * IdentityService.
 *
 * @author: pan
 **/
public interface IdentityService {

    Optional<TIdentity> fetchIdentity(String identifier, IdentityType identityType);

}
