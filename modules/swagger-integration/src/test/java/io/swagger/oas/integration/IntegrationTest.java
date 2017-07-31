package io.swagger.oas.integration;

import io.swagger.oas.models.OpenAPI;
import io.swagger.oas.models.info.Info;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertNotNull;

public class IntegrationTest {

    // TODO after implementation of generic reader and possibly generic scanner if we reintroduce "api" or similar annotation
    private final Set expectedKeys = new HashSet<String>(Arrays.asList("/packageA", "/packageB"));

    @Test(description = "initialize a context and read")
    public void shouldInitialize() throws Exception{

        OpenApiConfiguration config = new OpenApiConfiguration()
                .resourcePackages(Stream.of("com.my.project.resources", "org.my.project.resources").collect(Collectors.toSet()))
                .openApi(new OpenAPI().info(new Info().description("TEST INFO DESC")));

        OpenApiContext ctx = new GenericOpenApiContext()
                .openApiConfiguration(config)
                //.openApiReader()
                //.openApiReader()
                .init();
        OpenAPI openApi = ctx.read();

        assertNotNull(openApi);
    }

}
