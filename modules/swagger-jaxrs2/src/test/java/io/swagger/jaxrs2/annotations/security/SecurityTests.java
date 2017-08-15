package io.swagger.jaxrs2.annotations.security;

import io.swagger.jaxrs2.annotations.AbstractAnnotationTest;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.security.OAuthFlow;
import io.swagger.oas.annotations.security.OAuthFlows;
import io.swagger.oas.annotations.security.Scopes;
import io.swagger.oas.annotations.security.SecurityRequirement;
import io.swagger.oas.annotations.security.SecurityScheme;
import org.testng.annotations.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.ws.Response;

import static org.testng.Assert.assertEquals;

public class SecurityTests extends AbstractAnnotationTest {
    @Test
    public void testSecuritySheme() {
        String openApiYAML = readIntoYaml(SecurityTests.OAuth2SchemeOnClass.class);
        int start = openApiYAML.indexOf("components:");
        String extractedYAML = openApiYAML.substring(start, openApiYAML.length() - 1);
        String expectedYAML = "components:\n" +
                "  securitySchemes:\n" +
                "    myOauth2Security:\n" +
                "      type: oauth2\n" +
                "      name: myOauth2Security\n" +
                "      in: header\n" +
                "      flows:\n" +
                "        implicit:\n" +
                "          authorizationUrl: http://url.com/auth\n" +
                "          scopes:\n" +
                "            name: write:pets\n" +
                "            description: modify pets in your account";
        assertEquals(extractedYAML, expectedYAML);

    }

    @Test
    public void testSecurityRequirement() {
        String openApiYAML = readIntoYaml(SecurityTests.SecurityRequirementOnClass.class);
        String expectedYAML = "openapi: 3.0.0\n" +
                "paths:\n" +
                "  /:\n" +
                "    get:\n" +
                "      description: operation\n" +
                "      operationId: getResponse\n" +
                "      responses:\n" +
                "        default:\n" +
                "          description: no description\n" +
                "          content:\n" +
                "            '*/*':\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Response'\n" +
                "      security:\n" +
                "      - myOauth2Security:\n" +
                "        - 'write: read'\n" +
                "components:\n" +
                "  schemas:\n" +
                "    Response:\n" +
                "      type: object\n" +
                "      properties:\n" +
                "        context:\n" +
                "          type: object\n" +
                "          additionalProperties: {}\n" +
                "        cancelled:\n" +
                "          type: boolean\n" +
                "        done:\n" +
                "          type: boolean\n" +
                "  securitySchemes:\n" +
                "    myOauth2Security:\n" +
                "      type: oauth2\n" +
                "      name: myOauth2Security\n" +
                "      in: header\n" +
                "      flows:\n" +
                "        implicit:\n" +
                "          authorizationUrl: http://url.com/auth\n" +
                "          scopes:\n" +
                "            name: write:pets\n" +
                "            description: modify pets in your account\n";
        assertEquals(openApiYAML, expectedYAML);

    }

    @SecurityScheme(name = "myOauth2Security",
            type = "oauth2",
            in = "header",
            flows = @OAuthFlows(
                    implicit = @OAuthFlow(authorizationUrl = "http://url.com/auth",
                            scopes = @Scopes(name = "write:pets", description = "modify pets in your account"))))
    static class SecurityRequirementOnClass {
        @Operation(description = "operation")
        @SecurityRequirement(name = "myOauth2Security", scopes = "write: read")
        @GET
        @Path("/")
        public Response getResponse(){
            return null;
        }
    }

    @SecurityScheme(name = "myOauth2Security",
            type = "oauth2",
            in = "header",
            flows = @OAuthFlows(
                    implicit = @OAuthFlow(authorizationUrl = "http://url.com/auth",
                            scopes = @Scopes(name = "write:pets", description = "modify pets in your account"))))
    static class OAuth2SchemeOnClass {

    }
}
