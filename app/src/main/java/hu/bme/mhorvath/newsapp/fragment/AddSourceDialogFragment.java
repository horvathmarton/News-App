package hu.bme.mhorvath.newsapp.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.mhorvath.newsapp.R;
import hu.bme.mhorvath.newsapp.interfaces.OnSourceChangedListener;

public class AddSourceDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = "AddSourceDialogFragment";

    @BindView(R.id.etNewSourceName)
    EditText etNewSourceName;
    private OnSourceChangedListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof OnSourceChangedListener) {
            listener = (OnSourceChangedListener) getActivity();
        } else {
            throw new RuntimeException("Incompatible interface!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.action_add_source)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sourceName = etNewSourceName.getText().toString();
                        listener.onSourceAdded(sourceName);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_source, null);
        ButterKnife.bind(this, view);
        return view;
    }
}
