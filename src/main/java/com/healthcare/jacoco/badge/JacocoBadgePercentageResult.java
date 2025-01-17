package com.healthcare.jacoco.badge;

import org.gradle.api.GradleException;
import org.w3c.dom.NamedNodeMap;

import java.util.Collections;
import java.util.Map;

class JacocoBadgePercentageResult {

    private final Type type;
    private final int covered;
    private final int missed;
    private final double limit;

    JacocoBadgePercentageResult(NamedNodeMap attr) {
        this(attr, Collections.emptyMap());
    }

    JacocoBadgePercentageResult(NamedNodeMap attr,
                                Map<String, Number> limit) {
        final String type = attr.getNamedItem("type").getNodeValue();
        this.type = Type.valueOf(type);
        this.covered = Integer.parseInt(attr.getNamedItem("covered").getNodeValue());
        this.missed = Integer.parseInt(attr.getNamedItem("missed").getNodeValue());
        this.limit = limit.getOrDefault(type, 0d).doubleValue();
    }

    JacocoBadgePercentageResult(JacocoBadgePercentageResult that) {
        this.type = that.type();
        this.covered = that.covered();
        this.missed = that.missed();
        this.limit = that.limit();
    }

    String badgeUrl() {
        return String.format("https://img.shields.io/badge/%s-%.2f%%25-%s.svg",
                type.name().toLowerCase() + "--coverage", percent(), color());
    }

    void verifyLimit() {
        final double percent = percent();
        if (percent < limit) {
            throw new GradleException(String.format(
                    "%s coverage limit not satisfied, expect at least %.2f%%, got %.2f%%", type, limit,
                    percent));
        }
    }

    private String color() {
        double percent = percent();
        if (percent >= 80) {
            return "brightgreen";
        } else if (percent > 60) {
            return "yellow";
        } else if (percent > 40) {
            return "orange";
        } else {
            return "red";
        }
    }

    Type type() {
        return type;
    }

    int covered() {
        return covered;
    }

    int missed() {
        return missed;
    }

    public double limit() {
        return limit;
    }

    private double percent() {
        return 100.0 * covered / (covered + missed);
    }

    @Override
    public String toString() {
        return String.format("%s:%.2f%%", type, percent());
    }


    public enum Type {
        INSTRUCTION, BRANCH, LINE, COMPLEXITY, METHOD, CLASS
    }
}
