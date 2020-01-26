/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import java.util.regex.Pattern;

/**
 *
 * @author esma
 */
public class RegixMethods {
    public static boolean isValidEmail(String mail) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&-]+)@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (mail == null) {
            return false;
        }
        return pat.matcher(mail).matches();

    }

    public static boolean isValidPassword(String pass) {
        String passwordRegex
                = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,16}$";

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        if (pass == null) {
            return false;
        }
        return passwordPattern.matcher(pass).matches();
    }
}
