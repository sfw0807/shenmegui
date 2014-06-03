package org.zju.electric_factory.entity.converter.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    private static final SimpleDateFormat dateFormater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Date date, JsonGenerator gen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String formatedDate = dateFormater.format(date);
        gen.writeString(formatedDate);
    }
}
