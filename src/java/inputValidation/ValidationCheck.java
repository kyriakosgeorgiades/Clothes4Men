package inputValidation;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kakos
 */
//Method for manual Validation of NON REGEX usage.
public class ValidationCheck {

    public static boolean TextString(String name) {
        return name.equals("") || name == null;
    }
}
