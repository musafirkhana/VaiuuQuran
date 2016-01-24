package com.vaiuu.alquran.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vaiuu.alquran.adapter.NameAdapter;
import com.vaiuu.alquran.main.R;


public class AllahNameFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private Context context;
    private ListView name_listvie;
    public String namearabic[];
    public String namebangle[];
    public String nameeng[];

    public static AllahNameFragment newInstance(int position) {
        AllahNameFragment f = new AllahNameFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.name_mainfragment, container, false);

        context = getActivity();
        initUI(rootView);


        return rootView;
    }

    private void initUI(View view) {
        namearabic = context.getResources().getStringArray(R.array.allah_name_arb);
        namebangle = context.getResources().getStringArray(R.array.allah_name_bng);
        nameeng = context.getResources().getStringArray(R.array.allah_name_eng);


        Log.i("Size of tion", "" + namearabic.length);
        name_listvie = (ListView) view.findViewById(R.id.name_listvie);
        NameAdapter nameAdapter=new NameAdapter(getActivity(),namearabic,namebangle,nameeng);
        name_listvie.setAdapter(nameAdapter);
        name_listvie.deferNotifyDataSetChanged();

    }





}