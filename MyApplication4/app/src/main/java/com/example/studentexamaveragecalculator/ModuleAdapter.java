package com.example.studentexamaveragecalculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {

    private List<Module> modules;

    public ModuleAdapter(List<Module> modules) {
        this.modules = modules;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = modules.get(position);
        holder.moduleName.setText(module.name);
        holder.tdInput.setText(module.td == 0 ? "" : String.valueOf(module.td));
        holder.tpInput.setText(module.tp == 0 ? "" : String.valueOf(module.tp));
        holder.examInput.setText(module.exam == 0 ? "" : String.valueOf(module.exam));

        holder.tdInput.addTextChangedListener(new SimpleTextWatcher(s -> {
            module.td = parse(s, holder.itemView);
        }));
        holder.tpInput.addTextChangedListener(new SimpleTextWatcher(s -> {
            module.tp = parse(s, holder.itemView);
        }));
        holder.examInput.addTextChangedListener(new SimpleTextWatcher(s -> {
            module.exam = parse(s, holder.itemView);
        }));
    }

    private double parse(CharSequence s, View itemView) {
        try {
            return Double.parseDouble(s.toString());
        } catch (Exception e) {
            Toast.makeText(itemView.getContext(), "Enter a double number", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleName;
        EditText tdInput, tpInput, examInput;

        ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleName);
            tdInput = itemView.findViewById(R.id.tdInput);
            tpInput = itemView.findViewById(R.id.tpInput);
            examInput = itemView.findViewById(R.id.examInput);
        }
    }

    class SimpleTextWatcher implements TextWatcher {
        private final OnTextChanged onTextChanged;

        SimpleTextWatcher(OnTextChanged onTextChanged) {
            this.onTextChanged = onTextChanged;
        }

        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged.update(s);
        }
    }

    interface OnTextChanged {
        void update(CharSequence s);
    }
}