package net.nanxu.mall.infra;

/**
 * AnonymousUserConst.
 *
 * @author: pan
 **/
public interface AnonymousUserConst {
    String PRINCIPAL = "anonymousUser";

    String Role = "anonymous";

    static boolean isAnonymousUser(String principal) {
        return PRINCIPAL.equals(principal);
    }
}
