package aka.hanan.hananakawiapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import aka.hanan.hananakawiapp.data.Tables.Rmoz;

public class AddRmoz1 extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private EditText etText;
    private ImageView imageRmoz;


    private Rmoz r=new Rmoz();

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    pickImageFromGallery();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Toast.makeText(this, "Extentnal permission denied", Toast.LENGTH_SHORT).show();
                }
            });


    //upload: 1 add Xml image view or button and upload button
//upload: 2 add next fileds
    private final int IMAGE_PICK_CODE = 100;// קוד מזהה לבקשת בחירת תמונה
    private final int PERMISSION_CODE = 101;//קוד מזהה לבחירת הרשאת גישה לקבצים
    private ImageButton imgBtnl;//כפתור/ לחצן לבחירת תמונה והצגתה
    private Button btnUpload;// לחצן לביצוע העלאת התמונה
    private Uri toUploadimageUri;// כתוב הקובץ(תמונה) שרוצים להעלות
    private Uri downladuri;//כתובת הקוץ בענן אחרי ההעלאה

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rmoz1);
            etText = findViewById(R.id.etText);
            btnCancel = findViewById(R.id.btnCancel);
           btnSave = findViewById(R.id.btnSave);
           imageRmoz = findViewById(R.id.imageRmoz);
           imageRmoz.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   checkImagePermission();

               }
           });
           btnSave.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   checkRmz_FB();
               }
           });
           btnCancel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   finish();

               }
           });


    }
    //FireBase دالةت تفحص اذا معطيات التسجيل صحيحة
    private void checkRmz_FB() {
        boolean isAllok = true; // يحوي نتيجة فحص الحقول ان كانت  السليمة
      String text =etText.getText().toString();

       if(text.length()<1)
       {
           isAllok = false;
           etText.setError("The Text empty");
       }

        if(toUploadimageUri==null) {
           isAllok = false;
            Toast.makeText(this, "Add Image First", Toast.LENGTH_SHORT).show();
        }
        if (isAllok) {
          r.setText(text);
          uploadImage(toUploadimageUri);
        }
    }




    private void savermoz_FB(Rmoz rmz) {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //استخراج الرقم المميز للمستعمل الذي سجل الدخول
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //اضافة كائن لمجموعة المستعملين ومعالج حدث لفحص نجاح الاضافة
        db.collection("MyRmoz").add(rmz).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddRmoz1.this, "Succeed to add User", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddRmoz1.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    private void checkImagePermission() {
        if (ContextCompat.checkSelfPermission(
                this,READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
           pickImageFromGallery();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,READ_EXTERNAL_STORAGE)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected, and what
            // features are disabled if it's declined. In this UI, include a
            // "cancel" or "no thanks" button that lets the user continue
            // using your app without granting the permission.
            Toast.makeText(this, "you must drant this permission", Toast.LENGTH_SHORT).show();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    READ_EXTERNAL_STORAGE);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
//            //בדיקה אם ההשאה לא אושרה בעבר
//            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                //רשימת ההרשאות שרוצים לבקש אישור
//                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
//                //בקשת אישור ההשאות (שולחים קוד הבקשה)
//                //התשובה תתקבל בפעולה onRequestPermissionsResult
//                requestPermissions(permissions, PERMISSION_CODE);
//            } else {
//                //permission already granted אם יש הרשאה מקודם אז מפעילים בחירת תמונה מהטלפון
//                pickImageFromGallery();
//            }
//        } else {//אם גרסה ישנה ולא צריך קבלת אישור
//            pickImageFromGallery();
//        }
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
                                        r.setImageHand(downladuri.toString());//עדכון כתובת התמונה שהועלתה

                                        savermoz_FB(r);
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


        }
    }

}



