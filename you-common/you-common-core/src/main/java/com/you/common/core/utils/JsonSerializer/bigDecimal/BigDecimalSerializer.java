package com.you.common.core.utils.JsonSerializer.bigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;


public class BigDecimalSerializer extends JsonSerializer<BigDecimal> implements ContextualSerializer {

    private final DecimalFormat df;

    public BigDecimalSerializer() {
        this.df = new DecimalFormat("#0.00"); // Default
    }

    public BigDecimalSerializer(int scale) {
        this.df = new DecimalFormat("#0." + generateZeros(scale));
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value.toString() != null) {
            Double dd = Double.parseDouble(value.toString());
            gen.writeString(df.format(dd));
        } else{
            gen.writeString(value.toString());
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonDecimal annotation = beanProperty.getAnnotation(JsonDecimal.class);
        if (annotation != null) {
            return new BigDecimalSerializer(annotation.scale());
        }
        return this;
    }

    private String generateZeros(int scale) {
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < scale; i++) {
            zeros.append("0");
        }
        return zeros.toString();
    }
}
