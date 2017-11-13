package com.minecraft.packer.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.minecraft.packer.R;

/**
 * Created by user on 10/11/2017.
 */

public class CustomDialog {

   public Dialog dl;
    Boolean tempo=false;
   public Button bt_yes;
    public void ShowDialog(final Context context) {
        dl = new Dialog(context);
        dl.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl.setContentView(R.layout.custom_dialog);
        dl.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

        TextView tv_message = (TextView) dl.findViewById(R.id.textViewMessagelast);
        tv_message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


         bt_yes = (Button)dl.findViewById(R.id.buttonyes);

        Button bt_no = (Button)dl.findViewById(R.id.buttonnolast);
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.dismiss();
            }

        });
        dl.show();


    }
    public boolean showYes(){
        return tempo;
    }

}
