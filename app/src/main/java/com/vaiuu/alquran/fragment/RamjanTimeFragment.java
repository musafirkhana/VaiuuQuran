package com.vaiuu.alquran.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vaiuu.alquran.adapter.RamdanCalenderAdapter;
import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.util.Appconstant;


public class RamjanTimeFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private Context context;
    private ListView list_ramdancalender;




    public static RamjanTimeFragment newInstance(int position) {
        RamjanTimeFragment f = new RamjanTimeFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.ramadancalender_fragment, container, false);

        context = getActivity();

        initUI(rootView);


        return rootView;
    }

    private void initUI(View view) {
        list_ramdancalender=(ListView)view.findViewById(R.id.list_ramdancalender);
        RamdanCalenderAdapter adapter = new RamdanCalenderAdapter(getActivity(), Appconstant.date);
        list_ramdancalender.setAdapter(adapter);
        list_ramdancalender.deferNotifyDataSetChanged();

    }


}