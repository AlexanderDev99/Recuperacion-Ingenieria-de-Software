package com.fitflow.scheduler.logic.validators;

import java.util.function.Function;


public sealed interface Result<T> {
    record Success<T>(T value) implements Result<T> {
    }

    record Failure<T>(Throwable exception) implements Result<T> {
    }

    // Método estático para crear éxitos
    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    // Método estático para crear fallos
    static <T> Result<T> failure(Throwable exception) {
        System.err.println(exception.getMessage());
        return new Failure<>(exception);
    }

    // Método funcional equivalente al 'fold' de Kotlin
    default <R> R fold(Function<T, R> onSuccess, Function<Throwable, R> onFailure) {
        if (this instanceof Success<T> s) {
            return onSuccess.apply(s.value());
        } else {
            return onFailure.apply(((Failure<T>) this).exception());
        }
    }

    // --- Métodos de verificación estilo Kotlin ---
    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    // --- Métodos de conveniencia para obtener valores ---
    default T getOrNull() {
        if (this instanceof Success<T> s)
            return s.value();
        return null;
    }

    default Throwable exceptionOrNull() {
        if (this instanceof Failure<T> f)
            return f.exception();
        return null;
    }
}

