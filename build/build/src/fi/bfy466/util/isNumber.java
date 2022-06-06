package fi.bfy466.util;


/**
 * Special function for checking if entered value is numeric
 * 
 * @return true if argument is numeric
 */
public class isNumber {


public static boolean CheckIsNumber(String str) {

        if(str == null)
            return false;
        else
            return str.matches("^(-)?\\d+(\\.\\d+)?$");
   }
}