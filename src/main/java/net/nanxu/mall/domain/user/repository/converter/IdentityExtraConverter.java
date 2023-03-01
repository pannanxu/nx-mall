package net.nanxu.mall.domain.user.repository.converter;

import jakarta.persistence.Converter;
import jakarta.persistence.AttributeConverter;
import net.nanxu.mall.infra.utils.Json;

@Converter(autoApply = true)
public class IdentityExtraConverter implements AttributeConverter<IdentityExtra, String> {

    @Override
    public String convertToDatabaseColumn(IdentityExtra extra) {
        try {
            return Json.objectToJson(extra);
        } catch (Exception ex) {
            return "{}";
        }
    }

    @Override
    public IdentityExtra convertToEntityAttribute(String extra) {
        try {
            return Json.jsonToObject(extra, IdentityExtra.class);
        } catch (Exception ex) {
            return new IdentityExtra();
        }
    }
}
