package com.example.rsoi_course_work.front.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about-us", layout = HeaderLayout.class)
@PageTitle("О нас")
public class AboutUsView extends VerticalLayout {

    public AboutUsView() {
        try {
            setSizeFull();
            setAlignItems(Alignment.CENTER);

            add(new H2("Аренда самокатов"));
            add(new H3("Наши контакты"));
            add(new H4("VK: https://vk.com/id160539460"));
            add(new H4("Телефон: +7(985)119-44-64"));

/*        Image vk = new Image("https://cdn-icons.flaticon.com/png/512/3670/premium/3670055.png?token=exp=1654635175~hmac=0b243f4a100af517a79e31fe05ce9baa", "vk");
        Image phone = new Image("https://cdn-icons-png.flaticon.com/512/724/724664.png", "phone");
        vk.setHeight("20px");
        vk.setHeight("20px");
        phone.setHeight("20px");
        phone.setWidth("20px");

        HorizontalLayout horizontal   Layout = new HorizontalLayout();
        H4 hvk = new H4("https://vk.com/id160539460");
        horizontalLayout.add(vk, hvk);
        H4 hphone = new H4("+7(985)119-44-64");
        horizontalLayout.add(phone, hphone);
        add(horizontalLayout);*/
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }
}
