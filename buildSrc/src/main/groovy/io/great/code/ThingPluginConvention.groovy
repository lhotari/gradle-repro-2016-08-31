package io.great.code


class ThingPluginConvention {

    // from https://discuss.gradle.org/t/strange-class-not-found-error-for-closure/18982

    // Expected version format is a sequence of numbers, separated by periods.  E.g. 1.2.3.4.5
    private List<Integer> parseVersion(String version) {
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
}
