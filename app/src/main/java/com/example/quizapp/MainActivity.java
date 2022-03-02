package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.quizapp.Utils.Global;
import com.example.quizapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    boolean isBack = false;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.questionFrag:
                        isBack = false;
                        break;
                    default:
                        isBack = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.InternetConnection.checkConnection(this)) {
            Global.DialogShow(this, getString(R.string.Interneton)).dismiss();
            // Its Available...
        } else {
            Global.DialogShow(this, getString(R.string.internetoff)).show();
            // Not Available...
        }
    }


    @Override
    public void onBackPressed() {
        if (!isBack) {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText(getString(R.string.notice))
                    .setCustomImage(R.drawable.ic_wrong)
                    .setContentText(getString(R.string.warring_msg))
                    .setConfirmText(getString(R.string.yes))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.action_questionFrag_to_homeFrag);
                            sDialog.dismissWithAnimation();

                        }
                    })
                    .setCancelButton(getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        } else {
            super.onBackPressed();

        }
    }

}