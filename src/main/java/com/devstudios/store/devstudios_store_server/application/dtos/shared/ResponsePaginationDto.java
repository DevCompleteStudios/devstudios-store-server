package com.devstudios.store.devstudios_store_server.application.dtos.shared;




public class ResponsePaginationDto<T> extends ResponseDto<T> {

    private int maxPage;
    private Long allCountElements;



    public ResponsePaginationDto(int maxPage, Long allCountElements, String token, int status, T data) {
        super(token, status, data);
        this.maxPage = maxPage;
        this.allCountElements = allCountElements;
    }


    public int getMaxPage() {
        return maxPage;
    }
    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
    public Long getAllCountElements() {
        return allCountElements;
    }
    public void setAllCountElements(Long allCountElements) {
        this.allCountElements = allCountElements;
    }

}
