package net.nanxu.mall.domain.user.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IdentityType.
 *
 * @author: pan
 **/
@AllArgsConstructor
@Getter
public enum IdentityType {
    
    username(0),
    phone(10)
    ;
    
    private final int type;
    
}
