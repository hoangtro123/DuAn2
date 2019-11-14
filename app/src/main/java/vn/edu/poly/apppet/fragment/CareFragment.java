package vn.edu.poly.apppet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.poly.apppet.R;

public class CareFragment extends Fragment {

    private View c;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c = inflater.inflate (R.layout.fragment_care, container, false);






        return c;
    }

}
