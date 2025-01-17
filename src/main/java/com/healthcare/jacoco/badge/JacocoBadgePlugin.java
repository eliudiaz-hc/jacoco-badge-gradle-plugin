package com.healthcare.jacoco.badge;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

@SuppressWarnings("unused")
public class JacocoBadgePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        JacocoBadgeGenerateSetting.create(project);
        JacocoBadgeGenerate.define(project);
    }
}
