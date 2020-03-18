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

public class LeaveRecordingDialog extends DialogFragment {
    private  LeaveRecordingDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_leaving_recording, null);
        builder.setView(view);
        builder.setNegativeButton("NO", (dialog, which) -> {
            listener.stayInThisActivity();
        });
        builder.setPositiveButton("YES", (dialog, which) -> {
            listener.goToHomeActivity();
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        try {
            listener = (LeaveRecordingDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LeavingRecordingDialogListener");
        }
        super.onAttach(context);
    }

    public interface LeaveRecordingDialogListener{
        void stayInThisActivity();
        void goToHomeActivity();
    }
}
