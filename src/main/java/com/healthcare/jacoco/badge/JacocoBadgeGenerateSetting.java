package com.healthcare.jacoco.badge;

import org.gradle.api.GradleException;
import org.gradle.api.Project;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class JacocoBadgeGenerateSetting {

    private static final String SETTINGS = "jacocoBadgeGenSetting";

    private String jacocoReportPath;
    private String readmePath;
    private Map<String, Number> limit;

    private static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    static void create(Project project) {
        project.getExtensions().create(SETTINGS, JacocoBadgeGenerateSetting.class);
    }

    public String getJacocoReportPath() {
        return jacocoReportPath;
    }

    public JacocoBadgeGenerateSetting setJacocoReportPath(String jacocoReportPath) {
        this.jacocoReportPath = jacocoReportPath;
        return this;
    }

    public String getReadmePath() {
        return readmePath;
    }

    public JacocoBadgeGenerateSetting setReadmePath(String readmePath) {
        this.readmePath = readmePath;
        return this;
    }

    public Map<String, Number> getLimit() {
        return limit;
    }

    public JacocoBadgeGenerateSetting setLimit(Map<String, Number> limit) {
        this.limit = limit;
        return this;
    }

    JacocoBadgeGenerateSetting setDefault(Project project) {
        if (isEmpty(jacocoReportPath)) {
            Path defaultJacocoReportPath = project.getBuildDir().toPath()
                    .resolve("reports/jacoco/test/jacocoTestReport.xml");
            if (Files.exists(defaultJacocoReportPath)) {
                setJacocoReportPath(defaultJacocoReportPath.toString());
            } else {
                throw new GradleException(
                        "no default jacocoTestReport found, please specified it in " + SETTINGS);
            }
        } else {
            System.out.printf("report path: ", jacocoReportPath);
        }
        if (isEmpty(readmePath)) {
            Path defaultReadmePath = project.getProjectDir().toPath().resolve("README.md");
            if (Files.exists(defaultReadmePath)) {
                setReadmePath(defaultReadmePath.toString());
            } else {
                throw new GradleException(
                        "no default readmePath found, please specified it in " + SETTINGS);
            }
        }
        return this;
    }
}
