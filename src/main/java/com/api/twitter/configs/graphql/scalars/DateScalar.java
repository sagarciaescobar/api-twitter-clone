package com.api.twitter.configs.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateScalar {

    public static final GraphQLScalarType DATE = GraphQLScalarType.newScalar()
            .name("Date")
            .description("Date scalar")
            .coercing(new Coercing<Date, String>() {
                @Override
                public String serialize(final @NotNull Object dataFetcherResult) {
                    if (dataFetcherResult instanceof Date) {
                        return dataFetcherResult.toString();
                    } else {
                        throw new CoercingSerializeException("Expected a Date object.");
                    }
                }

                @Override
                public Date parseValue(final @NotNull Object input) {
                    try {
                        if (input instanceof String) {
                            return Date.from(Instant.parse((String) input));
                        } else {
                            throw new RuntimeException("Expected a String");
                        }
                    } catch (DateTimeParseException e) {
                        throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
                        );
                    }
                }

                @Override
                public Date parseLiteral(final @NotNull Object input) {
                    if (input instanceof StringValue) {
                        try {
                            return Date.from(Instant.parse((CharSequence) input));
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException(e);
                        }
                    } else {
                        throw new CoercingParseLiteralException("Expected a StringValue.");
                    }
                }
            }).build();
}
