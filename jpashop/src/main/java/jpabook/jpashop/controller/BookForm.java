package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    // 상품 관련 특성
    private Long id;

    private String  name;
    private int price;
    private int stockQuantity;

    // 책 관련 특성
    private String author;
    private String isbn;
}
