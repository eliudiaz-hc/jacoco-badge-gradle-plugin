package com.healthcare.jacoco.badge.report;

public class JsonReportEntry {
    private int total;
    private int covered;
    private int missed;
    private double pct;


    public JsonReportEntry(int total, int covered, int missed, double pct) {
        this.total = total;
        this.covered = covered;
        this.missed = missed;
        this.pct = pct;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCovered() {
        return covered;
    }

    public void setCovered(int covered) {
        this.covered = covered;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public double getPct() {
        return pct;
    }

    public void setPct(double pct) {
        this.pct = pct;
    }
}
