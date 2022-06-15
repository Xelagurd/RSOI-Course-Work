package com.example.rsoi_course_work.front.view;

import com.example.rsoi_course_work.front.FrontService;
import com.example.rsoi_course_work.front.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.front.model.rental.CreateRentalRequest;
import com.example.rsoi_course_work.front.model.rental.RentalInfo;
import com.example.rsoi_course_work.front.model.rental_station.RentalStationInfo;
import com.example.rsoi_course_work.front.model.scooter.ScooterInfo;
import com.example.rsoi_course_work.front.model.user.UserInfo;
import com.example.rsoi_course_work.front.model.user.UserRole;
import com.flowingcode.vaadin.addons.simpletimer.SimpleTimer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Route(value = "scooter", layout = HeaderLayout.class)
@PageTitle("Самокат")
public class ScooterInfoView extends VerticalLayout implements HasUrlParameter<String> {
    private FrontService frontService;
    private LocatedScooterInfo locatedScooter;
    private ScooterInfo scooter;
    private RentalInfo rental;
    private int page = 1;
    private int size = 12;
    private Boolean showAll = false;

    public ScooterInfoView(FrontService frontService) {
        this.frontService = frontService;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        try {
            ResponseEntity<LocatedScooterInfo> response = frontService.getLocatedScooter(UUID.fromString(parameter));

            if (response.getStatusCode().equals(HttpStatus.OK)) {

                locatedScooter = response.getBody();
                scooter = locatedScooter.getScooter();

                Paragraph location = new Paragraph("Местоположение: " + locatedScooter.getRental_station().getLocation());
                location.getStyle().set("padding-left", "32px").set("margin-top", "-10px");

                /*Paragraph registration_number = new Paragraph("Регистр. номер: " + locatedScooter.getRegistration_number());
                registration_number.getStyle().set("padding-left", "32px").set("margin-top", "-10px");*/

                Span current_charge = new Span("Текущий заряд (%): " + locatedScooter.getCurrent_charge() + "%");
                current_charge.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                Span availability = new Span("Доступен: " + (locatedScooter.getAvailability() ? "Да" : "Нет"));
                availability.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                H3 provider;
                Span price;
                Span max_speed;
                Span charge_consumption;
                Span charge_recovery;
                if (scooter != null) {
                    provider = new H3(scooter.getProvider() + ", регистр. номер: " + locatedScooter.getRegistration_number());
                    provider.getStyle().set("padding-left", "32px").set("margin-top", "10px");

                    price = new Span("Стоимость (₽ в мин): " + scooter.getPrice() + "₽");
                    price.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    max_speed = new Span("Макс. скорость (км/ч): " + scooter.getMax_speed());
                    max_speed.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    charge_consumption = new Span("Потребление энергии (% в 10 мин): " + scooter.getCharge_consumption());
                    charge_consumption.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    charge_recovery = new Span("Скорость зарядки (% в 10 мин): " + scooter.getCharge_recovery());
                    charge_recovery.getStyle().set("padding-left", "32px").set("margin-top", "-16px");
                } else {
                    provider = new H3("Поставщик: null, регистр. номер:" + locatedScooter.getRegistration_number());
                    provider.getStyle().set("padding-left", "32px").set("margin-top", "10px");

                    price = new Span("Стоимость (₽ в мин): null");
                    price.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    max_speed = new Span("Макс. скорость (км/ч): null");
                    max_speed.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    charge_consumption = new Span("Потребление энергии (% в 10 мин): null");
                    charge_consumption.getStyle().set("padding-left", "32px").set("margin-top", "-16px");

                    charge_recovery = new Span("Скорость зарядки (% в 10 мин): null");
                    charge_recovery.getStyle().set("padding-left", "32px").set("margin-top", "-16px");
                }

                add(provider, location, price, max_speed, charge_consumption, charge_recovery,
                        current_charge, availability);

                ResponseEntity<UserInfo> userResponse = frontService.getCurrentUser();
                UserInfo user = userResponse.getBody();
                if (user != null) {
                    Button cancelButton = new Button("Назад", e -> UI.getCurrent().navigate(SearchScootersView.class));
                    cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                    Button deleteScooterButton = new Button("Удалить", buttonClickEvent -> {
                        ResponseEntity<HttpStatus> responseEntity = frontService.removeLocatedScooter(locatedScooter.getLocated_scooter_uid());
                        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                            Notification.show("Самокат на станции удален");
                            UI.getCurrent().getPage().reload();
                            UI.getCurrent().navigate(SearchScootersView.class);
                        } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                            Notification.show("Сервис недоступен");
                        }

                    });
                    deleteScooterButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                    Dialog createPaymentDialog = new Dialog();
                    VerticalLayout createPaymentDialogLayout = createPaymentDialogLayout(createPaymentDialog);
                    createPaymentDialog.add(createPaymentDialogLayout);

                    Dialog createRentalDialog = new Dialog();
                    VerticalLayout createRentalDialogLayout = createRentalDialogLayout(createRentalDialog, createPaymentDialog);
                    createRentalDialog.add(createRentalDialogLayout);

                    Button rentButton = new Button("Арендовать", e -> createRentalDialog.open());
                    rentButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                    rentButton.setEnabled(locatedScooter.getAvailability());
                    //rentButton.setHeight("32px");
                    //rentButton.setWidth("120px");

                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    horizontalLayout.getStyle().set("padding-left", "32px");

                    if (userResponse.getBody().getRole().equals(UserRole.USER)) {
                        horizontalLayout.add(cancelButton, rentButton);
                        add(horizontalLayout);
                    } else if (userResponse.getBody().getRole().equals(UserRole.ADMIN)) {
                        horizontalLayout.add(cancelButton, rentButton, deleteScooterButton);
                        add(horizontalLayout);
                    }
                }
            } else if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                Notification.show("Сервис недоступен");
            }
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }

    public VerticalLayout createPaymentDialogLayout(Dialog createPaymentDialog) {
        H3 headline = new H3("Оплата аренды самоката");
        //headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight", "bold");

        SimpleTimer timer = new SimpleTimer(new BigDecimal("180"));
        timer.start();
        timer.addTimerEndEvent(ev -> {
            ResponseEntity<HttpStatus> responseEntity = frontService.cancelUserRentalAndPaymentAndUnReserveScooter(
                    rental.getRental_uid());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                Notification.show("Аренда самоката отменена, так как прошло больше 3 минут");
            } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                Notification.show("Сервис недоступен");
            }
            createPaymentDialog.close();
        });

        Button cancelButton = new Button("Отменить оплату", e -> {
            ResponseEntity<HttpStatus> responseEntity = frontService.cancelUserRentalAndPaymentAndUnReserveScooter(
                    rental.getRental_uid());
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                Notification.show("Аренда самоката отменена");
            } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                Notification.show("Сервис недоступен");
            }
            createPaymentDialog.close();
        });
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button rentButton = new Button("Оплатить", buttonClickEvent -> {
            Notification.show("Оплата прошла успешно");
            createPaymentDialog.close();
            //UI.getCurrent().getPage().reload();
            UI.getCurrent().navigate(SearchScootersView.class);
        });
        rentButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, rentButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, buttonLayout);
        //dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "400px");
        return dialogLayout;
    }

    public VerticalLayout createRentalDialogLayout(Dialog createRentalDialog, Dialog createPaymentDialog) {
        H3 headline = new H3("Аренда самоката");
        /*headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        ComboBox<RentalStationInfo> rentalStation = new ComboBox<>();
        rentalStation.setLabel("Вернете на станцию");
        ResponseEntity<List<RentalStationInfo>> rentalStations = frontService.getRentalStations();
        if (rentalStations.getStatusCode().equals(HttpStatus.OK)) {

            rentalStation.setItems(rentalStations.getBody());
            rentalStation.setValue(rentalStations.getBody().get(0));
        } else {
            Notification.show("Станции проката недоступны");
        }

        DateTimePicker date_from = new DateTimePicker("Выберите время начала");
        DateTimePicker date_to = new DateTimePicker("Выберите время окончания");

        date_from.addValueChangeListener(e -> {
            date_to.setMin(e.getValue());
        });
        date_to.addValueChangeListener(e -> {
            date_from.setMax(e.getValue());
        });

        VerticalLayout fieldLayout = new VerticalLayout(rentalStation, date_from, date_to);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> createRentalDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        Button addButton = new Button("Оплатить", buttonClickEvent -> {
            LocalDateTime localDate = date_from.getValue();
            Date date_from_value = null;
            if (date_from.getValue() != null) {
                date_from_value = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
            }

            LocalDateTime localDate2 = date_to.getValue();
            Date date_to_value = null;
            if (date_to.getValue() != null) {
                date_to_value = Date.from(localDate2.atZone(ZoneId.systemDefault()).toInstant());
            }

            if (rentalStation.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите станцию проката");
            } else if (date_from.getValue() == null || date_to.getValue() == null ||
                    (date_from_value != null && date_to_value != null && date_to_value.before(date_from_value))) {
                Notification.show("Укажите корректный временной промежуток");
            } else {
                ResponseEntity<RentalInfo> responseEntity = frontService.reserveUserScooterAndCreateRentalAndPayment(
                        new CreateRentalRequest(locatedScooter.getLocated_scooter_uid(),
                                rentalStation.getValue().getRental_station_uid(), date_from_value, date_to_value));
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Самокат арендован");
                    rental = responseEntity.getBody();
                    createRentalDialog.close();
                    //UI.getCurrent().getPage().reload();
                    //UI.getCurrent().navigate(SearchScootersView.class);
                    //UI.getCurrent().navigate("scooter/" + locatedScooter.getLocated_scooter_uid());
                    createPaymentDialog.open();
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    createRentalDialog.close();
                    Notification.show("Сервис недоступен");
                }
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //dialogLayout.setSpacing(false);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "400px");

        return dialogLayout;
    }
}
