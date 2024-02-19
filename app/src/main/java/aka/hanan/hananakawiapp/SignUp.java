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

//import aka.hanan.hananakawiapp.data.AppDataBase;
//import aka.hanan.hananakawiapp.data.Interface.UserQuery;
import aka.hanan.hananakawiapp.data.Tables.User;

public class SignUp extends AppCompatActivity {
    private Button btnCancel;
    private Button btnSave;
    private TextInputEditText etE_mail;
    private TextInputEditText etpassword;
    private TextInputEditText etrepassword;
    private TextInputEditText etname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onClickSavefirebase(View v) {
        checkDataFB();


    }

    public void onClickCancelfirebase(View v) {
        Intent i = new Intent(SignUp.this, SignIn.class);

        finish();
    }


    private void checkDataFB() {
        boolean isAllOk = true; // يحوي نتيجة فحص الحقول ان كانت سليمة

        // استخراج النص من حقل الايميل
        String email = etE_mail.getText().toString();

        // استخراج نص كلمة المرور
        String pass = etpassword.getText().toString();

        // استخراج تكرار كلمة المرور
        String repass = etrepassword.getText().toString();

        // استخراج الاسم
        String name = etname.getText().toString();

        // فحص الايميل ان كان طوله أقل من 6 أو لا يحتوي على @ فهو خاطئ
        if (email.length() < 6 || email.contains("@") == false) {
            // تعديل المتغير ليدل على أن الفحص يعطي نتيجة خاطئة
            isAllOk = false;
            // عرض ملاحظة خطأ على الشاشة داخل حقل البريد
            etE_mail.setError("wrong email");
        }

        // يجب أن تكون كلمة المرر من ثمانية ولا تحوي فراغ اذا كان بها هذه الأشياء فانه يرجع نص بأن كلمة المرور خاطئة
        if (pass.length() < 8 || pass.contains(" ") == true) {
            isAllOk = false;
            etpassword.setError("Wrong password");
        }

        //يجب أن يكون تكرار كلمة المرور نفس كلمة المرور
        if (repass.equals(pass) == false) {
            isAllOk = false;
            etrepassword.setError("not the same");
        }

        //يجب أن يكون الاسم اجباري ولو حر واحد لو ترك الاسم بدون أن يكتبه فانه يرجع نص بانه فارغ
        if (name.length() < 1) {
            isAllOk = false;
            etname.setError("name is empty");
        }
        if (isAllOk) {
            Toast.makeText(this, "All ok", Toast.LENGTH_SHORT).show();
        }
        if (isAllOk) {
            Toast.makeText(this, "All ok", Toast.LENGTH_SHORT).show();
            FirebaseAuth auth = FirebaseAuth.getInstance();//بناء كائن لعملية التسجيل
            // بناء حساب بمساعدة الميل وكلمة السر
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override//الاستجابة الواردة من محاولة التسجيل في السحابة
                public void onComplete(@NonNull Task<AuthResult> task) //البارمتر يحتوي على معلومات من الخادم حول نتيجة طلب التسجيل
                {
                    if (task.isSuccessful())//هل العملية ناجحة
                    {
                        Toast.makeText(SignUp.this, "Signingup succeeded", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "Signingup failed", Toast.LENGTH_SHORT).show();
                        etE_mail.setError(task.getException().getMessage());//ظهور رسالة الخاطئة من السحابة

                    }
                }
            });
        }



    }
        }





