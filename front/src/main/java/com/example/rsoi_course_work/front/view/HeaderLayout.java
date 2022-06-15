package com.example.rsoi_course_work.front.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.RouterLink;

public class HeaderLayout extends AppLayout {
    public HeaderLayout() {
        createHeader();
    }

    private void createHeader() {
        try {
            Icon userIcon = new Icon(VaadinIcon.USER);
            Button userButton = new Button("Мой профиль", userIcon);
            userButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            userButton.getStyle().set("margin-left", "10px").set("margin-right", "10px");

            Button scootersButton = new Button("Арендовать самокат");
            scootersButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            scootersButton.getStyle().set("margin-left", "10px").set("margin-right", "10px");

            Button contactButton = new Button("О нас");
            contactButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            contactButton.getStyle().set("margin-left", "10px").set("margin-right", "10px");

            Button signOutButton = new Button("Выйти", e -> {
                UI.getCurrent().getPage().executeJs("document.cookie = 'Authorization=;expires=Thu, 01 Jan 1984 00:00:00 GMT'");
                UI.getCurrent().getPage().reload();
            });
            signOutButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
            signOutButton.getStyle().set("margin-left", "10px").set("margin-right", "10px");

            RouterLink contactRouterLink = new RouterLink("", AboutUsView.class);
            RouterLink scootersRouterLink = new RouterLink("", SearchScootersView.class);
            RouterLink userRouterLink = new RouterLink("", ProfileView.class);

            contactRouterLink.getElement().appendChild(contactButton.getElement());
            scootersRouterLink.getElement().appendChild(scootersButton.getElement());
            userRouterLink.getElement().appendChild(userButton.getElement());

            addToNavbar(userRouterLink);
            addToNavbar(scootersRouterLink);
            addToNavbar(contactRouterLink);
            addToNavbar(signOutButton);
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }
}