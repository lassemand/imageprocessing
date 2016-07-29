package com.ls.imgproc.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;

/**
 * Created by Lasse on 22/07/2016.
 */
public class JSONValidator {
    private static final JsonSchema SCHEMA;
    private JsonNode statistics;
    private final String fileType = ".json";
    private final String afterPrefix = "/schemes/";
    private final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    static {
        try {
            SCHEMA = JsonSchemaFactory.byDefault()
                    .getJsonSchema(SchemaVersion.DRAFTV4.getSchema());

        } catch (ProcessingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public JSONValidator() {
        //statistics = JsonLoader.fromResource("/other/google-json-api.json");
    }

    public boolean doValidate(String prefix, String input, ValidationType type) throws ProcessingException, IOException {
        StringBuilder builder = new StringBuilder(prefix + afterPrefix);

        switch (type){
            case STATISTICS:
                builder.append("statistics");
                break;
        }
        builder.append(fileType);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree(input);
        final JsonSchema schema = factory.getJsonSchema(builder.toString());
        return schema.validate(obj).isSuccess();
    }

    public enum ValidationType {
        STATISTICS
    }

}
