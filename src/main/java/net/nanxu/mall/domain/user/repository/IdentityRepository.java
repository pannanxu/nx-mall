package net.nanxu.mall.domain.user.repository;

import net.nanxu.mall.domain.user.repository.entity.TIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRepository extends JpaRepository<TIdentity, Long> {

}