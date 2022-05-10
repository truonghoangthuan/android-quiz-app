package com.example.quizme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quizme.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseFirestore database;
    FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        DocumentReference userInfo = database.collection("users").document(currentUser.getUid());
        userInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                binding.nameBox.setHint(user.getName());
                binding.emailBox.setHint(user.getEmail());
                binding.passBox.setHint("Enter your new password");
            }
        });


        binding.updateBtn.setOnClickListener(view -> {
            String name = binding.nameBox.getText().toString();
            String email = binding.emailBox.getText().toString();
            String pass = binding.passBox.getText().toString();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("name", name);
            updateFields.put("pass", pass);
            userInfo.update(updateFields);
            currentUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return binding.getRoot();
    }
}