package com.example.rsoi_course_work.gateway_service.model;

import java.util.List;
import java.util.Objects;

public class PaginationResponse {
    private Integer page;
    private Integer pageSize;
    private Integer totalElements;
    private List<ScooterResponse> items;

    public PaginationResponse(Integer page, Integer pageSize, Integer totalElements, List<ScooterResponse> items) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<ScooterResponse> getItems() {
        return items;
    }

    public void setItems(List<ScooterResponse> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaginationResponse)) return false;

        PaginationResponse that = (PaginationResponse) o;

        if (!Objects.equals(page, that.page)) return false;
        if (!Objects.equals(pageSize, that.pageSize)) return false;
        if (!Objects.equals(totalElements, that.totalElements))
            return false;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
        result = 31 * result + (totalElements != null ? totalElements.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PaginationResponse{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", items=" + items +
                '}';
    }
}
