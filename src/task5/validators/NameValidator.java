package task5.validators;

import task5.exceptions.ValidateException;
import task5.exceptions.ValidateNameException;


public class NameValidator implements Validator {
// допишите код класса
    @Override
    public void validate(final String value) throws ValidateException{
        if (value.isBlank()|| value.isEmpty())
        {
            throw new ValidateNameException("Имя не должно быть пустым");
        }

    };

}