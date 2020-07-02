package com.example.tabsexample;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Map;



public class AddIssueFragment extends Fragment {

    private EditText edittextline;
    private Button btnSend,btnChooseDate, btnChooseTime;
    private TextView tv_result;
    private FirebaseFirestore db;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private final String DATE = "date";
    private final String TIME = "time";
    private final String BUSLINE = "line";
    private Calendar calendar;
    //private  SimpleDateFormat format;
    String date,time;

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public AddIssueFragment() {
        // Required empty public constructor
    }


    public Date getDateFromString(String datetoSaved){

        try {
            Date date = format.parse(datetoSaved);
            return date ;
        } catch (ParseException e){
            return null ;
        }

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
        btnChooseDate = getView().findViewById(R.id.btn_choose_date);
        btnChooseTime = getView().findViewById(R.id.btn_choose_time);
        tv_result = getView().findViewById(R.id.tv_result);
        db = FirebaseFirestore.getInstance();
        btnSend = getView().findViewById(R.id.button_send_issue);
        calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);
        //CalendarConstraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(new DateValidatorWeekdays());

        //MaterialDatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String category = "Software";
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
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        date = materialDatePicker.getHeaderText();
                        tv_result.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });
        btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int HOUR = calendar.get(Calendar.HOUR);
                int MINUTE = calendar.get(Calendar.MINUTE);
                boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());


                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Log.i("TAG", "onTimeSet: " + hour + minute);
                        calendar.set(Calendar.HOUR, hour);
                        calendar.set(Calendar.MINUTE, minute);
                       time = DateFormat.format("HH:mm", calendar).toString();

                    }
                }, HOUR, MINUTE, is24HourFormat);

                timePickerDialog.show();
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
