package com.healthcare.jacoco.badge;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.util.HashMap;

public class JacocoBadgeGenerate extends DefaultTask {

    static final String TASK_NAME = "generateJacocoBadge";
    static final String DESCRIPTION = "generate code coverage badge from Jacoco report";

    static void define(Project project) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", JacocoBadgeGenerate.class);
        map.put("description", DESCRIPTION);
        map.put("group", "jacoco-badge");
        project.task(map, JacocoBadgeGenerate.TASK_NAME);
    }

    @TaskAction
    public void generate() throws Exception {
        new ReadMeUpdater(getProject().getExtensions()
                .getByType(JacocoBadgeGenerateSetting.class)
                .setDefault(getProject()))
                .updateReadme();
    }
}
