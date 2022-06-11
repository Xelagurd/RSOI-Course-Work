package com.example.rsoi_course_work.gateway_service.security;

public class SessionRequest {
    private String username;
    private String password;
    private Options options;

    public SessionRequest() {
    }

    public SessionRequest(String username, String password, Options options) {
        this.username = username;
        this.password = password;
        this.options = options;
    }

    public static class Options {
        private boolean multiOptionalFactorEnroll;
        private boolean warnBeforePasswordExpired;

        public Options() {
        }

        public Options(boolean multiOptionalFactorEnroll, boolean warnBeforePasswordExpired) {
            this.multiOptionalFactorEnroll = multiOptionalFactorEnroll;
            this.warnBeforePasswordExpired = warnBeforePasswordExpired;
        }

        public boolean isMultiOptionalFactorEnroll() {
            return multiOptionalFactorEnroll;
        }

        public boolean isWarnBeforePasswordExpired() {
            return warnBeforePasswordExpired;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Options getOptions() {
        return options;
    }
}
