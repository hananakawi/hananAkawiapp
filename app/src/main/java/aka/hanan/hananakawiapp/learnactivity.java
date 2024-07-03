package aka.hanan.hananakawiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import aka.hanan.hananakawiapp.data.Tables.AdapterRmoz;
import aka.hanan.hananakawiapp.data.Tables.Rmoz;


public class learnactivity extends AppCompatActivity {

    private TextInputEditText tivText;
    private ListView listView;
    private ImageButton btnTrans;
    AdapterRmoz romozAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnactivity);
        listView=findViewById(R.id.listView);
      //  btnTrans=findViewById(R.id.btnTrans)
        tivText=findViewById(R.id.tivText);
        romozAdapter = new AdapterRmoz(this, R.layout.text_item_layout);//בניית המתאם
        listView.setAdapter(romozAdapter);
        tivText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                readRMZFrom_FB(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    public void readRMZFrom_FB(String s)
    {
        //בניית רשימה ריקה
        ArrayList<Rmoz> arrayList =new ArrayList<>();
        HashMap<String,Rmoz> word=new HashMap<>();
        //קבלת הפנייה למסד הנתונים
        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
        //קישור לקבוצה collection שרוצים לקרוא

        //הוספת מאזין לקריאת הנתונים
        ffRef.collection("MyRmoz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            /**
             * תגובה לאירוע השלמת קריאת הנתונים
             * @param task הנתונים שהתקבלו מענן מסד הנתונים
             */
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {// אם בקשת הנתונים התקבלה בהצלחה
                    //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
                        Rmoz rmz = document.toObject(Rmoz.class);

                        word.put(rmz.getText(),rmz);
                    }
                    for (int i = 0; i <s.length() ; i++) {
                        Rmoz rmoz = word.get(s.charAt(i) + "");
                        arrayList.add(rmoz);
                    }

                    romozAdapter.clear();//ניקוי המתאם מכל הנתונים
                    romozAdapter.addAll(arrayList);
                    // messageAdapter.addAll(arrayList);//הוספת כל הנתונים למתאם
                }
                else{
                }
            }
        });
    }
}