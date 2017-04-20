package com.example.mcnewz.regencycare.manager;

/**
 * Created by JAME on 14-Feb-17.
 */

public class config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://icareuserver.comscisau.com/icare/androidTest/departLogin.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String URL_DATA = "http://icareuserver.comscisau.com/icare/androidTest/ReadDataDepart.php";
    public static final String USERNAME_SHARED = "user";

    //This would be used to store the email of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static int id = 1;
    public static String token = "tokrn";
    public static final String TOKEN_URL = "http://icareuserver.comscisau.com/icare/androidTest/updateTokenRegency.php";

}
