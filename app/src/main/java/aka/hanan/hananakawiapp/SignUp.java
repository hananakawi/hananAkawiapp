package aka.hanan.hananakawiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

//import aka.hanan.hananakawiapp.data.AppDataBase;
//import aka.hanan.hananakawiapp.data.Interface.UserQuery;
import aka.hanan.hananakawiapp.data.Tables.MyUser;

public class SignUp extends AppCompatActivity {
    private Button btnCancel;
    private Button btnSave;
    private TextInputEditText etE_mail;
    private TextInputEditText etpassword;
    private TextInputEditText etrepassword;
    private TextInputEditText etname;


    //upload: 1 add Xml image view or button and upload button
//upload: 2 add next fileds
    private final int IMAGE_PICK_CODE=100;// קוד מזהה לבקשת בחירת תמונה
    private final int PERMISSION_CODE=101;//קוד מזהה לבחירת הרשאת גישה לקבצים
    private ImageButton imgBtnl;//כפתור/ לחצן לבחירת תמונה והצגתה
    private Button btnUpload;// לחצן לביצוע העלאת התמונה
    private Uri toUploadimageUri;// כתוב הקובץ(תמונה) שרוצים להעלות
    private Uri downladuri;//כתובת הקוץ בענן אחרי ההעלאה

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etE_mail= findViewById(R.id.etEmail);
        etpassword=findViewById(R.id.etpassword);
       etrepassword= findViewById(R.id.etrepassword);
        etname= findViewById(R.id.etname);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);

        //upload: 3
        imgBtnl=findViewById(R.id.imgButton);
        imgBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

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
                        saveUser_FB(email,pass,name);
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
    //FireBase
    private void checkAndSignUP_FB() {
        boolean isAllok = true; // يحوي نتيجة فحص الحقول ان كانت  السليمة
        String email = etE_mail.getText().toString();

        //استخراج النص كلمة المرور
        String password = etpassword.getText().toString();
        //استخراج نص الذي يحوي على الاسم
        String name = etname.getText().toString();
        // استخراج النص الذي يحوي على كلمة المرور الجديدة
        String rePaswword = etrepassword.getText().toString();
        //فحص الايميل ان كان طوله اقل من 6 او لا يحوي على @ فهو خطأ
        if (email.length() < 6 || email.contains("@") == false) {
            // تعديل المتغير و يدل على انه فحص و يعطي نتيجة خاطئة
            isAllok = false;
            //عرض النتيجة خطأ في حقل الايميل
            etE_mail.setError("worng email");
        }
        //فحص كلمة المرور اذا كانت اقل من 8 او تحتوي على فراغ
        if (password.length() < 8 || password.contains(" ") == true) {
            //تعديل المتغير على ان يعطي نتيجة خاطئة
            isAllok = false;
            //عرض نتيجة خطأ في حقل كلمة المرور
            etpassword.setError("worng password");
        }
        //فحص الاسم يجب ان لا يحتوي على اقل من 3 حروف
        if (name.length() < 3) {
            //تعديل المتغير على ان يعطي نتيجة خاطئة
            isAllok = false;
            //عرض نتيجة اسم خاطئ في حقل الاسم
           etname.setError("worng name");
        }
        //فحص اذا كانت كلمة المرور الجديدة نفس الكلمة القديمة(لتأكيد كبمة المرور)
        if (rePaswword.equals(password) == false) {
            //تعديل المتغير على ان يعطي نتيجة خاطئة
            isAllok = false;
            //عرض نتيجة خطأ في الحقل
            etrepassword.setError("worng password");
        }
        if (isAllok) {
            //كائن لعملية تسجيل
            FirebaseAuth auth = FirebaseAuth.getInstance();
            //יצירת חשבון בעזרת מיל ו סיסמא
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override //התגובה שמתקבל הנסיון הרשיום בען
                public void onComplete(@NonNull Task<AuthResult> task) // הפרמטר מכיל מידע מהשרת על תוצאת הבקשה לרישום
                {
                    if (task.isSuccessful()) {//
                        saveUser_FB(email,name,password);
                        Toast.makeText(SignUp.this, "Signing up Succeeded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, "Signing up Failed", Toast.LENGTH_SHORT).show();
                       etE_mail.setError(task.getException().getMessage());//
                    }
                }
            });
        }
    }
    private void saveUser_FB( String name ,String email,String pass)
    {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db= FirebaseFirestore.getInstance();

        //استخراج الرقم المميز للمستعمل الذي سجل الدخول
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //بناء الكائن الذي سيتم حفظه
        MyUser user=new MyUser();
        user.setName(name);
        user.setEmail(email);
        user.setPass(pass);

        //اضافة كائن لمجموعة المستعملين ومعالج حدث لفحص نجاح الاضافة
        db.collection("MyUsers").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignUp.this,"Succeed to add User",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(SignUp.this,"Failed to add user",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    /**
     * نقل الى شاشة اخرى
     * @param v
     */
    public void onClicksaveSignUp(View v) {
        checkAndSignUP_FB();
    }



    public void onClickCancelFireBase(View v)
    {
        finish();
    }
    private void pickImageFromGallery(){
        //implicit intent (מרומז) to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);//הפעלתה האינטנט עם קוד הבקשה
    }

}





