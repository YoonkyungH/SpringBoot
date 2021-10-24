package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 어딘가 내장될 수 있음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // protected까진 허용해줌
    protected Address() {  // 기본 생성자
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
