package com.example.tabsexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIssueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIssueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;

    private EditText edittextdate, edittextline, editTexttime;
    private Button btnSend;
    private FirebaseFirestore db;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private final String DATE = "Date";
    private final String BUSLINE = "line";
    private final String TIME = "Time";
    public AddIssueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIssueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIssueFragment newInstance(String param1, String param2) {
        AddIssueFragment fragment = new AddIssueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner = getView().findViewById(R.id.spinner_category);
        edittextline = getView().findViewById(R.id.editTextLine);
        edittextdate = getView().findViewById(R.id.editTextDate);
        editTexttime = getView().findViewById(R.id.editTextTime);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        btnSend = getView().findViewById(R.id.button_send_issue);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String category = spinner.getSelectedItem().toString();
                String date = edittextdate.getText().toString();
                String time = editTexttime.getText().toString();
                 int line= Integer.parseInt(edittextline.getText().toString());
                //IssueModel issueModel = new IssueModel(reporter,title,category);
                Map<String, Object> issue = new HashMap<>();
                issue.put(DATE, date);
                issue.put(TIME, time);
                issue.put(BUSLINE, line);
                db.collection("BusInvite").document().set(issue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                fm = getActivity().getSupportFragmentManager();
                                ft = fm.beginTransaction();
                                Fragment frag;
                                switch (category) {
                                    case "Software":
                                        frag = new SoftwareFragment();
                                        break;
                                    case "Hardware":
                                        frag = new HardwareFragment();
                                        break;
                                    default:
                                        frag = new SoftwareFragment();
                                }

                                ft.replace(R.id.frag_container, frag);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error see logs", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", e.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_issue, container, false);
    }
}
