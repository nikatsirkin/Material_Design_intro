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
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class AddIssueFragment extends Fragment {

    private EditText edittextdate, edittextline, editTexttime;
    private Button btnSend;
    private FirebaseFirestore db;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private final String DATE = "date";
    private final String BUSLINE = "line";
    private final String TIME = "time";
    public AddIssueFragment() {
        // Required empty public constructor
    }


    public static AddIssueFragment newInstance(String param1, String param2) {
        AddIssueFragment fragment = new AddIssueFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edittextline = getView().findViewById(R.id.editTextLine);
        edittextdate = getView().findViewById(R.id.editTextDate);
        editTexttime = getView().findViewById(R.id.editTextTime);

        db = FirebaseFirestore.getInstance();
        btnSend = getView().findViewById(R.id.button_send_issue);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String category = "Software";
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
