package com.nobblecrafts.challenge.devsecopssr.config.test.core.io;

import com.nobblecrafts.challenge.devsecopssr.config.test.annotation.PayloadSource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class PayloadArgumentsProvider implements ArgumentsProvider {

    private final ResourceLoader resourceLoader;
    private final String resourcePath;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        PayloadSource payloadSource = context.getRequiredTestClass().getAnnotation(PayloadSource.class);
        String path = payloadSource != null ? payloadSource.value() : resourcePath;
        Resource[] resources = new Resource[]{resourceLoader.getResource("classpath:" + path)};

        return Stream.of(resources)
                .flatMap(resource -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                        return reader.lines().map(Arguments::of);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to read payload file", e);
                    }
                });
    }

}