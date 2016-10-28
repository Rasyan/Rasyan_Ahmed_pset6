package rasyan_native_app.rasyan_ahmed_pset6.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import rasyan_native_app.rasyan_ahmed_pset6.R;
import rasyan_native_app.rasyan_ahmed_pset6.other.Apigetter;


/**
 * This fragment shows a dialog when called.
 * the only instence that this fragments gets used is when an user has pressed the floating action bar in the search fragment
 * the user then gets to see a pop up dialog where he or she can enter her search query.
 */
public class PopupFragment extends android.app.DialogFragment {

    private static final String TITLE = "Enter Recepy or ingredient name";
    public PopupFragment() {
        // required empty constructor
    }

    // makes a new instance of the class, stores the two booleans provided (which specify the type of dialog)
    // as arguments.
    public static PopupFragment newInstance() {
        PopupFragment fragment = new PopupFragment();
        return fragment;
    }

    // initilizes some internal variables
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // the main method which builds the dialog and sets the listeners
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // constructs the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(TITLE);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // adds the EditText inside fragment_input.xml
        builder.setView(inflater.inflate(R.layout.fragment_input, null));


        // adds the enter button and sets a listener
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            // get the entered text
            EditText edit = (EditText) getDialog().findViewById(R.id.edit);
            String inputText = String.valueOf(edit.getText());

            // if the entered text is not nothing
            if (!inputText.equals("")) {

                // search in the api for the inputted text
                Apigetter getter = new Apigetter(getActivity()) ;
                getter.execute("search",inputText);
                SearchFragment.searching = true; // tell Search fragment that it is searching
            } else {
                Toast.makeText(getActivity(), "No text entered", Toast.LENGTH_SHORT).show();
            }
            }
        })

        // setup the cancel button and the listener
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog, dialog gets removed automaticly
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }
}

