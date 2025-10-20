package task5.validators;


import task5.exceptions.ValidateException;

public interface Validator {
    void validate(String value) throws ValidateException;
}