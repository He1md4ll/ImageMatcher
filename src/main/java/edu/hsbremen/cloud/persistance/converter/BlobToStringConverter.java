package edu.hsbremen.cloud.persistance.converter;

import com.google.gson.Gson;
import edu.hsbremen.cloud.dto.BlobDto;

import javax.persistence.AttributeConverter;

public class BlobToStringConverter implements AttributeConverter<BlobDto, String> {

    @Override
    public String convertToDatabaseColumn(BlobDto blobDto) {
        return new Gson().toJson(blobDto, BlobDto.class);
    }

    @Override
    public BlobDto convertToEntityAttribute(String s) {
        return new Gson().fromJson(s, BlobDto.class);
    }
}