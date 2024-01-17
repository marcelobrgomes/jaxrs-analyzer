package com.sebastian_daschner.jaxrs_analyzer.model.rest;

import com.sebastian_daschner.jaxrs_analyzer.utils.StringUtils;

/**
 * A REST project.
 *
 * @author Sebastian Daschner
 */
public class Project {

    private final String name;
    private final String version;
    private final Resources resources;
    private final String basePath;

    public Project(final String name, final String version, final Resources resources) {
        this(name, version, resources, null);
    }

    public Project(final String name, final String version, final Resources resources, final String basePath) {
        StringUtils.requireNonBlank(name);
        StringUtils.requireNonBlank(version);
        this.name = name;
        this.version = version;
        this.resources = resources;
        this.basePath = basePath;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Resources getResources() {
        return resources;
    }

    public String getBasePath() {
        return basePath;
    }
}
