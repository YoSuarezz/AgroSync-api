package com.sedikev.domain;

public interface DomainRule <T>{
    void validate(T data);
}
