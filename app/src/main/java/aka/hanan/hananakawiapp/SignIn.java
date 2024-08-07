package aka.hanan.hananakawiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import aka.hanan.hananakawiapp.data.MainActivity;

/**
 * מחלקה לביצוע כניסה למשתמש קיים
 */
public class SignIn extends AppCompatActivity {
    private TextInputEditText etEmail;//שדה אימיל למשתמש קיים
    private TextInputEditText etpassword;//שדה סיסמה למשתמש קיים
    private Button btnSignIn;//לחצן לביצוע כניסה
    private Button  btnSignUp;//לחצן למעבר מסך רישום
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if(FirebaseAuth.getInstance().getCurrentUser()!= null)//هل تم التسجيل من قبل
        {
            Intent i= new Intent(SignIn.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        etEmail=findViewById(R.id.etEmail);
        etpassword=findViewById(R.id.etpassword);
    }

    /**
     * الدالة تفحص اذا الايميل وكلمة السر صحيحة
     */
    private void checkEmailPassFb()
    {
        boolean isAllOk=true; // يحوي نتيجة فحص الحقول ان كانت سليمة

        // استخراج النص من حقل الايميل
        String email= etEmail.getText().toString();

        // استخراج نص كلمة المرور
        String pass=etpassword.getText().toString();

        // فحص الايميل ان كان طوله أقل من 6 أو لا يحتوي على @ فهو خاطئ
        if (email.length()<6 || email.contains("@")== false)
        {
            // تعديل المتغير ليدل على أن الفحص يعطي نتيجة خاطئة
            isAllOk=false;
            // عرض ملاحظة خطأ على الشاشة داخل حقل البريد
            etEmail.setError("wrong email");
        }
        if (pass.length()<8 || pass.contains(" ")== true)
        {
            isAllOk=false;
            etpassword.setError("Wrong password");
        }


        if (isAllOk)
        {

            FirebaseAuth auth = FirebaseAuth.getInstance();//بناء كائن لعملية التسجيل
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())//هل العملية ناجحة
                    {
                        Toast.makeText(SignIn.this, "Signing In succeeded", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignIn.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(SignIn.this, "Signing In failed", Toast.LENGTH_SHORT).show();
                        etEmail.setError(task.getException().getMessage());//ظهور رسالة الخاطئة من السحابة

                    }
                }
            });
        }
          }

    public void onClickSignUP (View v)
    {

        //to open new activity from current to next activity
        Intent i= new Intent(SignIn.this,SignUp.class);
        startActivity(i);
    }

    public void onClickSigninFIREBASE (View v)
    {
        checkEmailPassFb();

    }
    public void onClickCancel(View v) {
        Intent i = new Intent(SignIn.this, SplashScreen.class);
        startActivity(i);
        finish();
    }
  }





