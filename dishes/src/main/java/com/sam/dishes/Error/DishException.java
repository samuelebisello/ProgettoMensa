package com.sam.dishes.Error;

import com.sam.dishes.Model.Dish;

public class DishException extends RuntimeException {
    public DishException() {
        super();
    }

    public DishException(String message) {
        super(message);
    }

    public DishException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishException(Throwable cause) {
        super(cause);
    }

    protected DishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
