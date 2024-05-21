package aka.hanan.hananakawiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import aka.hanan.hananakawiapp.data.Tables.MyMessageAdapter;

public class MainActivity extends AppCompatActivity {
    //spnr1 تعريف صفة للكائن المرئي
    private Text etText;
    private Button btnconvert;
    private ListView lstMessages;
    private MyMessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        lstMessages = findViewById(R.id.lst);//הפניה לרכיב הגרפי שמציג אוסף
        messageAdapter = new MyMessageAdapter(this, R.layout.text_item_layout);//בניית המתאם
        lstMessages.setAdapter(messageAdapter);//קישור המתאם אם המציג הגרפי לאוסף


    }

    @Override//بناء قائمة
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;

    }

    @Override//معالجة حدث اختيار عنصر من القائمة
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itmSetting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }
        if ((item.getItemId() == R.id.itmLogOut)) {
            showYesNoDialog();
        }
        if (item.getItemId() == R.id.itmAddSentence) {
            Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
            //* Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
            //* startActivity(i);

        }

        return true;
    }

    /**
     * دالة مساعدة لفتح قائمة تتلقى بارمتر للكائن الذي سبب فتح القائمة
     */
    public void showPopUpMenu(View v, Message m) {

        //بناء قائمة popup menu
        PopupMenu popup = new PopupMenu(this, v);//الكائن الذي سبب فتح القائمة v
        //ملف القائمة
        popup.inflate(R.menu.popup_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.itmEdit) {
                    Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.itmDelete) {
                    Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        popup.show();//فتح وعرض القائمة
    }

    public void showYesNoDialog() {
        //تجهيز بناء شباك حوار"ديالوغ" يتلقى بارمتر مؤشر للنشاط (الاكتيفيتي)الحالي
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");//تحديد العنوان
        builder.setMessage("Are you sure?");//تحدي فحوى شباك الحوار
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //معالجة حدث للموافقة
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        //الص على الزر ومعالجة الحدث
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //معالجة الحدث الموافقة
                Toast.makeText(MainActivity.this, "not Signing out", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog dialog = builder.create();//بناء شباك الحوار - ديالوغ
        dialog.show();//عرض الشباك
    }
}


