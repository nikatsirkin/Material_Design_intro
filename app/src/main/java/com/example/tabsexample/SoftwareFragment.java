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
import com.google.firebase.firestore.Query;


public class SoftwareFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    public SoftwareFragment() {
        // Required empty public constructor
    }

    FirestoreRecyclerAdapter adapter;

    public static SoftwareFragment newInstance() {
        SoftwareFragment fragment = new SoftwareFragment();
        Bundle args = new Bundle();
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build());
        Query query = FirebaseFirestore.getInstance().collection("BusInvite");
        FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                .setQuery(query, RequestModel.class).build();
        adapter = new FirestoreRecyclerAdapter<RequestModel, RequestViewHolder>(options) {

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_request,parent,false);
                return new RequestViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull RequestModel model) {
                holder.list_date.setText(""+model.getDate());
                holder.list_time.setText(""+model.getDate());
                holder.list_line.setText(""+ model.getLine());

            }

        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private class RequestViewHolder extends RecyclerView.ViewHolder {
        private TextView list_line;
        private TextView list_time;
        private TextView list_date;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            list_line = itemView.findViewById(R.id.tv_line);
            list_time = itemView.findViewById(R.id.tv_time);
            list_date = itemView.findViewById(R.id.tv_date);
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


