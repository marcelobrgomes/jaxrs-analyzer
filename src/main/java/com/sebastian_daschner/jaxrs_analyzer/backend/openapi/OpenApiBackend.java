package com.sebastian_daschner.jaxrs_analyzer.backend.openapi;

import com.sebastian_daschner.jaxrs_analyzer.backend.Backend;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.Project;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.ResourceMethod;
import com.sebastian_daschner.jaxrs_analyzer.model.rest.Resources;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * A backend which produces a OpenAPI 3.1.0 yaml representation of the resources.
 */
public class OpenApiBackend implements Backend {
    private static final String NAME = "OpenAPI";
    private final OutputBuilder builder = new OutputBuilder();
    private Resources resources;

    @Override
    public byte[] render(Project project) {
        resources = project.getResources();

        appendHeader(project);
        appendPaths();

        return serializeOutput();
    }

    private void appendPaths() {
        final OutputBuilder paths = new OutputBuilder();
        resources.getResources().stream().sorted().forEach(s -> paths.put('/' + s, buildPathDefinition(s)));
        builder.put("paths", paths.build());
    }

    private Map<String, Object> buildPathDefinition(final String s) {
        final OutputBuilder methods = new OutputBuilder();
        consolidateMultipleMethodsForSamePath(s)
                .values().stream()
                .sorted(comparing(ResourceMethod::getMethod))
                .forEach(m ->
                        methods.put(m.getMethod().toString().toLowerCase(), "buildForMethod(m, s)"));
        return methods.build();
    }

    private Map<String, ResourceMethod> consolidateMultipleMethodsForSamePath(String s) {
        return resources.getMethods(s).stream().collect(
                Collectors.groupingBy(m->m.getMethod().toString().toLowerCase(),
                        Collectors.reducing(new ResourceMethod(), ResourceMethod::combine))
        );
    }

    private void appendHeader(Project project) {
        builder.put("openapi", "3.1.0")
                .put("info", new OutputBuilder()
                        .put("title", project.getName())
                        .put("version", project.getVersion())
                        .build());
    }

    @Override
    public String getName() {
        return NAME;
    }

    private byte[] serializeOutput() {
        return new Yaml().dumpAsMap(builder.build()).getBytes();
    }

    private static class OutputBuilder {
        private final Map<String, Object> output = new LinkedHashMap<>();

        OutputBuilder put(String key, Object value) {
            output.put(key, value);
            return this;
        }

        Map<String, Object> build() {
            return output;
        }
    }
}
