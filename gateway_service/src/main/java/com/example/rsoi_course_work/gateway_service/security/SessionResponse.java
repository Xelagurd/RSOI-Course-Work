package com.example.rsoi_course_work.gateway_service.security;

import java.util.ArrayList;
import java.util.Date;

public class SessionResponse {
    private Date expiresAt;
    private String status;
    private String sessionToken;
    private Embedded _embedded;
    private Links _links;

    public SessionResponse() {
    }

    public SessionResponse(Date expiresAt, String status, String sessionToken, Embedded _embedded, Links _links) {
        this.expiresAt = expiresAt;
        this.status = status;
        this.sessionToken = sessionToken;
        this._embedded = _embedded;
        this._links = _links;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public Links get_links() {
        return _links;
    }

    public class Links {
        private Cancel cancel;

        public Links() {
        }

        public Links(Cancel cancel) {
            this.cancel = cancel;
        }

        public Cancel getCancel() {
            return cancel;
        }

        public class Cancel {
            private String href;
            private Hints hints;

            public Cancel() {

            }

            public Cancel(String href, Hints hints) {
                this.href = href;
                this.hints = hints;
            }

            public String getHref() {
                return href;
            }

            public Hints getHints() {
                return hints;
            }

            public class Hints {
                private ArrayList<String> allow;

                public Hints() {
                }

                public Hints(ArrayList<String> allow) {
                    this.allow = allow;
                }

                public ArrayList<String> getAllow() {
                    return allow;
                }
            }
        }

    }

    public class Embedded {
        private User user;

        public Embedded() {
        }

        public Embedded(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public class User {
            private String id;
            private Date passwordChanged;
            private Profile profile;

            public User() {
            }

            public User(String id, Date passwordChanged, Profile profile) {
                this.id = id;
                this.passwordChanged = passwordChanged;
                this.profile = profile;
            }

            public String getId() {
                return id;
            }

            public Date getPasswordChanged() {
                return passwordChanged;
            }

            public Profile getProfile() {
                return profile;
            }

            public class Profile {
                private String login;
                private String firstName;
                private String lastName;
                private String locale;
                private String timeZone;

                public Profile() {
                }

                public Profile(String login, String firstName, String lastName, String locale, String timeZone) {
                    this.login = login;
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.locale = locale;
                    this.timeZone = timeZone;
                }

                public String getLogin() {
                    return login;
                }

                public String getFirstName() {
                    return firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public String getLocale() {
                    return locale;
                }

                public String getTimeZone() {
                    return timeZone;
                }
            }
        }
    }
}
