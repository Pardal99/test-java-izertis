package com.enterprise.ppardal.shared.config.deserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StrictOffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException {

        // Ensure the input is a string token
        if (!parser.hasToken(JsonToken.VALUE_STRING)) {
            throw ctxt.weirdStringException(
                    parser.getText(),
                    OffsetDateTime.class,
                    "application_date must be an ISO-8601 string with timezone");
        }

        try {
            // Parse the OffsetDateTime from the string. If not valid, a
            // DateTimeParseException will be thrown enforcing strict ISO-8601 format with
            // timezone
            return OffsetDateTime.parse(parser.getText());
        } catch (DateTimeParseException ex) {
            throw ctxt.weirdStringException(
                    parser.getText(),
                    OffsetDateTime.class,
                    "Invalid ISO-8601 date-time format with timezone");
        }
    }
}
