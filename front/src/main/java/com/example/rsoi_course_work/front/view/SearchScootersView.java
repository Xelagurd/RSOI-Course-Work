package com.example.rsoi_course_work.front.view;

import com.example.rsoi_course_work.front.FrontService;
import com.example.rsoi_course_work.front.model.located_scooter.LocatedScooterInfo;
import com.example.rsoi_course_work.front.model.located_scooter.PaginationResponse;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Route(value = "scooters", layout = HeaderLayout.class)
@PageTitle("Самокаты")
public class SearchScootersView extends VerticalLayout {
    private FrontService frontService;
    private int itemsPerPage = 12;
    private int currentPageNumber = 1;
    private boolean showAll = true;

    public SearchScootersView(FrontService frontService) {
        this.frontService = frontService;
    }

    @PostConstruct
    public void init() {
        try {
            Grid<LocatedScooterInfo> grid = new Grid<>();

            //this.getStyle().set("padding", "0");
            ResponseEntity<PaginationResponse> scooters = frontService.getLocatedScooters(currentPageNumber, itemsPerPage, showAll);
            if (scooters.getStatusCode().equals(HttpStatus.OK)) {

                List<LocatedScooterInfo> initialItems = scooters.getBody().getItems();
            /*List<LocatedScooterInfo> data = new ArrayList<>();
            for (int i = 0, j = 0; j < 12 && i < initialItems.size(); i++) {
                if (initialItems.get(i).getScooter() != null &&
                        initialItems.get(i).getRental_station() != null) {
                    data.add(initialItems.get(i));
                    j++;
                }
            }*/
                GridListDataView<LocatedScooterInfo> dataView = grid.setItems(initialItems);
                Grid.Column<LocatedScooterInfo> provider = grid.addColumn(
                        e -> {
                            if (e.getScooter() != null) {
                                return e.getScooter().getProvider();
                            } else {
                                return "null";
                            }
                        }
                ).setAutoWidth(true);
                Grid.Column<LocatedScooterInfo> location = grid.addColumn(
                        e -> {
                            if (e.getRental_station() != null) {
                                return e.getRental_station().getLocation();
                            } else {
                                return "null";
                            }
                        }
                ).setAutoWidth(true);
                Grid.Column<LocatedScooterInfo> price = grid.addColumn(
                        e -> {
                            if (e.getScooter() != null) {
                                return e.getScooter().getPrice();
                            } else {
                                return "null";
                            }
                        }
                ).setAutoWidth(true);
                Grid.Column<LocatedScooterInfo> max_speed = grid.addColumn(
                        e -> {
                            if (e.getScooter() != null) {
                                return e.getScooter().getMax_speed();
                            } else {
                                return "null";
                            }
                        }
                ).setAutoWidth(true);

            /*grid.setWidth("100%");
            grid.setWidthFull();
            grid.getStyle().set("margin", "0").set("margin-top", "-12px");
            grid.getHeaderRows().clear();

            provider.setWidth("200px");
            location.setWidth("200px");
            price.setWidth("200px");
            max_speed.setWidth("200px");*/

                HeaderRow headerRow = grid.appendHeaderRow();
                ScooterFilter Filter = new ScooterFilter(dataView);
                headerRow.getCell(provider).setComponent(
                        createFilterHeader("Поставщик", Filter::setProvider));
                headerRow.getCell(location).setComponent(
                        createFilterHeader("Местоположение", Filter::setLocation));
                headerRow.getCell(price).setComponent(
                        createFilterHeader("Стоимость", Filter::setPrice));
                headerRow.getCell(max_speed).setComponent(
                        createFilterHeader("Макс. скорость", Filter::setMax_speed));
                grid.setHeightByRows(true);

                H5 currPage = new H5(String.valueOf(currentPageNumber));
                currPage.getStyle().set("margin-top", "0.8em");

                Button previousButton = new Button("Предыдущая", e -> {
                    if (currentPageNumber <= 1) {
                        return;
                    }

                    ResponseEntity<PaginationResponse> scootersPrev = frontService.getLocatedScooters(--currentPageNumber, itemsPerPage, showAll);
                    if (scootersPrev.getStatusCode().equals(HttpStatus.OK)) {
                        List<LocatedScooterInfo> prevPageItems = scootersPrev.getBody().getItems();
                    /*List<LocatedScooterInfo> data2 = new ArrayList<>();
                    for (int i = 0, j = 0; j < 12 && i < prevPageItems.size(); i++) {
                        if (prevPageItems.get(i).getScooter() != null &&
                                prevPageItems.get(i).getRental_station() != null) {
                            data2.add(prevPageItems.get(i));
                            j++;
                        }
                    }*/
                        GridListDataView<LocatedScooterInfo> prevDataView = grid.setItems(prevPageItems);
                        ScooterFilter prevScooterFilter = new ScooterFilter(prevDataView);
                        headerRow.getCell(provider).setComponent(
                                createFilterHeader("Поставщик", prevScooterFilter::setProvider));
                        headerRow.getCell(location).setComponent(
                                createFilterHeader("Местоположение", prevScooterFilter::setLocation));
                        headerRow.getCell(price).setComponent(
                                createFilterHeader("Стоимость", prevScooterFilter::setPrice));
                        headerRow.getCell(max_speed).setComponent(
                                createFilterHeader("Макс. скорость", prevScooterFilter::setMax_speed));
                        currPage.setText(String.valueOf(currentPageNumber));
                    } else if (scootersPrev.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                        Notification.show("Сервис недоступен");
                    }
                });
                previousButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                Button nextButton = new Button("Следующая", e -> {
                    if (currentPageNumber >= scooters.getBody().getTotalElements() / itemsPerPage + 1) {
                        return;
                    }

                    ResponseEntity<PaginationResponse> scootersNext = frontService.getLocatedScooters(++currentPageNumber, itemsPerPage, showAll);
                    if (scootersNext.getStatusCode().equals(HttpStatus.OK)) {
                        List<LocatedScooterInfo> nextPageItems = scootersNext.getBody().getItems();
                    /*List<LocatedScooterInfo> data3 = new ArrayList<>();
                    for (int i = 0, j = 0; j < 12 && i < nextPageItems.size(); i++) {
                        if (nextPageItems.get(i).getScooter() != null &&
                                nextPageItems.get(i).getRental_station() != null) {
                            data3.add(nextPageItems.get(i));
                            j++;
                        }
                    }*/
                        GridListDataView<LocatedScooterInfo> nextDataView = grid.setItems(nextPageItems);
                        ScooterFilter nextScooterFilter = new ScooterFilter(nextDataView);
                        headerRow.getCell(provider).setComponent(
                                createFilterHeader("Поставщик", nextScooterFilter::setProvider));
                        headerRow.getCell(location).setComponent(
                                createFilterHeader("Местоположение", nextScooterFilter::setLocation));
                        headerRow.getCell(price).setComponent(
                                createFilterHeader("Стоимость", nextScooterFilter::setPrice));
                        headerRow.getCell(max_speed).setComponent(
                                createFilterHeader("Макс. скорость", nextScooterFilter::setMax_speed));
                        currPage.setText(String.valueOf(currentPageNumber));
                    } else if (scootersNext.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                        Notification.show("Сервис недоступен");
                    }
                });
                nextButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

                HorizontalLayout horizontalLayout = new HorizontalLayout();
                horizontalLayout.add(previousButton, currPage, nextButton);
                horizontalLayout.getStyle().set("margin-left", "16px");

                grid.addItemClickListener(item -> {
                    UI.getCurrent().navigate("scooter/" + item.getItem().getLocated_scooter_uid());
                });

                add(grid);
                add(horizontalLayout);
            } else if (scooters.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                Notification.show("Сервис недоступен");
            }
        } catch (Exception e) {
            Notification.show("Произошла непредвиденная ошибка");
        }
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
/*        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");*/

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));

        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static class ScooterFilter {
        private final GridListDataView<LocatedScooterInfo> dataView;

        private String provider = "";
        private String location = "";
        private String price = "";
        private String max_speed = "";

        public ScooterFilter(GridListDataView<LocatedScooterInfo> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setProvider(String provider) {
            this.provider = provider;
            this.dataView.refreshAll();
        }

        public void setLocation(String location) {
            this.location = location;
            this.dataView.refreshAll();
        }

        public void setPrice(String price) {
            this.price = price;
            this.dataView.refreshAll();
        }

        public void setMax_speed(String max_speed) {
            this.max_speed = max_speed;
            this.dataView.refreshAll();
        }

        public boolean test(LocatedScooterInfo scooterResponse) {
            boolean flag1 = provider.isEmpty();
            if (!provider.isEmpty()) {
                if (scooterResponse.getScooter() != null) {
                    flag1 = scooterResponse.getScooter().getProvider().toLowerCase().contains(provider.toLowerCase());
                }
            }

            boolean flag2 = location.isEmpty();
            if (!location.isEmpty()) {
                if (scooterResponse.getRental_station().getLocation() != null) {
                    flag2 = scooterResponse.getRental_station().getLocation().toLowerCase().contains(location.toLowerCase());
                }
            }

            boolean flag3 = price.isEmpty();
            if (!price.isEmpty()) {
                try {
                    if (scooterResponse.getScooter() != null) {
                        flag3 = scooterResponse.getScooter().getPrice().equals(Integer.valueOf(price));
                    }
                } catch (NumberFormatException e) {

                }
            }

            boolean flag4 = max_speed.isEmpty();
            if (!max_speed.isEmpty()) {
                try {
                    if (scooterResponse.getScooter() != null) {
                        flag4 = scooterResponse.getScooter().getMax_speed().equals(Integer.valueOf(max_speed));
                    }
                } catch (NumberFormatException e) {

                }
            }

            return flag1 && flag2 && flag3 && flag4;
        }
    }
}
