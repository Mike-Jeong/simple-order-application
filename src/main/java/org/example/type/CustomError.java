package org.example.type;

public enum CustomError {
    COMMAND_NOT_FOUND("CommandNotFountException 발생. 입력하신 명령어는 등록되지 않은 명령어 입니다."),
    PRODUCT_NOT_FOUND("ProductNotFoundException 발생. 찾으시는 상품이 없습니다.");

    private final String description;

    CustomError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
