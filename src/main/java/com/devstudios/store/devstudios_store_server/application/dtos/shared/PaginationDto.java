package com.devstudios.store.devstudios_store_server.application.dtos.shared;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;




public class PaginationDto {

    @Min(value=0)
    int page = 0;

    @Min(value=1)
    @Max(value=100)
    int elements = 10;

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getElements() {
        return elements;
    }
    public void setElements(int elements) {
        this.elements = elements;
    }

}
