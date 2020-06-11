package com.example.tabsexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;


public class SoftwareFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    public SoftwareFragment() {
        // Required empty public constructor
    }

    FirestoreRecyclerAdapter adapter;

    public static SoftwareFragment newInstance(String param1, String param2) {
        SoftwareFragment fragment = new SoftwareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.list_software);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build());
        Query query = FirebaseFirestore.getInstance().collection("BusInvite");
        FirestoreRecyclerOptions<IssueModel> options = new FirestoreRecyclerOptions.Builder<IssueModel>()
                .setQuery(query, IssueModel.class).build();
        adapter = new FirestoreRecyclerAdapter<IssueModel, IssueViewHolder>(options) {

            @NonNull
            @Override
            public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_issue,parent,false);
                return new IssueViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull IssueViewHolder holder, int position, @NonNull IssueModel model) {
                holder.list_reporter.setText(model.getReporter());
                holder.list_title.setText(model.getTitle());
            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private class IssueViewHolder extends RecyclerView.ViewHolder {
        private TextView list_title;
        private TextView list_reporter;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            list_title = itemView.findViewById(R.id.tv_title);
            list_reporter = itemView.findViewById(R.id.tv_reporter);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_software, container, false);
    }


}


