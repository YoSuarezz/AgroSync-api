package com.agrosync.application.usecase;

public interface UseCaseWithoutReturn<T> {

    void ejecutar(T data);
}
