package com.example.amirah.playgram2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amirah.playgram2.adapter.PostinganListAdapter;
import com.example.amirah.playgram2.entity.Postingan;
import com.example.amirah.playgram2.model.PostinganViewModel;

import java.sql.Timestamp;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabSchedule extends Fragment {

    private OnFragmentInteractionListener mListener;
    private PostinganViewModel mPostinganViewModel;

    public TabSchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_schedule, container, false);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TabView.NEW_POSTINGAN_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Postingan postingan = new Postingan();
            postingan.setCaption(data.getStringExtra(AddSchedule.CAPTION));
            postingan.setPostTime(data.getStringExtra(AddSchedule.POST_TIME));
            postingan.setPathPicture(data.getStringExtra(AddSchedule.IMAGE_PATH));
            postingan.setId(String.valueOf(new Timestamp(System.currentTimeMillis())));
            mPostinganViewModel.insert(postingan);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
