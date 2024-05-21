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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import aka.hanan.hananakawiapp.MainActivity;
import aka.hanan.hananakawiapp.R;

public class MyMessageAdapter  extends ArrayAdapter<Message> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
    /**
     * פעולה בונה מתאם
     * @param context מסך אקטיבי
     * @param resource עיצוב של פריט שיציג הנותנים של העצם
     */
    public MyMessageAdapter(@NonNull Context context, int resource) {
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
            TextView tvimagehand=vitem.findViewById(R.id.tvimagehand);
            TextView tvTsentence=vitem.findViewById(R.id.tvitemsentence);
            ImageButton btnSendSms=vitem.findViewById(R.id.imgBtnSendSmsitm);
            ImageButton btnEdit=vitem.findViewById(R.id.imgBtnEdititm);
            ImageButton btnCall=vitem.findViewById(R.id.imgBtnCallitm);
            ImageButton btnDelete=vitem.findViewById(R.id.imgBtnDeleteitm);
            //קבלת הנתון (עצם) הנוכחי
            Message current=getItem(position);
            //הצגת הנתונים על שדות הרכיב הגרפי
            tvTsentence.setText(current.getSentence());
            tvimagehand.setText(current.getImageHand());
        } return vitem;


    }

}




