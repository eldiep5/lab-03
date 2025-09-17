package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private City edit_city; // Selected City to be editted if applicable

    AddCityFragment() {
        // Constructor for adding
        this.edit_city = null;
    }
    AddCityFragment(City city){
        // Constructor for editing
        this.edit_city = city;
    }
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity();
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        }
        else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (edit_city == null) {
            // If selected city is null - then that means the user is adding a city
            return builder
                    .setView(view)
                    .setTitle("Add a City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        listener.addCity(new City(cityName, provinceName));
                    })
                    .create();
        }
        else {
            // If selected city isn't null - then that means the user is editing a city
            editCityName.setText(edit_city.getName()); // Set editable City text to the previous city name
            editProvinceName.setText(edit_city.getProvince()); // Set editable Province text to the previous province name
            return builder
                    .setView(view)
                    .setTitle("Edit City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit", (dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        edit_city.setName(cityName);
                        edit_city.setProvince(provinceName);
                        listener.editCity();
                    })
                    .create();

        }


    }
}
