package task5;

import task5.exceptions.ValidateException;
import task5.exceptions.ValidateNameException;
import task5.exceptions.ValidatePasswordException;
import task5.storage.PasswordMemoryStorage;
import task5.storage.PasswordStorage;
import task5.validators.NameValidator;
import task5.validators.PasswordLengthValidator;
import task5.validators.PasswordStrengthValidator;
import task5.validators.Validator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class zd5 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Validator> passwordValidators = List.of(
            new PasswordLengthValidator(5), new PasswordStrengthValidator()
    );

    private static final List<Validator> nameValidators = List.of(
            new NameValidator()
    ); // поработайте со списком

    public static void main(String[] args) {
        loop();
    }

    public static void loop() {
        while (true) {
            final String action = getAction();
            if ("1".equals(action)) {
                addUser();
            } else if ("2".equals(action)) {
                showUserPassword();
            } else {
                break;
            }
        }
    }

    private static void checkValidatorRules(
            final List<Validator> validators, final String value
    ) throws ValidateException {
        for (Validator validator: validators) {
            validator.validate(value);
        }
    }

    private static void addUser() {
        final PasswordStorage storage = new PasswordMemoryStorage();
        // добавьте отлов исключений ValidateNameException и ValidatePasswordException
        // закройте хранилище
        try {
            storage.open();
            System.out.println("Введите имя пользователя => ");
            final String name = scanner.nextLine();
            try {
                checkValidatorRules(nameValidators, name);
                System.out.println("Введите пароль пользователя => ");
                final String password = scanner.nextLine();
                try {
                    checkValidatorRules(passwordValidators, password);
                }
                catch (ValidatePasswordException e){
                    System.out.println(e.getMessage());
                }


                storage.store(name, password);
            }
            catch (ValidateNameException e){
                System.out.println(e.getMessage());
            }




        } catch (ValidateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка работы с хранилищем: " + e.getMessage());
        }finally {
            storage.close();
        }

    }

    private static void showUserPassword() {
        final PasswordStorage storage = new PasswordMemoryStorage();
        // добавьте отлов исключения ValidateNameException
        // закройте хранилище
        try {
            storage.open();
            System.out.println("Введите имя пользователя => ");
            final String name = scanner.nextLine();
            try {

                checkValidatorRules(nameValidators, name);
                final String password = storage.get(name);
                System.out.println(String.format("Пароль пользователя %s = %s", name, password));

            }
            catch(ValidateNameException ex){
                System.out.println(ex.getMessage());

            }




        } catch (ValidateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка работы с хранилищем: " + e.getMessage());
        }
        finally {
            storage.close();
        }

    }

    private static String getAction() {
        System.out.println("1 - добавить пользователя с паролем");
        System.out.println("2 - отобразить пароль пользователя");
        System.out.println("другие символы - выход");
        System.out.println("Выберите действие => ");
        return scanner.nextLine();
    }
}