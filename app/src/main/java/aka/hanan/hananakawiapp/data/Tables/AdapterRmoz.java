package aka.hanan.hananakawiapp.data.Tables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import aka.hanan.hananakawiapp.R;

public class AdapterRmoz extends ArrayAdapter<Rmoz> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
    private Rmoz current;

    /**
     * פעולה בונה מתאם
     * @param context מסך אקטיבי
     * @param resource עיצוב של פריט שיציג הנותנים של העצם
     */
    public AdapterRmoz(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout=resource;
    }
    /**
     * * בונה פריט גרפי אחד בהתאם לעיצוב והצגת נתוני העצם עליו
     * @param position מיקום הפריט החל מ 0
     * @param convertView
     * @param parent רכיב האוסף שיכיל את הפריטים כמו listview
     * @return  . פריט גרפי שמציג נתוני עצם א
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //בניית הפריט הגרפי מתו קובץ העיצוב
        View vitem= convertView;
        if(vitem==null) {
            vitem= LayoutInflater.from(getContext()).inflate(itemLayout,parent,false);
            //קפלת הפניות לרכיבים בקובץ העיצוב
            ImageView imageview=vitem.findViewById(R.id.imgvitem);

            TextView tvTsentence=vitem.findViewById(R.id.tvitemsentence);

            ImageView btnEdit=vitem.findViewById(R.id.imgBtnEdititm);

            ImageView btnDelete=vitem.findViewById(R.id.imgBtnDeleteitm);
            //קבלת הנתון (עצם) הנוכחי
            current=getItem(position);
            //הצגת הנתונים על שדות הרכיב הגרפי
            tvTsentence.setText(current.getText());

            downloadImageUsingPicasso(current.getImageHand(),imageview);

        } return vitem;


    }
    /**
     * הצגת תמונה ישירות מהענן בעזרת המחלקה ״פיקאסו״
     * @param imageUrL כתובת התמונה בענן/שרת
     * @param toView רכיב תמונה המיועד להצגת התמונה אחרי ההורדה
     */
    private void downloadImageUsingPicasso(String imageUrL, ImageView toView)
    {
        // אם אין תמונה= כתובת ריקה אז לא עושים כלום מפסיקים את הפעולה
        if(imageUrL==null) return;
        //todo: add dependency to module gradle:
        //    implementation 'com.squareup.picasso:picasso:2.5.2'
        Picasso.with(getContext())
                .load(imageUrL)//הורדת התמונה לפי כתובת
                .centerCrop()
                .error(R.drawable.ic_launcher_background)//התמונה שמוצגת אם יש בעיה בהורדת התמונה
                .resize(90,90)//שינוי גודל התמונה
                .into(toView);// להציג בריכיב התמונה המיועד לתמונה זו
    }
    /**
     * מחיקת פריט כולל התמונה מבסיס הנתונים
     * @param myrmoz הפריט שמוחקים
     */
    private void delMyrmozFromDB_FB(Rmoz myrmoz)
    {
        //הפנייה/כתובת  הפריט שרוצים למחוק
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("MyUsers").
                document(FirebaseAuth.getInstance().getUid()).
                collection("subjects").
                document(myrmoz.id).
                collection("rmoz").document(myrmoz.id).
                delete().//מאזין אם המחיקה בוצעה
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    remove(myrmoz);// מוחקים מהמתאם
                    deleteFile(myrmoz.getImageHand());// מחיקת הקובץ
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * מחיקת קובץ האיחסון הענן
     * @param fileURL כתובת הקובץ המיועד למחיקה
     */
    private void deleteFile(String fileURL) {
        // אם אין תמונה= כתובת ריקה אז לא עושים כלום מפסיקים את הפעולה
        if(fileURL==null){
            Toast.makeText(getContext(), "Theres no file to delete!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        // הפניה למיקום הקובץ באיחסון
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        //מחיקת הקובץ והוספת מאזין שבודק אם ההורדה הצליחה או לא
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "file deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "onFailure: did not delete file "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}




