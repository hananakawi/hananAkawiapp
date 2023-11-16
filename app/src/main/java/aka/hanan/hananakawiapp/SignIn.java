package aka.hanan.hananakawiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import aka.hanan.hananakawiapp.data.AppDataBase;
import aka.hanan.hananakawiapp.data.Interface.UserQuery;
import aka.hanan.hananakawiapp.data.MainActivity;

public class SignIn extends AppCompatActivity {
    private TextInputEditText etEmail;
    private TextInputEditText etpassword;
    private Button btnSignIn;
    private Button  btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
    private void checkEmailPass()
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
            Toast.makeText(this,"All ok",Toast.LENGTH_SHORT).show();
         // بناء قاعدة بيانات وارجاع مؤشر عليها
            AppDataBase db=AppDataBase.getDB(getApplicationContext());
            //مؤشر لكائن عمليات الجدول
            UserQuery userQuery = db.getUserQuery();
            UserQuery MyUser = userQuery.checkEmailPassw(email, pass);
            if (MyUser==null){
                Toast.makeText(this, "wrong email or password", Toast.LENGTH_LONG).show();

            }
            else
            {
                Intent i=new Intent(SignIn.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
    public void onClickSignUP (View v)
    {

        //to open new activity from current to next activity
        Intent i= new Intent(SignIn.this,  SignUp.class);
        startActivity(i);
    }

    public void onClickSignin (View v)
    {
        checkEmailPass();

    }
    public void onClickCancel(View v) {
        Intent i = new Intent(SignIn.this, SplashScreen.class);
        startActivity(i);
        finish();
    }


}


