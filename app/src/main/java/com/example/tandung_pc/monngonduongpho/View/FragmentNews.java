package com.example.tandung_pc.monngonduongpho.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tandung_pc.monngonduongpho.R;
import com.example.tandung_pc.monngonduongpho.until.Server;

public class FragmentNews extends Fragment {
    String urlGetdata = Server.DuongdanGetAllCuaHang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        View hView = MainActivity.navigationView.getHeaderView(0);
        final TextView txtName = hView.findViewById(R.id.txtName);
        FloatingActionButton mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtName.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Bạn phải đăng nhập !", Toast.LENGTH_SHORT).show();
                } else {
                    enterDiaglog();
                }
            }
        });

        return view;
    }

    private void enterDiaglog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_them, null);
        final EditText edtTench = mView.findViewById(R.id.edtTench);
        final EditText edtsdt = mView.findViewById(R.id.edtSdt);
        final EditText edtDiachi = mView.findViewById(R.id.edttDiachi);
        Button btnXacnhan = mView.findViewById(R.id.btnThem);
        Button btnTrove = mView.findViewById(R.id.btnTrove);
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = edtTench.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String diachi = edtDiachi.getText().toString().trim();
                if (name.equals("") || sdt.equals("") || diachi.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    final View mView = getLayoutInflater().inflate(R.layout.activity_them_next, null);
                    ImageView img_bg = mView.findViewById(R.id.img_bg);
                    EditText ed_tinh = mView.findViewById(R.id.ed_tinh);
                    EditText ed_quan = mView.findViewById(R.id.ed_quan);
                    EditText ed_dc = mView.findViewById(R.id.ed_dc);
                    Button btn_send = mView.findViewById(R.id.btn_send);
                    Button btn_out_next = mView.findViewById(R.id.btn_out_next);
                    //click image
                    img_bg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            choooseImage();
                        }
                    });
                    //send
                    btn_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    builder.setView(mView);
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    ////out
                    btn_out_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void choooseImage() {

    }

}
