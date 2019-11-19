package com.sp.plugin.log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.owasp.encoder.Encode;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Plugin(name = "LogCrlfConverter", category = "Converter")
@ConverterKeys({ "crlf" })
@Order(51)
public class LogCrlfConverter extends LogEventPatternConverter {
    private final String pattern;
    
    private static final String EMPTY = "";

    private LogCrlfConverter(String name, String style, String[] options) {
        super(name, style);
        if (options != null && options.length > 0) {
            this.pattern = options[0];
        } else {
            this.pattern = null;
        }
    }

    public static LogCrlfConverter newInstance(String[] options) {
        return new LogCrlfConverter("crlf", Thread.currentThread().getName(), options);
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        if (this.pattern == null) {
            String message = event.getMessage().getFormattedMessage();
            String sanitizedMessage = getMessage(message);
            outputMessage.append(sanitizedMessage);
        } else {
            PatternParser parser = new PatternParser(null, "Converter", LogEventPatternConverter.class);
            List<PatternFormatter> formatters = parser.parse(this.pattern);

            StringBuilder groupBuilder = new StringBuilder();
            for (PatternFormatter formatter : formatters) {
                formatter.format(event, groupBuilder);
            }
            String sanitizedMessage = getMessage(groupBuilder.toString());
            outputMessage.append(sanitizedMessage);
        }
    }

    public static String getMessage(final String message) {
        if (message != null && !message.isEmpty()) {
            String cleanMsg = Encode.forJava(message);
            if (cleanMsg.contains("\"")) {
                // preserve the double quotes
                cleanMsg = cleanMsg.replace("\\\"", "\"");
            }
            return cleanMsg;
        } else {
            return EMPTY;
        }
    }
}
