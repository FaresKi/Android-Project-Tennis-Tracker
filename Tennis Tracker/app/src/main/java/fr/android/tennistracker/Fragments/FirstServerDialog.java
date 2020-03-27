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

public class FirstServerDialog extends DialogFragment {
    private FirstServerDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        Button firstPlayer = view.findViewById(R.id.buttonFirstPlayerDialog);
        Button secondPlayer = view.findViewById(R.id.buttonSecondPlayerDialog);
        builder.setTitle(R.string.first_server);
        builder.setView(view);
        builder.setNegativeButton("cancel", (dialog, which) -> {
            listener.goBackToNewMatch();
        });
        listener.setPlayersName(view);
        firstPlayer.setOnClickListener(v -> listener.sendPlayerName(v));
        secondPlayer.setOnClickListener(v -> listener.sendPlayerName(v));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (FirstServerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface FirstServerDialogListener {
        void sendPlayerName(View view);

        void setPlayersName(View view);

        void goBackToNewMatch();
    }


}
