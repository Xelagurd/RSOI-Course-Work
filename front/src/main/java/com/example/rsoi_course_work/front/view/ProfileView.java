package com.example.rsoi_course_work.front.view;

import com.example.rsoi_course_work.front.FrontService;
import com.example.rsoi_course_work.front.model.located_scooter.CreateLocatedScooterRequest;
import com.example.rsoi_course_work.front.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.front.model.rental.RentalInfo;
import com.example.rsoi_course_work.front.model.rental.RentalStatus;
import com.example.rsoi_course_work.front.model.rental_station.CreateRentalStationRequest;
import com.example.rsoi_course_work.front.model.rental_station.RentalStationInfo;
import com.example.rsoi_course_work.front.model.scooter.CreateScooterRequest;
import com.example.rsoi_course_work.front.model.scooter.ScooterInfo;
import com.example.rsoi_course_work.front.model.statistic_operation.StatisticOperationInfo;
import com.example.rsoi_course_work.front.model.user.UserInfo;
import com.example.rsoi_course_work.front.model.user.UserRole;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Route(value = "user", layout = HeaderLayout.class)
@PageTitle("Мой профиль")
public class ProfileView extends VerticalLayout {
    private FrontService frontService;

    public ProfileView(FrontService frontService) {
        this.frontService = frontService;
    }

