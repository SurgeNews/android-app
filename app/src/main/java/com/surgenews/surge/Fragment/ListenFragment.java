package com.surgenews.surge.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.surgenews.surge.R;

/**
 * Created by anishhegde on 10/09/16.
 */
public class ListenFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listen_fragment, container, false);
        Bundle args = getArguments();
        return rootView;
    }
}
