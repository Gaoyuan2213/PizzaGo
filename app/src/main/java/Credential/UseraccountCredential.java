package Credential;

public class UseraccountCredential {
    public static boolean checkifPasswordSame(String password1, String password2){
        if(password1.equals(password2)){
            return true;
        }
        return false;
    }
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        int alphanumCount = 0;
        int specialCount = 0;
        for(char c : password.toCharArray()){
            if(Character.isLetterOrDigit(c)){
                alphanumCount++;
            }else if(c == '!' || c == '@' || c == '#') {
                specialCount++;
            }

        }
        if(alphanumCount>=7&&specialCount>=1){
            return true;
        }
        return false;
    }


}
