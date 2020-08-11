package com.example.acaciapasswords;


import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tooltip.Tooltip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoruneyTab extends Fragment {

    String dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    String[] dateItems = dateFormat.split("/");

    public JoruneyTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.journey_tab, container, false);

        final TextView textViewDay = view.findViewById(R.id.textViewDay);
        final TextView textViewMonth = view.findViewById(R.id.textViewMonth);
        final TextView textViewYear = view.findViewById(R.id.textViewYear);
        final TextView textViewPassword = view.findViewById(R.id.textViewPassword);
        final TextView textViewPasswordLabel = view.findViewById(R.id.textViewPasswordLabel);
        final EditText editTextId = view.findViewById(R.id.editTextId);
        Resources res = getResources();
        String day = res.getString(R.string.day, dateItems[0]);
        textViewDay.setText(day);
        String month = res.getString(R.string.month, dateItems[1]);
        textViewMonth.setText(month);
        String year = res.getString(R.string.year, dateItems[2]);
        textViewYear.setText(year);

        textViewPasswordLabel.setVisibility(View.INVISIBLE);

        Button buttonPassword = view.findViewById(R.id.buttonPassword);
        buttonPassword.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)
            {
                if (editTextId.getText().toString().isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                    dlg.setTitle("Erro");
                    dlg.setMessage("Informe o código.");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                    editTextId.setError("*");
                    editTextId.requestFocus();
                    return;
                }
                String dayString = dateItems[0];
                String monthString = dateItems[1];
                String codAfvString = editTextId.getText().toString();
                String yearString = dateItems[2];
                double day = Double.parseDouble(dayString);
                double month = Double.parseDouble(monthString);
                double codAfv = Double.parseDouble(codAfvString);
                double year = Double.parseDouble(yearString);
                double resultDouble = (day + month + codAfv) * year;
                int result = (int) resultDouble;
                textViewPasswordLabel.setVisibility(View.VISIBLE);
                textViewPassword.setText(String.valueOf(result));
                showTooltip(view, Gravity.TOP);
            }
        });

        textViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewPassword.getText() != "") {
                    ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(textViewPassword.getText());
                    Toast.makeText(view.getContext(), "Copiado para a área de transferência.", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void showTooltip(View v, int gravity) {
        TextView textViewPasswordAfv = (TextView) v.findViewById(R.id.textViewPassword);
        Tooltip tooltip = new Tooltip.Builder(textViewPasswordAfv)
                .setText("Clique sob a senha para copiá-la.")
                .setTextColor(Color.WHITE)
                .setGravity(gravity)
                .setCornerRadius(8f)
                .setDismissOnClick(true)
                .setCancelable(true)
                .show();
    }


}
