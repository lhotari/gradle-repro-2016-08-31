package io.great.code

import org.gradle.api.Plugin
import org.gradle.api.Project

class ThingPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.convention.plugins.put('thing', new ThingPluginConvention())
    }
}
