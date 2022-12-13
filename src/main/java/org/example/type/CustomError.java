package org.example.type;

public enum CustomError {
    COMMAND_NOT_FOUND("CommandNotFountException 발생. 입력하신 명령어는 등록되지 않은 명령어 입니다."),
    PRODUCT_NOT_FOUND("ProductNotFoundException 발생. 찾으시는 상품이 없습니다."),
    QUANTITY_NOT_ACCEPTABLE("QuantityNotAcceptableException 발생. 1개 이상의 갯수를 입력해주세요."),
    SOLD_OUT("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");

    private final String description;

    CustomError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
