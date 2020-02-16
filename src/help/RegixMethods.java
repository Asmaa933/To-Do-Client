/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

/**
 *
 * @author esma
 */
public class RegixMethods {

    public static boolean isValidEmail(String mail) {

        String emailRegex = "^([A-z]|[0-9])([A-z]|[0-9]|(\\.)){5,30}@[a-z]{2,9}(\\.)[a-z]{2,3}$";

        return mail.matches(emailRegex);
    }

    public static boolean isValidPassword(String pass) {
        String passwordRegex
                = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,50}$";
        return pass.matches(passwordRegex);

    }

    public static boolean isValidName(String name) {
        String NameRegex = "^[a-zA-Z]+((['_. -][a-zA-Z ])?[a-zA-Z]*)*$";
        return name.matches(NameRegex);

    }

    public static boolean isValidIP(String ip) {
        String ipRegex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipRegex);
    }
}
