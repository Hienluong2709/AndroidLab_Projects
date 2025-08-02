package com.example.lab6_personalinfor_project;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtHoten, edtCMND, edtBoSung;
    CheckBox checkBox1, checkBox2, checkBox3;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;
    Button btnGuiTT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtHoten = findViewById(R.id.edtHoten);
        edtCMND = findViewById(R.id.edtCMND);
        edtBoSung = findViewById(R.id.edtBoSung);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        btnGuiTT = findViewById(R.id.btnGuiTT);

        radioButton3.setChecked(true);
        btnGuiTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtHoten.getText().toString().trim();
                String cmnd = edtCMND.getText().toString().trim();
                String thongTinBoSung = edtBoSung.getText().toString().trim();

                if (hoTen.length() < 3) {
                    edtHoten.requestFocus();
                    Toast.makeText(MainActivity.this, "Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cmnd.length() != 9) {
                    edtCMND.requestFocus();
                    Toast.makeText(MainActivity.this, "CMND phải đúng 9 ký tự", Toast.LENGTH_LONG).show();
                    return;
                }

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Phải chọn bằng cấp", Toast.LENGTH_LONG).show();
                    return;
                }

                String bangCap = ((RadioButton) findViewById(selectedId)).getText().toString();

                if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()) {
                    Toast.makeText(MainActivity.this, "Phải chọn ít nhất 1 sở thích", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuilder soThich = new StringBuilder();
                if (checkBox1.isChecked()) soThich.append("Đọc sách - ");
                if (checkBox2.isChecked()) soThich.append("Đọc báo - ");
                if (checkBox3.isChecked()) soThich.append("Đọc code - ");
                if (soThich.length() > 3) soThich.setLength(soThich.length() - 3);

                String msg = hoTen + "\n" +
                        cmnd + "\n" +
                        bangCap + "\n" +
                        soThich + "\n" +
                        "------------------------------\n" +
                        "Thông tin bổ sung:\n" +
                        thongTinBoSung + "\n" +
                        "------------------------------";

                SpannableString title = new SpannableString("Thông tin cá nhân");
                title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(title);
                builder.setMessage(msg);
                builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView textMessage = dialog.findViewById(android.R.id.message);
                if (textMessage != null) {
                    textMessage.setGravity(Gravity.START);
                    textMessage.setPadding(40, 20, 40, 20);
                }
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Question");
                b.setMessage("Are you sure you want to exit?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                b.create().show();
            }
        });
    }
}
