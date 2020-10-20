package com.keiapp.cooffee;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.keiapp.cooffee.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    private FragmentRegisterBinding binding;
    private FirebaseAuth auth;
    private static final String TAG = "REG_FRAG_LOG";
    private Loader loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        auth = FirebaseAuth.getInstance();
        loader = new Loader(getActivity());

        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUser();
            }
        });

        //Go to login
        binding.regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.navigateToLogin);
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void registerUser() {

        loader.StartDialog("Authenticating new user...");

        String username = binding.regUsername.getText().toString();
        String password = binding.regPass.getText().toString();
        String confPassword = binding.regConfPass.getText().toString();
        String email = binding.regEmail.getText().toString();

        if (validateInput(username,password,confPassword,email)){

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loader.DismissDialog();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                updateUI();

                            } else {
                                loader.DismissDialog();
                                // If sign in fails, display a message to the user
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {
                                        binding.regPass.setError("Week password, password must contains more than 6 character");
                                        binding.regPass.requestFocus();
                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        binding.regEmail.setError("Invalid email address");
                                        binding.regEmail.requestFocus();
                                    } catch(FirebaseAuthUserCollisionException e) {
                                        binding.regEmail.setError("User with the email already exist");
                                        binding.regEmail.requestFocus();
                                    } catch(Exception e) {
                                        Log.e(TAG, e.getMessage());
                                    }

                            }

                        }
                    });
        }else{
            loader.DismissDialog();
        }

    }
    private void updateUI() {
        Intent intent = new Intent(getContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }
    private boolean validateInput(String username, String password, String confPassword, String email) {
        boolean isValid = true;
        String error;

        if (TextUtils.isEmpty(username)){
            error = "Username is empty";
            binding.regUsername.setError(error);
            isValid = false;
        }
        if(TextUtils.isEmpty(password)){
            error = "Password is empty";
            binding.regPass.setError(error);
            isValid = false;
        }

        if (TextUtils.isEmpty(confPassword)){
            error = "Please confirm your password";
            binding.regConfPass.setError(error);
            isValid = false;
        }

        if(TextUtils.isEmpty(email) && !isValidEmail(email)){
            error = "Invalid email";
            binding.regEmail.setError(error);
            isValid = false;
        }
        
        if (!password.equals(confPassword)){
            isValid = false;
            Toast.makeText(getContext(), "Confirmation password is not identical", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}