package com.example.bloggist.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.bloggist.R;
import com.example.bloggist.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {
    Button login ;
    View user;


    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textView = root.findViewById(R.id.text_home);

       textView.setText("Vous n'êtes pas connecté actuellement");
        login = (Button) root.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);

                // close this activity
                getActivity().finish();
            }
        });
        return root;
    }

}
