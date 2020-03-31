package fr.android.tennistracker.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import fr.android.tennistracker.R;

public class UpdateServerDialog extends DialogFragment {
    
    private UpdateServerListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_update_server,null);
        Button startOver = view.findViewById(R.id.buttonStartOver);
        Button grantToFP = view.findViewById(R.id.buttonGrantToFP);
        Button grantToSP = view.findViewById(R.id.buttonGrantToSP);
        
        startOver.setOnClickListener(v -> listener.onUpdateServerButtonClick(v));
        grantToFP.setOnClickListener(v -> listener.onUpdateServerButtonClick(v));
        grantToSP.setOnClickListener(v -> listener.onUpdateServerButtonClick(v));
        
        builder.setTitle(R.string.update_server_text);
        builder.setView(view);
        
        return builder.create();
        
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (UpdateServerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }
    
    
    public interface UpdateServerListener{
        
        void onUpdateServerButtonClick(View view);
    }
}
