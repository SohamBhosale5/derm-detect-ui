package com.skin.skinapp2.ui.privacy;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.skin.skinapp2.R;

public class PrivacyFragment extends Fragment {

    private PrivacyViewModel mViewModel;
    View root;
    WebView webView;
    public static PrivacyFragment newInstance() {
        return new PrivacyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.privacy_fragment, container, false);
        webView = root.findViewById(R.id.privacy_view);
        webView.loadUrl("https://dermdetect.blogspot.com/2021/10/privacy-policy.html");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(PrivacyViewModel.class);
        // TODO: Use the ViewModel
    }

}