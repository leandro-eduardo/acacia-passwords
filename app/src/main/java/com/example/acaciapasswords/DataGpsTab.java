package com.example.acaciapasswords;


import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tooltip.Tooltip;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataGpsTab extends Fragment {

    String dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    String[] dateItems = dateFormat.split("/");

    public DataGpsTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.datagps_tab, container, false);

        final LinearLayout linearLayoutDataGps = view.findViewById(R.id.linearLayoutDataGps);

        final TextView textViewDay = view.findViewById(R.id.textViewDay);
        final TextView textViewMonth = view.findViewById(R.id.textViewMonth);
        final TextView textViewYear = view.findViewById(R.id.textViewYear);
        final TextView textViewPassword = view.findViewById(R.id.textViewPassword2);
        final TextView textViewPasswordLabel2 = view.findViewById(R.id.textViewPasswordLabel2);
        final EditText editTextId = view.findViewById(R.id.editTextId2);
        final EditText editTextChecker = view.findViewById(R.id.editTextChecker);

        final LinearLayout linearLayoutChecker = view.findViewById(R.id.linearLayoutChecker);
        final LinearLayout linearLayoutCod = view.findViewById(R.id.linearLayoutCod);
        final LinearLayout linearLayoutPassword = view.findViewById(R.id.linearLayoutPassword);
        final Button buttonPassword = view.findViewById(R.id.buttonPassword2);

        Resources res = getResources();
        String day = res.getString(R.string.day, dateItems[0]);
        textViewDay.setText(day);
        String month = res.getString(R.string.month, dateItems[1]);
        textViewMonth.setText(month);
        String year = res.getString(R.string.year, dateItems[2]);
        textViewYear.setText(year);

        textViewPasswordLabel2.setVisibility(View.INVISIBLE);

        final Spinner spinner = view.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.options_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        textViewPasswordLabel2.setVisibility(View.INVISIBLE);
                        buttonPassword.setVisibility(View.VISIBLE);
                        textViewPassword.setText("");
                        if ((linearLayoutCod.getParent() != null)) {
                            ((ViewGroup) linearLayoutCod.getParent()).removeView(linearLayoutCod);
                        }
                        if ((linearLayoutChecker.getParent() != null)) {
                            ((ViewGroup) linearLayoutChecker.getParent()).removeView(linearLayoutChecker);
                        }
                        if ((linearLayoutPassword.getParent() != null)) {
                            ((ViewGroup) linearLayoutPassword.getParent()).removeView(linearLayoutPassword);
                        }
                        if ((buttonPassword.getParent() != null)) {
                            ((ViewGroup) buttonPassword.getParent()).removeView(buttonPassword);
                        }
                        linearLayoutDataGps.addView(linearLayoutCod);
                        linearLayoutDataGps.addView(linearLayoutChecker);
                        linearLayoutDataGps.addView(linearLayoutPassword);
                        linearLayoutDataGps.addView(buttonPassword);
                        break;
                    case 1:
                        textViewPasswordLabel2.setVisibility(View.VISIBLE);
                        buttonPassword.setVisibility(View.INVISIBLE);
                        textViewPassword.setText("");
                        linearLayoutDataGps.removeView(linearLayoutCod);
                        linearLayoutDataGps.removeView(linearLayoutChecker);
                        linearLayoutDataGps.removeView(linearLayoutChecker);
                        String dayString = dateItems[0];
                        String monthString = dateItems[1];
                        String yearString = dateItems[2];
                        double day = Double.parseDouble(dayString);
                        double month = Double.parseDouble(monthString);
                        double year = Double.parseDouble(yearString);
                        double resultDouble = (day + month) * year;
                        int result = (int) resultDouble;
                        textViewPassword.setText(String.valueOf(result));
                        showTooltip(view, Gravity.TOP);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPassword.setText("");
                if (editTextId.getText().toString().isEmpty() && (editTextChecker.getText().toString().isEmpty())) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                    dlg.setTitle("Erro");
                    dlg.setMessage("Informe código e o verificador.");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                    editTextId.setError("*");
                    editTextChecker.setError("*");
                    editTextId.requestFocus();
                } else if (editTextId.getText().toString().isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                    dlg.setTitle("Erro");
                    dlg.setMessage("Informe o código.");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                    editTextId.setError("*");
                    editTextId.requestFocus();
                } else if (editTextChecker.getText().toString().isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                    dlg.setTitle("Erro");
                    dlg.setMessage("Informe o verificador.");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                    editTextChecker.setError("*");
                    editTextChecker.requestFocus();
                } else if (spinner.getSelectedItemId() == 0) {
                    String monthString = dateItems[1];
                    String codString = editTextId.getText().toString();
                    String codCheck = editTextChecker.getText().toString();
                    String yearString = dateItems[2];
                    double month = Double.parseDouble(monthString);
                    double cod = Double.parseDouble(codString);
                    double codChecker = Double.parseDouble(codCheck);
                    double year = Double.parseDouble(yearString);
                    double resultDouble = (cod + codChecker - month) * year;
                    int result = (int) resultDouble;
                    textViewPasswordLabel2.setVisibility(View.VISIBLE);
                    textViewPassword.setText(String.valueOf(result));
                    showTooltip(view, Gravity.TOP);
                }
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
        TextView textViewPasswordAfv = v.findViewById(R.id.textViewPassword2);
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
