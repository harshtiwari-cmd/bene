package com.bank.retail.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        // Inverted mapping: true -> 0, false -> 1 (to match migrated data in Oracle)
        return attribute ? 0 : 1;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        // Inverted mapping: 0 -> true, 1 -> false (to match migrated data in Oracle)
        return dbData == 0;
    }
}

