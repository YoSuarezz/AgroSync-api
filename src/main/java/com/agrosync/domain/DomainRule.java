package com.agrosync.domain;

public interface DomainRule <T>{
    void validate(T data);
}
