package com.slis.service;

public class LogCode {
    private final String code;

    public LogCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogCode logCode = (LogCode) o;

        return !(code != null ? !code.equals(logCode.code) : logCode.code != null);

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
