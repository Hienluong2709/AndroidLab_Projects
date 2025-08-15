package com.example.lab19_parsexml_project;

import android.os.Bundle;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnParse;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> display = new ArrayList<>();

    // Lớp Employee tương tự như data class bên Kotlin
    public static class Employee {
        int id;
        String title;
        String name;
        String phone;

        public Employee() {
            this.id = 0;
            this.title = "";
            this.name = "";
            this.phone = "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnParse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, display);
        lv.setAdapter(adapter);

        btnParse.setOnClickListener(v -> {
            try {
                List<Employee> employees = parseEmployeesFromAssets("employee.xml");
                display.clear();
                for (int i = 0; i < employees.size(); i++) {
                    Employee e = employees.get(i);
                    display.add((i + 1) + "-" + e.title + "-" + e.name + "-" + e.phone);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Parse lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Employee> parseEmployeesFromAssets(String fileName) throws Exception {
        List<Employee> list = new ArrayList<>();
        InputStream input = getAssets().open(fileName);

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(input, "UTF-8");

            int event = parser.getEventType();
            Employee current = null;

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if ("employee".equals(parser.getName())) {
                            current = new Employee();
                            String idStr = parser.getAttributeValue(null, "id");
                            current.id = (idStr != null) ? Integer.parseInt(idStr) : 0;

                            String titleStr = parser.getAttributeValue(null, "title");
                            current.title = (titleStr != null) ? titleStr : "";
                        } else if ("name".equals(parser.getName())) {
                            if (current != null) {
                                current.name = parser.nextText();
                            }
                        } else if ("phone".equals(parser.getName())) {
                            if (current != null) {
                                current.phone = parser.nextText();
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("employee".equals(parser.getName()) && current != null) {
                            list.add(current);
                            current = null;
                        }
                        break;
                }
                event = parser.next();
            }
        } finally {
            input.close();
        }

        return list;
    }
}
