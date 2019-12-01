package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jetpack.data_binding.DataBindingActivity;
import com.example.jetpack.navigation.ShellActivity;

public class MainActivity extends AppCompatActivity {

    private IntentClassWithButton intent ;

    private LinearLayout llRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llRoot = findViewById(R.id.ll_root);
        intent = new IntentClassWithButton(llRoot);

        intent.intentTo(DataBindingActivity.class);
        intent.intentTo(ShellActivity.class);
    }

    static class IntentClassWithButton implements IIntent, View.OnClickListener {
        Context context;

        ViewGroup rootView;

        private static final int BUTTON_MARGIN_TOP_DP = 12;

        private static final int BUTTON_MARGIN_EDGE_DP = 18;

        IntentClassWithButton(ViewGroup rootView) {
            this.context = rootView.getContext();
            this.rootView = rootView;
        }

        @Override
        public void intentTo(Class<? extends Activity> activity) {
            Button button = getButton();
            initLayoutParams(button);
            initClickListener(button);
            button.setText(activity.getSimpleName());
            button.setTag(activity);
            button.setAllCaps(false);

            addView(button);
        }

        @Override
        public void onClick(View view) {
            Class tag = (Class) view.getTag();
            tag.asSubclass(Activity.class);
            intentToOne(tag);
        }


        private Button getButton() {
            return new Button(context);
        }

        private void initLayoutParams(View view) {
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            float density = context.getResources().getDisplayMetrics().density;
            int marginTop = (int) (density * BUTTON_MARGIN_TOP_DP);
            int marginEdge = (int) (density * BUTTON_MARGIN_EDGE_DP);

            params.topMargin = marginTop;
            params.leftMargin = marginEdge;
            params.rightMargin = marginEdge;
            view.setLayoutParams(params);
        }

        private void initClickListener(View view) {
            view.setOnClickListener(this);
        }

        private void addView(View view) {
            rootView.addView(view);
        }

        private void intentToOne(Class activity) {
            Intent intent = new Intent(context, activity);
            context.startActivity(intent);
        }
    }

    public interface IIntent {
        void intentTo(Class<? extends Activity> activity);
    }
}


