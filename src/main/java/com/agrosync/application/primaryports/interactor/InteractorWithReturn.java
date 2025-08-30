package com.agrosync.application.primaryports.interactor;

public interface InteractorWithReturn<T, R> {

    R ejecutar(T data);

}