    @PostConstruct
    public void init() {
        try {
            setSizeFull();
            ResponseEntity<UserInfo> response = frontService.getCurrentUser();
            UserInfo user = response.getBody();

            if (user != null) {
                HorizontalLayout horizontalLayout = new HorizontalLayout();
                VerticalLayout profile = new VerticalLayout();
                VerticalLayout userHistory = new VerticalLayout();
                profile.setAlignItems(Alignment.CENTER);
                //profile.getStyle().set("margin-left", "50px");
                //profile.getStyle().set("margin-right", "-50%");
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    H2 title = new H2("Мой профиль");

                    Image userImage = new Image("https://cdn-icons-png.flaticon.com/512/3022/3022316.png", "user");
                    userImage.setHeight("125px");
                    userImage.setHeight("125px");

                    H4 log = new H4("Логин: " + user.getLogin());
                    log.getStyle().set("margin-top", "0px");

                    H4 name = new H4("Имя: " + user.getName());
                    name.getStyle().set("margin-top", "0px");

                    H4 surname = new H4("Фамилия: " + user.getSurname());
                    surname.getStyle().set("margin-top", "0px");

                    H4 role = new H4("Роль:" + " " + user.getRole().toString());
                    role.getStyle().set("margin-top", "0px");

                    profile.add(title, userImage, log, name, surname, role);

                    if (user.getRole().equals(UserRole.USER)) {

                        ResponseEntity<List<RentalInfo>> rentals = frontService.getUserRentals();
                        if (rentals.getStatusCode().equals(HttpStatus.OK)) {
                            H3 userStatTitle = new H3("Ваши последние аренды на портале");
                            userStatTitle.getStyle().set("margin-bottom", "-8px").set("margin-top", "5px");
                            userHistory.getStyle().set("margin-top", "60px");
                            //userHistory.getStyle().set("margin-left", "500px");
                            userHistory.add(userStatTitle);

                            Grid<RentalInfo> gridUser = new Grid<>(RentalInfo.class, false);

                            gridUser.addColumn(
                                    e -> {
                                        if (e.getLocated_scooter() != null) {
                                            if (e.getLocated_scooter().getScooter() != null) {
                                                return e.getLocated_scooter().getScooter().getProvider();
                                            } else {
                                                return "null";
                                            }
                                        } else {
                                            return "null";
                                        }
                                    }
                            ).setHeader("Самокат").setAutoWidth(true);
                            gridUser.addColumn(
                                    e -> {
                                        if (e.getTakenFromRentalStation() != null) {
                                            return e.getTakenFromRentalStation().getLocation();
                                        } else {
                                            return "null";
                                        }
                                    }
                            ).setHeader("Местоположение").setAutoWidth(true);
                            gridUser.addColumn(e -> getDateString(e.getDate_from())).setHeader("Начало").setAutoWidth(true);
                            gridUser.addColumn(e -> getDateString(e.getDate_to())).setHeader("Конец").setAutoWidth(true);
                            gridUser.addColumn(
                                    e -> {
                                        if (e.getPayment() != null) {
                                            return e.getPayment().getPrice();
                                        } else {
                                            return "null";
                                        }
                                    }
                            ).setHeader("Общая цена").setAutoWidth(true);
                            gridUser.addColumn(e -> e.getStatus().toString()).setHeader("Статус").setAutoWidth(true);

                            List<RentalInfo> rentalsData = rentals.getBody();
                            Collections.reverse(rentalsData);
                            List<RentalInfo> data = new ArrayList<>();
                            for (int i = 0, j = 0; j < 12 && i < rentalsData.size(); i++) {
                        /*if (rentalsData.get(i).getLocated_scooter() != null &&
                                rentalsData.get(i).getTakenFromRentalStation() != null &&
                                rentalsData.get(i).getPayment() != null) {*/
                                data.add(rentalsData.get(i));
                                j++;
                                //}
                            }
                            gridUser.setItems(data);

                            gridUser.setMinWidth("1000px");
                            //gridUser.setMaxHeight("240px");

                            //gridUser.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                            //gridUser.getStyle().set("margin-left", "500px");
                            //gridUser.getStyle().set("margin-top", "0px");

                            gridUser.addItemClickListener(item -> {
                                if (item.getItem().getStatus().equals(RentalStatus.IN_PROGRESS)) {
                                    Dialog finishRentalDialog = new Dialog();
                                    VerticalLayout finishRentalLayout = makeFinishRentalLayout(finishRentalDialog, item.getItem());
                                    finishRentalDialog.add(finishRentalLayout);
                                    finishRentalDialog.open();
                                }
                            });

                            userHistory.add(gridUser);
                        } else {
                            Span err = new Span("Аренды недоступны");
                            err.getStyle().set("width", "400px");
                            userHistory.add(err);
                        }
                    } else if (user.getRole().equals(UserRole.ADMIN)) {
                        ResponseEntity<List<ScooterInfo>> scooters = frontService.getScooters();
                        ResponseEntity<List<RentalStationInfo>> rentalStations = frontService.getRentalStations();

                        HorizontalLayout scooterLayout = new HorizontalLayout();

                        Dialog addScooterDialog = new Dialog();
                        VerticalLayout addScooterLayout = makeAddScooterLayout(addScooterDialog);
                        addScooterDialog.add(addScooterLayout);

                        Button addScooterButton = new Button("Добавить типовой самокат", e -> addScooterDialog.open());
                        addScooterButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        Dialog removeScooterDialog = new Dialog();
                        VerticalLayout removeScooterLayout = makeRemoveScooterLayout(removeScooterDialog, scooters);
                        removeScooterDialog.add(removeScooterLayout);

                        Button removeScooterButton = new Button("Удалить типовой самокат", e -> removeScooterDialog.open());
                        removeScooterButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        scooterLayout.add(addScooterButton, removeScooterButton);
                        profile.add(scooterLayout);

                        HorizontalLayout rentalStationLayout = new HorizontalLayout();

                        Dialog addRentalStationDialog = new Dialog();
                        VerticalLayout addRentalStationLayout = makeAddRentalStationLayout(addRentalStationDialog);
                        addRentalStationDialog.add(addRentalStationLayout);

                        Button addRentalStationButton = new Button("Добавить станцию проката", e -> addRentalStationDialog.open());
                        addRentalStationButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        Dialog removeRentalStationDialog = new Dialog();
                        VerticalLayout removeRentalStationLayout = makeRemoveRentalStationLayout(removeRentalStationDialog, rentalStations);
                        removeRentalStationDialog.add(removeRentalStationLayout);

                        Button removeRentalStationButton = new Button("Удалить станцию проката", e -> removeRentalStationDialog.open());
                        removeRentalStationButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        rentalStationLayout.add(addRentalStationButton, removeRentalStationButton);
                        profile.add(rentalStationLayout);

                        HorizontalLayout locatedScooterLayout = new HorizontalLayout();

                        Dialog addLocatedScooterDialog = new Dialog();
                        VerticalLayout addLocatedScooterLayout = makeAddLocatedScooterLayout(addLocatedScooterDialog, scooters, rentalStations);
                        addLocatedScooterDialog.add(addLocatedScooterLayout);

                        Button addLocatedScooterButton = new Button("Добавить самокат на станцию", e -> addLocatedScooterDialog.open());
                        addLocatedScooterButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        Dialog removeLocatedScooterDialog = new Dialog();
                        VerticalLayout removeLocatedScooterLayout = makeRemoveLocatedScooterLayout(removeLocatedScooterDialog);
                        removeLocatedScooterDialog.add(removeLocatedScooterLayout);

                        Button removeLocatedScooterButton = new Button("Удалить самокат со станции", e -> removeLocatedScooterDialog.open());
                        removeLocatedScooterButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                        locatedScooterLayout.add(addLocatedScooterButton, removeLocatedScooterButton);
                        profile.add(locatedScooterLayout);

                        ResponseEntity<List<StatisticOperationInfo>> statisticOperations = frontService.getStatisticOperations();
                        if (statisticOperations.getStatusCode().equals(HttpStatus.OK)) {
                            H3 userStatTitle = new H3("Последние операции на портале");
                            userStatTitle.getStyle().set("margin-bottom", "-8px").set("margin-top", "5px");
                            userHistory.getStyle().set("margin-top", "60px");
                            //userHistory.getStyle().set("margin-left", "500px");
                            userHistory.add(userStatTitle);

                            Grid<StatisticOperationInfo> gridUser = new Grid<>(StatisticOperationInfo.class, false);

                            gridUser.addColumn(StatisticOperationInfo::getService_type).setHeader("Тип сервиса").setAutoWidth(true);
                            gridUser.addColumn(StatisticOperationInfo::getStatistic_operation_type).setHeader("Тип операции").setAutoWidth(true);
                            gridUser.addColumn(e -> getDateString(e.getDate())).setHeader("Дата").setAutoWidth(true);

                            List<StatisticOperationInfo> statisticOperationsData = statisticOperations.getBody();
                            Collections.reverse(statisticOperationsData);
                            List<StatisticOperationInfo> data = new ArrayList<>();
                            for (int i = 0; i < 12 && i < statisticOperationsData.size(); i++) {
                                data.add(statisticOperationsData.get(i));
                            }
                            gridUser.setItems(data);

                            gridUser.setMinWidth("500px");
                            //gridUser.setMaxHeight("240px");

                            //gridUser.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
                            //gridUser.getStyle().set("margin-left", "500px");
                            //gridUser.getStyle().set("margin-top", "0px");
                            userHistory.add(gridUser);
                        } else {
                            Span err = new Span("Статистика недоступна");
                            err.getStyle().set("width", "400px");
                            userHistory.add(err);
                        }
                    }
                    horizontalLayout.add(profile, userHistory);
                    add(horizontalLayout);
                    setAlignItems(Alignment.CENTER);
                } else if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }
            }
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }

    public String getDateString(Date date) {
        return (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) +
                ":" + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) +
                " " + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + "." +
                ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "." + (date.getYear() + 1900);
    }

    public VerticalLayout makeFinishRentalLayout(Dialog finishRentalDialog, RentalInfo rentalInfo) {
        H3 headline = new H3("Закончить аренду");

        Button cancelButton = new Button("Нет", e -> finishRentalDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button finishRentalButton = new Button("Да", e -> {
            if (rentalInfo.getStatus().equals(RentalStatus.IN_PROGRESS)) {
                ResponseEntity<HttpStatus> responseEntity = frontService.finishUserRentalAndUnReserveScooter(rentalInfo.getRental_uid());

                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Аренда закончена");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }
                finishRentalDialog.close();
            }

        });
        finishRentalButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, finishRentalButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout addRentalStationLayout = new VerticalLayout(headline, buttonLayout);
        //addRentalStationLayout.setSpacing(false);
        addRentalStationLayout.setPadding(false);
        addRentalStationLayout.setAlignItems(Alignment.STRETCH);
        addRentalStationLayout.getStyle().set("width", "400px");

        return addRentalStationLayout;
    }

    public VerticalLayout makeAddScooterLayout(Dialog addScooterDialog) {
        H3 headline = new H3("Добавление типового самоката");
/*        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        TextField provider = new TextField();
        provider.setLabel("Поставщик");
        provider.setWidth("400");
        provider.setClearButtonVisible(true);
        provider.setMaxLength(100);

        IntegerField price = new IntegerField();
        price.setLabel("Стоимость (₽ в мин)");
        price.setWidth("400");
        price.setMax(10000);
        price.setMin(1);
        price.setClearButtonVisible(true);

        IntegerField max_speed = new IntegerField();
        max_speed.setLabel("Макс. скорость (км/ч)");
        max_speed.setMax(100);
        max_speed.setMin(1);
        max_speed.setWidth("400");
        max_speed.setClearButtonVisible(true);

        IntegerField charge_consumption = new IntegerField();
        charge_consumption.setLabel("Потребление энергии (% в 10 мин)");
        charge_consumption.setMax(100);
        charge_consumption.setMin(1);
        charge_consumption.setWidth("400");
        charge_consumption.setClearButtonVisible(true);
        charge_consumption.setValue(5);

        IntegerField charge_recovery = new IntegerField();
        charge_recovery.setLabel("Скорость зарядки (% в 10 мин)");
        charge_recovery.setMax(100);
        charge_recovery.setMin(1);
        charge_recovery.setWidth("400");
        charge_recovery.setClearButtonVisible(true);
        charge_recovery.setValue(10);

        VerticalLayout fieldLayout = new VerticalLayout(provider, price, max_speed, charge_consumption, charge_recovery);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> addScooterDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (provider.getValue().trim().isEmpty()) {
                Notification.show("Введите поставщика");
            } else if (price.getValue().toString().trim().isEmpty()) {
                Notification.show("Введите стоимость (₽ в мин)");
            } else if (max_speed.getValue().toString().trim().isEmpty()) {
                Notification.show("Введите макс. скорость (км/ч)");
            } else if (charge_consumption.getValue().toString().trim().isEmpty()) {
                Notification.show("Введите потребление энергии (% в 10 мин)");
            } else if (charge_recovery.getValue().toString().trim().isEmpty()) {
                Notification.show("Введите скорость зарядки (% в 10 мин)");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.createScooter(new CreateScooterRequest(
                        provider.getValue(), max_speed.getValue(), price.getValue(),
                        charge_recovery.getValue(), charge_consumption.getValue()));

                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    Notification.show("Самокат добавлен");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                addScooterDialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout addScooterLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //addScooterLayout.setSpacing(false);
        addScooterLayout.setPadding(false);
        addScooterLayout.setAlignItems(Alignment.STRETCH);
        addScooterLayout.getStyle().set("width", "400px");

        return addScooterLayout;
    }

    public VerticalLayout makeAddRentalStationLayout(Dialog addRentalStationDialog) {
        H3 headline = new H3("Добавление станции проката");
 /*       headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        TextField location = new TextField();
        location.setLabel("Местоположение");
        location.setClearButtonVisible(true);
        location.setMaxLength(100);
        location.setWidth("400");
        location.setPrefixComponent(VaadinIcon.MAP_MARKER.create());

        VerticalLayout fieldLayout = new VerticalLayout(location);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> addRentalStationDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (location.getValue().trim().isEmpty()) {
                Notification.show("Введите местоположение");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.createRentalStation(new CreateRentalStationRequest(
                        location.getValue()));
                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    Notification.show("Станция проката добавлена");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                addRentalStationDialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout addRentalStationLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //addRentalStationLayout.setSpacing(false);
        addRentalStationLayout.setPadding(false);
        addRentalStationLayout.setAlignItems(Alignment.STRETCH);
        addRentalStationLayout.getStyle().set("width", "400px");

        return addRentalStationLayout;
    }

    public VerticalLayout makeAddLocatedScooterLayout(Dialog
                                                              addLocatedScooterDialog, ResponseEntity<List<ScooterInfo>> scooters, ResponseEntity<List<RentalStationInfo>> rentalStations) {
        H3 headline = new H3("Добавление самоката на станцию");
/*        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        Select<ScooterInfo> scooter = new Select<>();
        scooter.setLabel("Выберите типовой самокат");

        if (scooters.getStatusCode().equals(HttpStatus.OK)) {
            scooter.setItems(scooters.getBody());
            scooter.setValue(scooters.getBody().get(0));
        } else {
            Notification.show("Самокаты недоступны");
        }
        scooter.setWidth("400");

        ComboBox<RentalStationInfo> rentalStation = new ComboBox<>();
        rentalStation.setLabel("Выберите станцию проката");

        if (rentalStations.getStatusCode().equals(HttpStatus.OK)) {

            rentalStation.setItems(rentalStations.getBody());
            rentalStation.setValue(rentalStations.getBody().get(0));
        } else {
            Notification.show("Станции проката недоступны");
        }
        rentalStation.setWidth("400");

        TextField provider = new TextField();
        provider.setLabel("Регистр. номер");
        provider.setWidth("400");
        provider.setClearButtonVisible(true);
        provider.setMaxLength(100);

        VerticalLayout fieldLayout = new VerticalLayout(provider, scooter, rentalStation);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> addLocatedScooterDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button addButton = new Button("Добавить", buttonClickEvent -> {
            if (provider.getValue().trim().isEmpty()) {
                Notification.show("Введите поставщика");
            } else if (scooter.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите самокат");
            } else if (rentalStation.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите станцию проката");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.createLocatedScooter(new CreateLocatedScooterRequest(
                        scooter.getValue().getScooter_uid(), rentalStation.getValue().getRental_station_uid(), provider.getValue()));
                if (responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                    Notification.show("Самокат добавлен на станцию");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                addLocatedScooterDialog.close();
            }
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, addButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout addLocatedScooterLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //addLocatedScooterLayout.setSpacing(false);
        addLocatedScooterLayout.setPadding(false);
        addLocatedScooterLayout.setAlignItems(Alignment.STRETCH);
        addLocatedScooterLayout.getStyle().set("width", "400px");

        return addLocatedScooterLayout;
    }

    public VerticalLayout makeRemoveScooterLayout(Dialog
                                                          removeScooterDialog, ResponseEntity<List<ScooterInfo>> scooters) {
        H3 headline = new H3("Удаление типового самоката");
/*        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        Select<ScooterInfo> scooter = new Select<>();
        scooter.setLabel("Выберите самокат");

        if (scooters.getStatusCode().equals(HttpStatus.OK)) {
            scooter.setItems(scooters.getBody());
            scooter.setValue(scooters.getBody().get(0));
        } else {
            Notification.show("Самокаты недоступны");
        }
        scooter.setWidth("400");

        VerticalLayout fieldLayout = new VerticalLayout(scooter);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> removeScooterDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button removeButton = new Button("Удалить", buttonClickEvent -> {
            if (scooter.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите самокат");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.removeScooter(scooter.getValue().getScooter_uid());

                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Типовой самокат удален");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                removeScooterDialog.close();
            }
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, removeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout removeScooterLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //removeScooterLayout.setSpacing(false);
        removeScooterLayout.setPadding(false);
        removeScooterLayout.setAlignItems(Alignment.STRETCH);
        removeScooterLayout.getStyle().set("width", "400px");

        return removeScooterLayout;
    }

    public VerticalLayout makeRemoveRentalStationLayout(Dialog
                                                                removeRentalStationDialog, ResponseEntity<List<RentalStationInfo>> rentalStations) {
        H3 headline = new H3("Удаление станции проката");
 /*       headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        ComboBox<RentalStationInfo> rentalStation = new ComboBox<>();
        rentalStation.setLabel("Выберите станцию проката");

        if (rentalStations.getStatusCode().equals(HttpStatus.OK)) {

            rentalStation.setItems(rentalStations.getBody());
            rentalStation.setValue(rentalStations.getBody().get(0));
        } else {
            Notification.show("Станции проката недоступны");
        }
        rentalStation.setWidth("400");

        VerticalLayout fieldLayout = new VerticalLayout(rentalStation);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> removeRentalStationDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button removeButton = new Button("Удалить", buttonClickEvent -> {
            if (rentalStation.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите станцию проката");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.removeRentalStation(rentalStation.getValue().getRental_station_uid());
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Станция проката удалена");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                removeRentalStationDialog.close();
            }
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, removeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout removeRentalStationLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //removeRentalStationLayout.setSpacing(false);
        removeRentalStationLayout.setPadding(false);
        removeRentalStationLayout.setAlignItems(Alignment.STRETCH);
        removeRentalStationLayout.getStyle().set("width", "400px");

        return removeRentalStationLayout;
    }

    public VerticalLayout makeRemoveLocatedScooterLayout(Dialog removeLocatedScooterDialog) {
        H3 headline = new H3("Удаление самоката со станции");
/*        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");*/

        Select<LocatedScooterInfo> locatedScooter = new Select<>();
        locatedScooter.setLabel("Выберите самокат на станции");
        ResponseEntity<List<LocatedScooterInfo>> locatedScooters = frontService.getLocatedScooters();
        if (locatedScooters.getStatusCode().equals(HttpStatus.OK)) {
            locatedScooter.setItems(locatedScooters.getBody());
            locatedScooter.setValue(locatedScooters.getBody().get(0));
        } else {
            Notification.show("Самокаты недоступны");
        }
        locatedScooter.setWidth("400");

        VerticalLayout fieldLayout = new VerticalLayout(locatedScooter);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(Alignment.STRETCH);

        Button cancelButton = new Button("Назад", e -> removeLocatedScooterDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button removeButton = new Button("Удалить", buttonClickEvent -> {
            if (locatedScooter.getValue().toString().trim().isEmpty()) {
                Notification.show("Выберите самокат на станции");
            } else {
                ResponseEntity<HttpStatus> responseEntity = frontService.removeLocatedScooter(locatedScooter.getValue().getLocated_scooter_uid());
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Notification.show("Самокат на станции удален");
                    UI.getCurrent().getPage().reload();
                    UI.getCurrent().navigate(ProfileView.class);
                } else if (responseEntity.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                    Notification.show("Сервис недоступен");
                }

                removeLocatedScooterDialog.close();
            }
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, removeButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);

        VerticalLayout removeLocatedScooterLayout = new VerticalLayout(headline, fieldLayout, buttonLayout);
        //removeLocatedScooterLayout.setSpacing(false);
        removeLocatedScooterLayout.setPadding(false);
        removeLocatedScooterLayout.setAlignItems(Alignment.STRETCH);
        removeLocatedScooterLayout.getStyle().set("width", "900px");

        return removeLocatedScooterLayout;
    }
}