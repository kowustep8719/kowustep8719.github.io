// Stephen Owusu-Agyekum
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

public class EditDataFragment extends Fragment {

    private EditDataViewModel editDataViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Set View Variables
        editDataViewModel = new ViewModelProvider(this).get(EditDataViewModel.class);
        editDataViewModel.InitializeDataProvider(getContext());
        View root = inflater.inflate(R.layout.fragment_edit_data, container, false);

        String editId = EditDataFragmentArgs.fromBundle(getArguments()).getItemId();
        String editCount = EditDataFragmentArgs.fromBundle(getArguments()).getItemCount();

        EditText productCountEditText = root.findViewById(R.id.count);
        productCountEditText.setText(editCount);
        Button submitButton = root.findViewById(R.id.Add);

        // I will create the Hhandlers
        editDataViewModel.getEditDataFormState().observe(getViewLifecycleOwner(), new Observer<EditDataFormState>() {
            @Override
            public void onChanged(@Nullable EditDataFormState addDataFormState) {
                if (addDataFormState == null) {
                    return;
                }
                submitButton.setEnabled(addDataFormState.isDataValid());
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
                editDataViewModel.addDataDataChanged(productCountEditText.getText().toString());
            }
        };
        productCountEditText.addTextChangedListener(afterTextChangedListener);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // I will add the item to the database
                try {
                    editDataViewModel.UpdateRecordCount( editId, productCountEditText.getText().toString());
                } catch (IOException e) {
                    Snackbar.make(root, "Failed to update record", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                // display a message on the user interface showing success
                Snackbar.make(root, "Record Updated", Snackbar.LENGTH_LONG)
                        .show();
              
                getActivity().onBackPressed();
            }
        });

        return root;
    }

}
