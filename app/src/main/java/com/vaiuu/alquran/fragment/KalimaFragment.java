package com.vaiuu.alquran.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vaiuu.alquran.main.KalimaDetailActivity;
import com.vaiuu.alquran.main.R;


public class KalimaFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private Context context;
    private RelativeLayout txt_kalima1;
    private RelativeLayout txt_kalima2;
    private RelativeLayout txt_kalima3;
    private RelativeLayout txt_kalima4;
    private RelativeLayout txt_kalima5;

    public static KalimaFragment newInstance(int position) {
        KalimaFragment f = new KalimaFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.kalima_fragment, container, false);

        context = getActivity();
        initUI(rootView);


        return rootView;
    }

    private void initUI(View view) {
        txt_kalima1=(RelativeLayout)view.findViewById(R.id.txt_kalima1);
        txt_kalima2=(RelativeLayout)view.findViewById(R.id.txt_kalima2);
        txt_kalima3=(RelativeLayout)view.findViewById(R.id.txt_kalima3);
        txt_kalima4=(RelativeLayout)view.findViewById(R.id.txt_kalima4);
        txt_kalima5=(RelativeLayout)view.findViewById(R.id.txt_kalima5);
        txt_kalima1.setOnClickListener(clickListener);
        txt_kalima2.setOnClickListener(clickListener);
        txt_kalima3.setOnClickListener(clickListener);
        txt_kalima4.setOnClickListener(clickListener);
        txt_kalima5.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_kalima1:
                    Intent intent=new Intent(getActivity(), KalimaDetailActivity.class);
                    intent.putExtra("title","kalima1");
                    startActivity(intent);
                    break;
                case R.id.txt_kalima2:
                    Intent intent2=new Intent(getActivity(), KalimaDetailActivity.class);
                    intent2.putExtra("title","kalima2");
                    startActivity(intent2);
                    break;
                case R.id.txt_kalima3:
                    Intent intent3=new Intent(getActivity(), KalimaDetailActivity.class);
                    intent3.putExtra("title","kalima3");
                    startActivity(intent3);
                    break;
                case R.id.txt_kalima4:
                    Intent intent4=new Intent(getActivity(), KalimaDetailActivity.class);
                    intent4.putExtra("title","kalima4");
                    startActivity(intent4);
                    break;
                case R.id.txt_kalima5:
                    Intent intent5=new Intent(getActivity(), KalimaDetailActivity.class);
                    intent5.putExtra("title","kalima5");
                    startActivity(intent5);
                    break;

            }
        }
    };



}