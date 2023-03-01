package net.nanxu.mall.domain.user.repository;

import net.nanxu.mall.domain.user.repository.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TUser, Long> {
}