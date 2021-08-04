package com.healthcare.jacoco.badge;

import com.healthcare.jacoco.badge.report.JsonReportEntry;
import groovy.json.JsonOutput;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class ReadMeUpdater {

    private static final Pattern BADGE_PATTERN = Pattern.compile("^!\\[([^]]+)]\\(([^)]+)\\)$");
    private final JacocoBadgeGenerateSetting setting;
    private final JacocoResultParser parser;

    ReadMeUpdater(JacocoBadgeGenerateSetting setting) {
        this(setting, new JacocoResultParser(setting));
    }

    ReadMeUpdater(JacocoBadgeGenerateSetting setting, JacocoResultParser parser) {
        this.setting = setting;
        this.parser = parser;
    }

    void updateReadme() throws Exception {
        final Map<String, JacocoBadgePercentageResult> results = parser.getJacocoResults();

        results.values().forEach(JacocoBadgePercentageResult::verifyLimit);

        final Path readmePath = Paths.get(setting.getReadmePath());
        Files.write(readmePath, Files.readAllLines(readmePath).stream()
                .map(l -> {
                    Matcher matcher = BADGE_PATTERN.matcher(l);
                    if (matcher.find()) {
                        String type = matcher.group(1);
                        JacocoBadgePercentageResult result = results.get(type);
                        if (result != null) {
                            return matcher.replaceFirst(String.format("![$1](%s)", result.badgeUrl()));
                        }
                    }
                    return l;
                })
                .collect(Collectors.toList()));
        System.out.println("Generating JSON report!");
        // json report
        final var jsonReportDir = setting.getReadmePath().indexOf("/") >= 0 ?
                this.setting.getReadmePath().substring(0, this.setting.getReadmePath().lastIndexOf("/")) : "";
        final var jsonReportPath = Paths.get(jsonReportDir.concat("/metrics.json"));
        System.out.println("Report path: " + jsonReportPath.toString());
        final var totals = new LinkedHashMap<String, JsonReportEntry>();
        results.forEach((type, item) -> {
            var total = item.missed() + item.covered();
            var pct = total > 0 ? (double) item.covered() / total : 0;
            totals.put(type, new JsonReportEntry(total,
                    item.covered(), item.missed(), pct));
        });

        var report = new LinkedHashMap<String, LinkedHashMap<String, JsonReportEntry>>();
        report.put("total", totals);
        var json = JsonOutput.toJson(report);
        Files.write(jsonReportPath, json.getBytes(StandardCharsets.UTF_8));
    }

}
