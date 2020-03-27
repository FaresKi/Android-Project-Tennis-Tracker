package fr.android.tennistracker.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.R;

public class DoneMatchDialog extends DialogFragment {
    private DoneMatchDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Player winner = listener.getWinner();
        builder.setTitle(winner.getName() + " won the game");
        builder.setNegativeButton("Ok", (dialog, which) -> {
            listener.redirectToStats();
        });
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DoneMatchDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface DoneMatchDialogListener {
        void redirectToStats();

        Player getWinner();
    }

}
