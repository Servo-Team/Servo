package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.servo.auth.MainActivity;
import com.servo.auth.R;
import com.servo.database.Service;
import com.servo.database.ServiceDatabase;
import com.servo.home.HomeActivity;
import com.servo.utils.Constants;
import com.servo.utils.StringManupilation;

import java.util.ArrayList;
import java.util.List;


public class AddDialog {

    private Activity act;
    private View globalView;
    private Dialog dialog;
    private EditText edit;
    private EditText edit2;
    private Button submit;
    private List<Chip> chips = new ArrayList();
    private List<Boolean> chips_state = new ArrayList();

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            checkFields();
        }
    };

    public AddDialog(Activity act){
        this.act = act;
    }

    public void startDialog(){
        dialog = new Dialog(act, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflator = act.getLayoutInflater();
        globalView = inflator.inflate(R.layout.add_dialog, null);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(globalView);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

        edit = (EditText) globalView.findViewById(R.id.serviceTitle);
        edit2= (EditText) globalView.findViewById(R.id.serviceDescription);
        submit = (Button) globalView.findViewById(R.id.submitProductButton);

        submit.setEnabled(false);
        submit.setAlpha(.5F);
        addTextListeners();
        addChipListeners();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addService();

                    ((HomeActivity) act).main_dialog.startSuccessDialog("Successfully Added Service!");
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((HomeActivity) act).main_dialog.dismissDialog();
                            dialog.dismiss();
                        }
                    }, 2500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addService() throws Exception {
        Service service = new Service();
        service.setTitle(edit.getText().toString());
        service.setDescription(edit2.getText().toString());
        service.setAssignerID(((HomeActivity)act).USER_ID);
        service.setWorkerID(-1);
        service.setTags(StringManupilation.combine_chips(chips_state, chips.size()));
        service.setStateID(Constants.SERVICE_NOT_ASSIGNED);

        ServiceDatabase db = new ServiceDatabase();
        db.insertObj(service);
    }

    private void addTextListeners(){
        edit.addTextChangedListener(watcher);
        edit2.addTextChangedListener(watcher);
    }

    private void checkFields(){
        if(edit.getText().toString().isEmpty() || edit2.getText().toString().isEmpty()){
            submit.setEnabled(false);
            submit.setAlpha(.5F);
        } else{
            submit.setEnabled(true);
            submit.setAlpha(1.F);
        }
    }

    private void addChipListeners(){
        chips.add((Chip) globalView.findViewById(R.id.chip));
        chips.add((Chip) globalView.findViewById(R.id.chip2));
        chips.add((Chip) globalView.findViewById(R.id.chip3));
        chips.add((Chip) globalView.findViewById(R.id.chip4));
        chips.add((Chip) globalView.findViewById(R.id.chip5));
        chips.add((Chip) globalView.findViewById(R.id.chip6));

        for(int i=0; i<chips.size(); i++) chips_state.add(false);

        for(int i=0; i<chips.size(); i++){
            chips.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    switch(compoundButton.getId()){
                        case R.id.chip:
                            chips_state.set(0,b);
                            break;
                        case R.id.chip2:
                            chips_state.set(1,b);
                            break;
                        case R.id.chip3:
                            chips_state.set(2,b);
                            break;
                        case R.id.chip4:
                            chips_state.set(3,b);
                            break;
                        case R.id.chip5:
                            chips_state.set(4,b);
                            break;
                        case R.id.chip6:
                            chips_state.set(5,b);
                            break;
                    }
                }
            });
        }
    }


}
