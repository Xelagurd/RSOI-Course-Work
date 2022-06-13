package com.example.rsoi_course_work.front;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class SecurityService implements VaadinServiceInitListener {
    private final FrontService frontService;

    public SecurityService(FrontService frontService) {
        this.frontService = frontService;
    }

    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        initEvent.getSource().addUIInitListener(uiInitEvent -> {
            uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
                if (cookies != null) {
                    String token = frontService.getJWT();
                    if (frontService.verifyJwtToken(token).getBody() == null ||
                            frontService.verifyJwtToken(token).getBody().equals(Boolean.FALSE)) {
                        if (enterEvent.getNavigationTarget().equals(RegistrationView.class)) {
                            enterEvent.forwardTo(RegistrationView.class);
                        } else {
                            enterEvent.forwardTo(LoginView.class);
                        }
                    } else if (frontService.verifyJwtToken(token).getBody() != null &&
                            frontService.verifyJwtToken(token).getBody().equals(Boolean.TRUE)) {
                        if (enterEvent.getNavigationTarget().equals(RegistrationView.class) ||
                                enterEvent.getNavigationTarget().equals(LoginView.class)) {
                            enterEvent.forwardTo(MainView.class);
                        }
                    }
                }
            });
        });
    }
}