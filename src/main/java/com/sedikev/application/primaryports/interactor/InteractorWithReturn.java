package com.sedikev.application.primaryports.interactor;

public interface InteractorWithReturn<T, R> {

    R ejecutar(T data);

}
