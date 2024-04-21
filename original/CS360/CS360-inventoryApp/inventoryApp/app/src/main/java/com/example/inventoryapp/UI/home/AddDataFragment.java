package com.example.inventoryApp.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.inventoryApp.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class AddDataFragment extends Fragment {

    private AddDataViewModel addDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //we will set view Variables here
        addDataViewModel = new ViewModelProvider(this).get(AddDataViewModel.class);
        addDataViewModel.InitializeDataProvider(getContext());
        View root = inflater.inflate(R.layout.fragment_add_data, container, false);

      //We will set variable control parameters
        EditText productNameEditText = root.findViewById(R.id.name);
        EditText productTypeEditText = root.findViewById(R.id.type);
        EditText productCountEditText = root.findViewById(R.id.count);
        Button submitButton = root.findViewById(R.id.Add);

        
        addDataViewModel.getAddDataFormState().observe(getViewLifecycleOwner(), new Observer<AddDataFormState>() {
            @Override
            public void onChanged(@Nullable AddDataFormState addDataFormState) {
                if (addDataFormState == null) {
                    return;
                }
                submitButton.setEnabled(addDataFormState.isDataValid());
                if (addDataFormState.getNameError() != null) {
                    productNameEditText.setError(getString(addDataFormState.getNameError()));
                }
                if (addDataFormState.getTypeError() != null) {
                    productTypeEditText.setError(getString(addDataFormState.getTypeError()));
                }
                if (addDataFormState.getCountError() != null) {
                    productCountEditText.setError(getString(addDataFormState.getCountError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              
            }

            @Override
            public void afterTextChanged(Editable s) {
                addDataViewModel.addDataDataChanged(productNameEditText.getText().toString(),
                        productTypeEditText.getText().toString(), productCountEditText.getText().toString());
            }
        };
        productNameEditText.addTextChangedListener(afterTextChangedListener);
        productTypeEditText.addTextChangedListener(afterTextChangedListener);
        productCountEditText.addTextChangedListener(afterTextChangedListener);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the item to the database
                try {
                    addDataViewModel.AddRecord(productNameEditText.getText().toString(),
                            productTypeEditText.getText().toString(), productCountEditText.getText().toString());
                } catch (IOException e) {
                    Snackbar.make(root, "Failed to add record", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                // Shoot a UI message indicating success
                Snackbar.make(root, "Record Added", Snackbar.LENGTH_LONG)
                        .show();
                // Simulate Back
                getActivity().onBackPressed();
            }
        });

        return root;
    }

}
