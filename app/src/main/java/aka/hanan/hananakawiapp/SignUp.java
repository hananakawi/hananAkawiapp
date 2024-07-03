package aka.hanan.hananakawiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import aka.hanan.hananakawiapp.data.AppDataBase;
//import aka.hanan.hananakawiapp.data.Interface.UserQuery;
import java.util.ArrayList;
import java.util.UUID;

import aka.hanan.hananakawiapp.data.Tables.Message;
import aka.hanan.hananakawiapp.data.Tables.MyUser;

/**
 * מחלקה לביצוע כניסה למשתמש חדש
 */
public class SignUp extends AppCompatActivity {
    private Button btnCancel;//לחצן לביצוע ביטול
    private Button btnSave;//לחצן לביצוע שמירה
    private TextInputEditText etE_mail;//שדה אימיל למשתמש חדש
    private TextInputEditText etpassword;//שדה סיסמה למשתמש חדש
    private TextInputEditText etrepassword;
    private TextInputEditText etname;//שדה לשם המשתמש


    //upload: 1 add Xml image view or button and upload button
//upload: 2 add next fileds
    private final int IMAGE_PICK_CODE = 100;// קוד מזהה לבקשת בחירת תמונה
    private final int PERMISSION_CODE = 101;//קוד מזהה לבחירת הרשאת גישה לקבצים
    private ImageButton imgBtnl;//כפתור/ לחצן לבחירת תמונה והצגתה
    private Button btnUpload;// לחצן לביצוע העלאת התמונה
    private Uri toUploadimageUri;// כתוב הקובץ(תמונה) שרוצים להעלות
    private Uri downladuri;//כתובת הקוץ בענן אחרי ההעלאה
    MyUser user = new MyUser();

    /**
     *  הפעולה שבונה new activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etE_mail = findViewById(R.id.etEmail);
        etpassword = findViewById(R.id.etpassword);
        etrepassword = findViewById(R.id.etrepassword);
        etname = findViewById(R.id.etname);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        //upload: 3
        imgBtnl = findViewById(R.id.imgButton);
        imgBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload: 8
                checkPermission();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSignUP_FB();
            }
        });

    }


    public void onClickSavefirebase(View v) {



    }

    public void onClickCancelfirebase(View v) {
        Intent i = new Intent(SignUp.this, SignIn.class);

        finish();
    }


    //FireBase دالةت تفحص اذا معطيات التسجيل صحيحة
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
       //if(toUploadimageUri==null) {
//            isAllok = false;
//            Toast.makeText(this, "Add Image First", Toast.LENGTH_SHORT).show();
//        }
        if (isAllok) {
            //كائن لعملية تسجيل
            FirebaseAuth auth = FirebaseAuth.getInstance();
            //יצירת חשבון בעזרת מיל ו סיסמא
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override //התגובה שמתקבל הנסיון הרשיום בען
                public void onComplete(@NonNull Task<AuthResult> task) // הפרמטר מכיל מידע מהשרת על תוצאת הבקשה לרישום
                {
                    if (task.isSuccessful()) {//
                        user.setEmail(email);
                        //todo set phone
                        uploadImage(toUploadimageUri);
                        Toast.makeText(SignUp.this, "Signing up Succeeded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, "Signing up Failed", Toast.LENGTH_SHORT).show();
                        etE_mail.setError(task.getException().getMessage());//

                    }
                }
            });
        }
    }




    private void saveUser_FB(MyUser user) {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //استخراج الرقم المميز للمستعمل الذي سجل الدخول
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //اضافة كائن لمجموعة المستعملين ومعالج حدث لفحص نجاح الاضافة
        db.collection("MyUsers").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Succeed to add User", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * نقل الى شاشة اخرى
     *
     * @param v
     */
    public void onClicksaveSignUp(View v) {
        checkAndSignUP_FB();
    }


    public void onClickCancelFireBase(View v) {
        finish();
    }

    private void pickImageFromGallery() {
        //implicit intent (מרומז) to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);//הפעלתה האינטנט עם קוד הבקשה
    }


    //upload: 5:handle result of picked images

    /**
     * @param requestCode מספר הקשה
     * @param resultCode  תוצאה הבקשה (אם נבחר משהו או בוטלה)
     * @param data        הנתונים שנבחרו
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //אם נבחר משהו ואם זה קוד בקשת התמונה
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //a עידכון תכונת כתובת התמונה
            toUploadimageUri = data.getData();//קבלת כתובת התמונה הנתונים שניבחרו
            imgBtnl.setImageURI(toUploadimageUri);// הצגת התמונה שנבחרה על רכיב התמונה
        }
    }
    //upload: 6

    /**
     * בדיקה האם יש הרשאה לגישה לקבצים בטלפון
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
            //בדיקה אם ההשאה לא אושרה בעבר
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //רשימת ההרשאות שרוצים לבקש אישור
                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                //בקשת אישור ההשאות (שולחים קוד הבקשה)
                //התשובה תתקבל בפעולה onRequestPermissionsResult
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted אם יש הרשאה מקודם אז מפעילים בחירת תמונה מהטלפון
                pickImageFromGallery();
            }
        } else {//אם גרסה ישנה ולא צריך קבלת אישור
            pickImageFromGallery();
        }
    }
    //upload: 7

    /**
     * @param requestCode  The request code passed in מספר בקשת ההרשאה
     * @param permissions  The requested permissions. Never null. רשימת ההרשאות לאישור
     * @param grantResults The grant results for the corresponding permissions תוצאה עבור כל הרשאה
     *                     PERMISSION_GRANTED אושר or PERMISSION_DENIED נדחה . Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {//בדיקת קוד בקשת ההרשאה
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission was granted אם יש אישור
                pickImageFromGallery();
            } else {
                //permission was denied אם אין אישור
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            //יצירת דיאלוג התקדמות
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();//הצגת הדיאלוג
            //קבלת כתובת האחסון בענן
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            //יצירת תיקיה ושם גלובלי לקובץ
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            // יצירת ״תהליך מקביל״ להעלאת תמונה
            ref.putFile(filePath)
                    //הוספת מאזין למצב ההעלאה
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();// הסתרת הדיאלוג
                                //קבלת כתובת הקובץ שהועלה
                                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        downladuri = task.getResult();
                                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                        user.setImage(downladuri.toString());//עדכון כתובת התמונה שהועלתה
                                        // استخراج النص من حقل الايميل
                                        String email = etE_mail.getText().toString();

                                        // استخراج نص كلمة المرور
                                        String pass = etpassword.getText().toString();

                                        // استخراج تكرار كلمة المرور
                                        String repass = etrepassword.getText().toString();

                                        // استخراج الاسم
                                        String name = etname.getText().toString();


                                        saveUser_FB(user);
                                    }
                                });
                            } else {
                                progressDialog.dismiss();//הסתרת הדיאלוג
                                Toast.makeText(getApplicationContext(), "Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    //הוספת מאזין שמציג מהו אחוז ההעלאה
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //חישוב מה הגודל שהועלה

                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            // استخراج النص من حقل الايميل
            String email = etE_mail.getText().toString();

            // استخراج نص كلمة المرور
            String pass = etpassword.getText().toString();

            // استخراج تكرار كلمة المرور
            String repass = etrepassword.getText().toString();

            // استخراج الاسم
            String name = etname.getText().toString();


            saveUser_FB(user);

        }
    }

}





