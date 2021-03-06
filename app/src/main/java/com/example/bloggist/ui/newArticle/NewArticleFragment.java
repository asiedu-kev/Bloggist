package com.example.bloggist.ui.newArticle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bloggist.HomeActivity;
import com.example.bloggist.R;
import com.example.bloggist.SplashActivity;
import com.example.bloggist.ui.login.LoginActivity;

public class NewArticleFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new, container, false);
        TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Vous n'êtes pas connecté actuellement.");
        Button login = root.findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
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