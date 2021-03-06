package com.cea.cares;

public class URLs {

//  IP 10.0.2.2 will connect emulator with the localhost of your machine
//    private static final String ROOT_URL = "http://10.0.2.2/project/Api.php?apicall=";

//  192.168.209.231 is my machine IP address connected on one wifi network. Change it with your machine's IP address
private static final String ROOT_URL = "http://192.168.209.231/project/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_UPDATE= ROOT_URL + "update";
//    public static final String URL_PREDICT= "http://10.0.2.2:5000/predict?id=";
public static final String URL_PREDICT= "http://192.168.209.231:5000/predict?id=";
    public static final String URL_REFRESH= ROOT_URL + "refresh";
    public static final String URL_WEEK= ROOT_URL + "week";
    public static final String URL_FOOD= ROOT_URL + "food";
    public static final String URL_EXC= ROOT_URL + "exc";
    public static final String URL_DATE= ROOT_URL + "date";
}
