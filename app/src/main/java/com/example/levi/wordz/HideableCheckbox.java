package com.example.levi.wordz;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HideableCheckbox extends RelativeLayout {
    CheckBox checkBox;
    TextView textView;
    View     rootView;

    public HideableCheckbox(Context context){
        super(context);
        init(context);
    }

    public HideableCheckbox(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        rootView = inflate(context, R.layout.hideable_checkbox, this);
        textView = rootView.findViewById(R.id.textView);
        checkBox = rootView.findViewById(R.id.checkBox);

        checkBox.setVisibility(INVISIBLE);
    }

    public void setText(String s){
        textView.setText(s);
    }

    public void setText(SpannableString s){
        textView.setText(s);
    }

    public String getText(){
        return textView.getText().toString();
    }

    public boolean isChecked(){
        return checkBox.isChecked();
    }

    public void showCheckBox(){
        checkBox.setVisibility(VISIBLE);
        LayoutParams offset = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        offset.addRule(RelativeLayout.RIGHT_OF, R.id.checkBox);
        textView.setLayoutParams(offset);
    }

    public void hideCheckBox(){
        checkBox.setVisibility(INVISIBLE);
        LayoutParams offset = (RelativeLayout.LayoutParams) rootView.findViewById(R.id.container).getLayoutParams();
        offset.removeRule(RelativeLayout.RIGHT_OF);
        textView.setLayoutParams(offset);
    }
}
