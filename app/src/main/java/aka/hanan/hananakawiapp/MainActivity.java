package aka.hanan.hananakawiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
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
            }

