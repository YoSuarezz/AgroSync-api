package com.sedikev.application.usecase;

public interface UseCaseWithoutReturn<T> {

    void ejecutar(T data);
}
