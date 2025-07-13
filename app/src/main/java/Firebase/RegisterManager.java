package Firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Core.User;

public class RegisterManager {
    public interface SignupCallback{
        void onSuccess();
        void onFailure(@NonNull Exception e);
    }
    public static void saveRegisterUser(@NonNull User user, @NonNull SignupCallback su){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Exception e = task.getException();

                    String message = (e instanceof FirebaseAuthUserCollisionException)
                            ? "Email already registered."
                            : "Something went wrong";
                    su.onFailure(new Exception(message));
                    return;
                }
                String userId = task.getResult().getUser().getUid();
                if (userId == null) {
                    su.onFailure(new Exception("Something went wrong."));
                    return;

                }
                user.setPassword(null);
                FirebaseDatabase.getInstance().getReference("users").child(userId).setValue(user)
                        .addOnSuccessListener(v -> su.onSuccess())
                        .addOnFailureListener(su::onFailure);

                su.onSuccess();






            }
        });






    }
}
