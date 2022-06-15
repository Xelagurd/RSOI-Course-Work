package com.example.rsoi_course_work.front.view;

import com.example.rsoi_course_work.front.FrontService;
import com.example.rsoi_course_work.front.model.user.security.RegistrationRequest;
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

@Route(value = "register")
@PageTitle("Регистрация")
public class RegistrationView extends VerticalLayout {
    private FrontService frontService;

    public RegistrationView(FrontService frontService) {
        try {
            this.frontService = frontService;
            setSizeFull();
            setAlignItems(Alignment.CENTER);
            TextField name = new TextField("Имя");
            name.setMaxLength(100);
            TextField surname = new TextField("Фамилия");
            surname.setMaxLength(100);
            TextField username = new TextField("Логин");
            username.setMaxLength(100);
            PasswordField password = new PasswordField("Пароль");
            password.setMaxLength(100);

            Button registrationButton = new Button("Зарегистрироваться", event -> register(
                    name.getValue(),
                    surname.getValue(),
                    username.getValue(),
                    password.getValue()
            ));
            registrationButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

            Button loginButton = new Button("Вход", event -> {
                UI.getCurrent().navigate(LoginView.class);
            });
            loginButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

            add(
                    new H2("Регистрация"),
                    name,
                    surname,
                    username,
                    password,
                    registrationButton,
                    loginButton
            );
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }

    private void register(String name, String surname, String username, String password) {
        if (name.trim().isEmpty()) {
            Notification.show("Введите имя");
        } else if (surname.trim().isEmpty()) {
            Notification.show("Введите фамилию");
        } else if (username.trim().isEmpty()) {
            Notification.show("Введите логин");
        } else if (password.trim().isEmpty()) {
            Notification.show("Введите пароль");
        } else if (password.length() < 6) {
            Notification.show("Пароль должен быть >5 символов");
        } else {
            ResponseEntity<SessionJwtResponse> response = frontService.userRegistration(new RegistrationRequest
                    (name, surname, username, password));
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                UI.getCurrent().getPage().reload();
                UI.getCurrent().navigate(ProfileView.class);
            } else if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                Notification.show("Сервис недоступен");
            }
        }
    }
}
