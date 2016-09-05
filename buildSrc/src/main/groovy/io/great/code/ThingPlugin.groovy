package io.great.code

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.util.GradleVersion

class ThingPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.convention.plugins.generalUtilities = new ThingPluginConvention(project)
    }
}

class ThingPluginConvention {
    private final Project project

    ThingPluginConvention(Project project) {
        this.project = project
    }

    // from https://discuss.gradle.org/t/strange-class-not-found-error-for-closure/18982

    // Expected version format is a sequence of numbers, separated by periods.  E.g. 1.2.3.4.5
    private List<Integer> parseThingVersion(String version) {
        println "Parsing as version: $version"
        String[] sarr = version.split(/\./)
        Integer[] narr = sarr.collect() { String s ->  // <<< THIS CLOSURE IS NOT FOUND
            if (s == null) {
                return 0
            }
            // Pick leading digits with "\d+" to handle 2.4-20150222230019+0000
            def n = s =~ /\d+/
            if (n) {
                return n[0].toInteger()
            } else {
                return 0
            }
        }
        return narr.toList()
    }

    boolean ensureGradleVersion() {
        parseThingVersion(GradleVersion.current().version)
        true
    }
}