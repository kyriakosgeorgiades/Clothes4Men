package inputValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author kakos
 */
public class Regex {
    //Different REGEX patterns for various input fields
    private static final Pattern VALID_EMAIL = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    private static final Pattern USERNAME_PATTERN
            //Start with alphanumeric follow by a choosen characters OR Alphanumeric
            //Requires min 5 lenght
            = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
    private static final Pattern PASSWORD_PATTERN
            //Requires at least 1 of each number,character and letter 5-12 range
            = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{5,12}$");

    private static final Pattern CATEGORY_PATTERN
            = Pattern.compile("(?:Jeans|Shirts|Tshirts)");

    private static final Pattern CREDIT_NAME_PATTERN
            = Pattern.compile("^([A-Za-z]+[,.]?[ ]?|[A-Za-z]+['-]?)+$");

    private static final Pattern CREDIT_NUMBER_PATTERN
            = Pattern.compile("^\\d{10}");

    private static final Pattern CREDIT_CVV_PATTERN
            = Pattern.compile("^\\d{3}");

    public static boolean usernameVal(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.find();
    }

    public static boolean emailVal(String email) {
        Matcher matcher = VALID_EMAIL.matcher(email);
        return matcher.find();
    }

    public static boolean passwordVal(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.find();
    }

    public static boolean categoryVal(String category) {
        Matcher matcher = CATEGORY_PATTERN.matcher(category);
        return matcher.find();
    }

    public static boolean cardNameVal(String name) {
        Matcher matcher = CREDIT_NAME_PATTERN.matcher(name);
        return matcher.find();
    }

    public static boolean cardNumberVal(String card) {
        Matcher matcher = CREDIT_NUMBER_PATTERN.matcher(card);
        return matcher.find();
    }

    public static boolean cardCvvVal(String cvv) {
        Matcher matcher = CREDIT_CVV_PATTERN.matcher(cvv);
        return matcher.find();
    }
}
