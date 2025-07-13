package Firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Core.User;

public class Firebasemanager {
    private static Firebasemanager instance;
    private Firebasemanager(){}
    public static Firebasemanager getInstance() {
        if (instance == null) {
            instance = new Firebasemanager();
        }
        return instance;
    }
    public static void setInstanceToMock(Firebasemanager mock) {
        instance = mock;
    }
    public void RegistUser(User user, RegisterManager.SignupCallback callback){
           RegisterManager.saveRegisterUser(user,callback);
    }
}
