# gradle-repro-2016-08-31

Attempts to reproduce [GRADLE-3526](https://issues.gradle.org/browse/GRADLE-3526)

### Known failing scenarios

#### Closure executed in doLast of a task, class is in buildSrc

- example is `ClasspathManifestPatcher` in gradle build-tool source code

```
Caused by: java.lang.NoClassDefFoundError: ClasspathManifestPatcher
    at build_a47xsap72du1w41ek88y75wup$_run_closure8$_closure50.doCall(/home/tcagent2/agent/work/1c72cb73edd79150/build.gradle:292)
    at org.gradle.api.internal.AbstractTask$ClosureTaskAction.execute(AbstractTask.java:587)
    at org.gradle.api.internal.AbstractTask$ClosureTaskAction.execute(AbstractTask.java:568)
    at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeAction(ExecuteActionsTaskExecuter.java:80)
    at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeActions(ExecuteActionsTaskExecuter.java:61)
    ... 58 more
Caused by: java.lang.ClassNotFoundException: ClasspathManifestPatcher
    ... 63 more
```

#### Closure added to ext in root project to allprojects, executed in subprojects

- example is classycle.gradle in gradle build-tool source code
  
```
Caused by: java.lang.NoClassDefFoundError: classycle_7rm47nqjbx6087ticaiwygcd7$_run_closure1$_closure2$_closure3
         at classycle_7rm47nqjbx6087ticaiwygcd7$_run_closure1$_closure2.doCall(/Users/bmuschko/dev/projects/gradle/gradle/classycle.gradle:5)
         at classycle_7rm47nqjbx6087ticaiwygcd7$_run_closure1$_closure2.doCall(/Users/bmuschko/dev/projects/gradle/gradle/classycle.gradle)
         at org.gradle.internal.metaobject.BeanDynamicObject$MetaClassAdapter.invokeMethod(BeanDynamicObject.java:382)
         at org.gradle.internal.metaobject.BeanDynamicObject.invokeMethod(BeanDynamicObject.java:170)
         at org.gradle.internal.metaobject.MixInClosurePropertiesAsMethodsDynamicObject.invokeMethod(MixInClosurePropertiesAsMethodsDynamicObject.java:43)
         at org.gradle.internal.metaobject.AbstractDynamicObject.invokeMethod(AbstractDynamicObject.java:163)
         at org.gradle.groovy.scripts.BasicScript.methodMissing(BasicScript.java:83)
         at base_services_98klnifgk7wbci5yjpxp7ct4t.run(/Users/bmuschko/dev/projects/gradle/subprojects/base-services/base-services.gradle:18)
         at org.gradle.groovy.scripts.internal.DefaultScriptRunnerFactory$ScriptRunnerImpl.run(DefaultScriptRunnerFactory.java:90)
         ... 58 more
```

#### Closure in plugin convention class

- example in https://discuss.gradle.org/t/strange-class-not-found-error-for-closure/18982

```
Caused by: java.lang.NoClassDefFoundError: ThingUtilsPluginConvention$_parseVersion_closure3
        at ThingUtilsPluginConvention.parseVersion(/Users/scott/dev/ThingSDK/BuildScripts/ThingUtilsPlugin.gradle:270)
        at org.gradle.internal.metaobject.BeanDynamicObject$MetaClassAdapter.invokeMethod(BeanDynamicObject.java:382)
        at org.gradle.internal.metaobject.BeanDynamicObject.invokeMethod(BeanDynamicObject.java:170)
        at org.gradle.api.internal.plugins.DefaultConvention$ExtensionsDynamicObject.invokeMethod(DefaultConvention.java:220)
        at org.gradle.internal.metaobject.CompositeDynamicObject.invokeMethod(CompositeDynamicObject.java:96)
        at org.gradle.internal.metaobject.MixInClosurePropertiesAsMethodsDynamicObject.invokeMethod(MixInClosurePropertiesAsMethodsDynamicObject.java:30)
        at org.gradle.internal.metaobject.AbstractDynamicObject.invokeMethod(AbstractDynamicObject.java:163)
        at org.gradle.api.internal.project.DefaultProject_Decorated.invokeMethod(Unknown Source)
        at ThingUtilsPluginConvention.ensureGradleVersion(/Users/scott/dev/ThingSDK/BuildScripts/ThingUtilsPlugin.gradle:325)
        at ThingUtilsPluginConvention$ensureGradleVersion.callCurrent(Unknown Source)
        at ThingUtilsPluginConvention.ensureGradleVersion(/Users/scott/dev/ThingSDK/BuildScripts/ThingUtilsPlugin.gradle:321)
        at org.gradle.internal.metaobject.BeanDynamicObject$MetaClassAdapter.invokeMethod(BeanDynamicObject.java:382)
        at org.gradle.internal.metaobject.BeanDynamicObject.invokeMethod(BeanDynamicObject.java:170)
        at org.gradle.api.internal.plugins.DefaultConvention$ExtensionsDynamicObject.invokeMethod(DefaultConvention.java:220)
        at org.gradle.internal.metaobject.CompositeDynamicObject.invokeMethod(CompositeDynamicObject.java:96)
        at org.gradle.api.internal.ExtensibleDynamicObject$InheritedDynamicObject.invokeMethod(ExtensibleDynamicObject.java:245)
        at org.gradle.internal.metaobject.CompositeDynamicObject.invokeMethod(CompositeDynamicObject.java:96)
        at org.gradle.api.internal.ExtensibleDynamicObject$InheritedDynamicObject.invokeMethod(ExtensibleDynamicObject.java:245)
        at org.gradle.internal.metaobject.CompositeDynamicObject.invokeMethod(CompositeDynamicObject.java:96)
        at org.gradle.api.internal.ExtensibleDynamicObject$InheritedDynamicObject.invokeMethod(ExtensibleDynamicObject.java:245)
        at org.gradle.internal.metaobject.CompositeDynamicObject.invokeMethod(CompositeDynamicObject.java:96)
        at org.gradle.internal.metaobject.MixInClosurePropertiesAsMethodsDynamicObject.invokeMethod(MixInClosurePropertiesAsMethodsDynamicObject.java:30)
        at org.gradle.internal.metaobject.AbstractDynamicObject.invokeMethod(AbstractDynamicObject.java:163)
        at org.gradle.api.internal.project.DefaultProject_Decorated.invokeMethod(Unknown Source)
        at ThingSubprojectPlugin.apply(/Users/scott/dev/ThingSDK/BuildScripts/ThingSubprojectPlugin.gradle:19)
        at ThingSubprojectPlugin.apply(/Users/scott/dev/ThingSDK/BuildScripts/ThingSubprojectPlugin.gradle)
        at org.gradle.api.internal.plugins.ImperativeOnlyPluginApplicator.applyImperative(ImperativeOnlyPluginApplicator.java:35)
        at org.gradle.api.internal.plugins.RuleBasedPluginApplicator.applyImperative(RuleBasedPluginApplicator.java:43)
        at org.gradle.api.internal.plugins.DefaultPluginManager.doApply(DefaultPluginManager.java:139)
        at org.gradle.api.internal.plugins.DefaultPluginManager.apply(DefaultPluginManager.java:116)
        at org.gradle.api.internal.plugins.DefaultObjectConfigurationAction.applyType(DefaultObjectConfigurationAction.java:123)
        at org.gradle.api.internal.plugins.DefaultObjectConfigurationAction.applyPlugin(DefaultObjectConfigurationAction.java:107)
        at org.gradle.api.internal.plugins.DefaultObjectConfigurationAction.access$100(DefaultObjectConfigurationAction.java:36)
        at org.gradle.api.internal.plugins.DefaultObjectConfigurationAction$2.run(DefaultObjectConfigurationAction.java:71)
        at org.gradle.api.internal.plugins.DefaultObjectConfigurationAction.execute(DefaultObjectConfigurationAction.java:136)
        at org.gradle.groovy.scripts.DefaultScript.apply(DefaultScript.java:114)
        at org.gradle.api.Script$apply.callCurrent(Unknown Source)
        at ThingSubprojectPlugin_1lne4l8iehf8hw0uyvhjojfo7.run(/Users/scott/dev/ThingSDK/BuildScripts/ThingSubprojectPlugin.gradle:6)
        at org.gradle.groovy.scripts.internal.DefaultScriptRunnerFactory$ScriptRunnerImpl.run(DefaultScriptRunnerFactory.java:90)
        ... 70 more
Caused by: java.lang.ClassNotFoundException: ThingUtilsPluginConvention$_parseVersion_closure3
        ... 109 more
```

### Ideas

#### Definitions

- binary plugin: plugin loaded from jar file
- script plugin: a script that is applied with "apply from:'some_file.gradle'"

#### How can binary plugins be loaded

- applied in build script
- applied in script plugin that gets applied
- applied in a binary plugin
- a binary plugin loaded from sub project child classloader loads a binary plugin that is also defined in the parent classloader
- multiple levels of script plugins
- a binary plugin that applies a script plugin

#### Questions

- is there a difference in classloading between `buildSrc` plugins and other binary plugins?
- should a plugin class be the same if the root project loads the same plugin class as the sub project?
  - is the sub project's plugin classloader parent-first?
- does [using "to:" in the apply method](https://github.com/gradle/gradle/blob/94b9299/subprojects/plugin-use/src/integTest/groovy/org/gradle/plugin/use/PluginUseDslIntegrationSpec.groovy#L159) change the classloading behaviour of a script plugin?


#### Some things to test

#### Try to get classes / cached classloaders garbage collected

- use `-XX:SoftRefLRUPolicyMSPerMB=0` and several `System.gc()` calls to enforce GC of weak/soft references
- try if `-XX:+UseConcMarkSweepGC`, `-XX:+UseParNewGC` and `-XX:+CMSClassUnloadingEnabled` make a difference
- try `-XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses` ([blog post](https://blog.codecentric.de/en/2013/10/useful-jvm-flags-part-7-cms-collector/))

#### Try with configure-on-demand and/or parallel enabled

```
org.gradle.configureondemand=true
org.gradle.parallel=true
```



## Related topics

### URLClassLoader and default URL caching in the JVM

#### default URL caching

It's possible to disable default URL caching in the JVM. This is the approach that Tomcat, Jetty and many appservers use. This is also used in the [Gradle Play support](https://github.com/gradle/gradle/blob/c532913/subprojects/platform-play/src/main/java/org/gradle/play/internal/run/PlayWorkerServer.java#L97). 
I found a comment about this [in the Gradle forums](https://discuss.gradle.org/t/getresourceasstream-returns-null-in-plugin-in-daemon-mode/2385/6):
>I'm going to propose setting this globally in Gradle, but if this is indeed the issue that code should unblock you.

Disabling URL caching does affect the whole JVM, but it's not a big deal since for example [Tomcat already does this by default](https://github.com/apache/tomcat/blob/b9fdc88/java/org/apache/catalina/core/JreMemoryLeakPreventionListener.java#L272). 

quote from Tomcat [javadocs in JreMemoryLeakPreventionListener](https://github.com/apache/tomcat/blob/b9fdc88/java/org/apache/catalina/core/JreMemoryLeakPreventionListener.java#L54-L56)
> Locked files usually occur when a resource inside a JAR is accessed without first disabling Jar URL connection caching. The workaround is to disable this caching by default.

Disabling URL caching alone isn't sufficient. File handles will be kept open if there are leaks in resource handling where an `InputStream` originating from a classloader isn't closed properly. Btrace probes or other types of byte code instrumentation can be used to track down leaks in resource handling.

Interesting detail: Changing jar files what have open file handles causes the the JVM to crash because the open zip files are mmapped and changing a mmapped file from elsewhere can cause a crash, more details in [JDK-4425695](https://bugs.openjdk.java.net/browse/JDK-4425695).

#### How does default URL caching affect classloading

There is a JDK bug [JDK-8155607: Closing an URLClassLoader instance affect another URLClassLoader instance when getResourceAsStream() is being used](https://bugs.openjdk.java.net/browse/JDK-8155607). It's marked as a duplicate of an old bug [JDK-6947916: JarURLConnection does not handle useCaches correctly](https://bugs.openjdk.java.net/browse/JDK-6947916). 

It can be seen in the [source code of getResourceAsStream](https://github.com/dmlloyd/openjdk/blob/jdk7u/jdk7u/jdk/src/share/classes/java/net/URLClassLoader.java#L227-L250) that the use of `getResourceAsStream` might affect the behaviour of parent classloaders when the child classloader gets closed.

The connection between JDK-6947916 and JDK-8155607 is the URL cache usage. The use of `getResourceAsStream` would only affect the parent classloaders if URL caching is enabled. The jar file instances are kept open and shared only when URL caching is enabled. That's how race conditions can occur when a child classloader gets closes since it will also close the jar file instances that are in the parent classloader.

[`sun.net.www.protocol.jar.JarURLConnection.connect()`](https://github.com/dmlloyd/openjdk/blob/b003713e/jdk/src/share/classes/sun/net/www/protocol/jar/JarURLConnection.java#L122) gets the `JarFile` from [`sun.net.www.protocol.jar.JarFileFactory`](https://github.com/dmlloyd/openjdk/blob/b003713e449e8651fb605a15ecccc40b0dfb7f54/jdk/src/solaris/classes/sun/net/www/protocol/jar/JarFileFactory.java#L81) and the `JarFile` is cached and shared if URL caching is enabled. There is a possibility for a race condition. 













