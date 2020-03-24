package fr.android.tennistracker.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import fr.android.tennistracker.R;

public class GameNotOverDialog extends DialogFragment {
    
    private GameNotOverDialogListener listener;
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_game_not_over_dialog,null);
        builder.setTitle(R.string.game_not_over_message);
        builder.setView(view);
        builder.setNegativeButton("CANCEL", (dialog, which) -> listener.closeDialog());
        return builder.create();
        
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (GameNotOverDialog.GameNotOverDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }
    
    
    public interface GameNotOverDialogListener {
        
        void closeDialog();
        void gameNotOverButtons(View view);
        
    }
}
