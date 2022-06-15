package com.example.rsoi_course_work.front.view;

import com.example.rsoi_course_work.front.FrontService;
import com.example.rsoi_course_work.front.model.user.security.SessionJwtResponse;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Route(value = "login")
@PageTitle("Вход")
public class LoginView extends VerticalLayout {
    private FrontService frontService;

    public LoginView(FrontService frontService) {
        try {
            this.frontService = frontService;
            setId("login-view");
            setSizeFull();
            setAlignItems(Alignment.CENTER);
            var username = new TextField("Логин");
            username.setMaxLength(100);
            var password = new PasswordField("Пароль");
            password.setMaxLength(100);

            Button loginButton = new Button("Войти", event -> {
                if (username.getValue().isEmpty()) {
                    Notification.show("Введите логин");
                } else if (password.getValue().isEmpty()) {
                    Notification.show("Введите пароль");
                } else {
                    ResponseEntity<SessionJwtResponse> response = frontService.userAuthorization
                            (username.getValue(), password.getValue());

                    if (response.getStatusCode().equals(HttpStatus.OK)) {
                        UI.getCurrent().getPage().reload();
                        UI.getCurrent().navigate(ProfileView.class);
                    } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                        Notification.show("Неверный логин");
                    } else if (response.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)) {
                        Notification.show("Неверный пароль");
                    } else if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                        Notification.show("Сервис недоступен");
                    }
                }
            });
            loginButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

            Button registrationButton = new Button("Регистрация", event -> {
                UI.getCurrent().navigate(RegistrationView.class);
            });
            registrationButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

            add(
                    new H2("Вход"),
                    username,
                    password,
                    loginButton,
                    registrationButton
            );
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }
}
