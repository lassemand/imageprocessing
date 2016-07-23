package com.ls.imgproc.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Lasse on 22/07/2016.
 */
public class JSONValidator {
    private static final JsonSchema SCHEMA;
    private JsonNode statistics;

    static {
        try {
            SCHEMA = JsonSchemaFactory.byDefault()
                    .getJsonSchema(SchemaVersion.DRAFTV4.getSchema());

        } catch (ProcessingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public JSONValidator() throws IOException {
        URL location = JSONValidator.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
        statistics = JsonLoader.fromResource("/other/google-json-api.json");
    }

    public boolean doValidate(String input){

        return true;
    }

}
