package br.com.softhouse.dende.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String mensage){
        super(mensage);
    }
}
