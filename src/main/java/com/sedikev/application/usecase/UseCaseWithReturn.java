package com.sedikev.application.usecase;

public interface UseCaseWithReturn<T, R> {
    R ejecutar(T data);
}
