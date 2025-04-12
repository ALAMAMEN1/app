package com.example.studentexamaveragecalculator;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ModuleAdapter adapter;
    List<Module> moduleList = new ArrayList<>();
    TextView resultTextView;
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recyclerView);
        resultTextView = findViewById(R.id.resultTextView);
        calculateButton = findViewById(R.id.calculateButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModuleAdapter(moduleList);
        recyclerView.setAdapter(adapter);

        fetchModules();

        calculateButton.setOnClickListener(v -> calculateAverage());
    }

    private void fetchModules() {
        try {
            InputStream is = getResources().openRawResource(R.raw.modules);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            moduleList.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Module module = new Module();
                module.name = obj.getString("name");
                module.coefficient = obj.getInt("coefficient");
                moduleList.add(module);
            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            loadSampleData();
        }
    }

    private void loadSampleData() {
        moduleList.add(new Module("Mathe", 3));
        moduleList.add(new Module("physics", 2));
        //moduleList.add(new Module("Eng", 1));

        adapter.notifyDataSetChanged();
    }

    private void calculateAverage() {
        double total = 0, sumCoeff = 0;

        for (Module module : moduleList) {
            double avg = module.getModuleAverage();
            total += avg * module.coefficient;
            sumCoeff += module.coefficient;
        }

        if (sumCoeff == 0) {
            resultTextView.setText("No units available!");
            return;
        }

        double weightedAvg = total / sumCoeff;
        String result = String.format("Result is : %.2f\n%s",
                weightedAvg,
                (weightedAvg >= 10 ? "successful" : "Failed"));
        resultTextView.setText(result);
    }
}