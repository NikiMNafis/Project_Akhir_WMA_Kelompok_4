package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    private SharedPreferences sharedPreferences;

    private static final String USER_DATA = "userdata";
    private static final String NAMA_USER = "nama";
    private static final String NOTELP_USER = "notelp";
    private static final String EMAIL_USER = "email";

    TextView txtNamaUser, txtEmailUser;

    private AppCompatButton btnViewAkun, btnChangePass, btnMediaSosial, btnAbout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnViewAkun = view.findViewById(R.id.btn_view_akun);
        btnViewAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        btnChangePass = view.findViewById(R.id.btn_change_pass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnMediaSosial = view.findViewById(R.id.btn_media_sosial);
        btnMediaSosial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MediaSosialActivity.class);
                startActivity(intent);
            }
        });

        btnAbout = view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        txtNamaUser = view.findViewById(R.id.txt_nama_user);
        txtEmailUser = view.findViewById(R.id.txt_email_user);

        // Set profile menggunakan share preference
//        sharedPreferences = this.getActivity().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
//
//        String namaUser = sharedPreferences.getString(NAMA_USER, null);
//        String emailUser = sharedPreferences.getString(EMAIL_USER, null);
//
//        if (namaUser != null || emailUser != null) {
//            txtNamaUser.setText(namaUser);
//            txtEmailUser.setText(emailUser);
//        }
        // Set profile menggunakan firebase auth
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserRegister userProfile = snapshot.getValue(UserRegister.class);

                if (userProfile != null) {
                    String namaUser = userProfile.nama;
                    String noTelpUser = userProfile.noTelp;
                    String emailUser = userProfile.email;

                    txtNamaUser.setText(namaUser);
                    txtEmailUser.setText(emailUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}