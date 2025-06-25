package br.com.sabrinaweb.maventest.services.exceptions;

public class InvalidIdException extends IllegalArgumentException {
    public InvalidIdException(String s) {
        super(s);
    }
}
