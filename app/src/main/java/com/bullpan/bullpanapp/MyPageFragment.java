package com.bullpan.bullpanapp;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bullpan.bullpanapp.adapter.MyChatroomListAdapter;
import com.sendbird.android.model.Channel;
import com.sendbird.android.shadow.com.google.gson.Gson;
import com.sendbird.android.shadow.com.google.gson.JsonElement;
import com.sendbird.android.shadow.com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MyPageFragment extends Fragment implements AbsListView.OnItemClickListener {

//    private OnFragmentInteractionListener mListener;
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MyChatroomListAdapter mAdapter;
    List<Channel> mChatroomList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Change Adapter to display your content
//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

        mChatroomList = new ArrayList<>();
        mAdapter = new MyChatroomListAdapter(this.getActivity(),mChatroomList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        JsonObject object= new JsonObject();
        object.addProperty("id",0);
        object.addProperty("member_count", 10);
        object.addProperty("channel_url", "http://#");
        object.addProperty("name", "Mychannel");
        object.addProperty("created_at", new Date().getTime());
        object.addProperty("cover_img_url", "http://#");

        mAdapter.add(Channel.build(object));
        mAdapter.add(Channel.build(object));
        mAdapter.add(Channel.build(object));
        mAdapter.add(Channel.build(object));
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
////        try {
////            mListener = (OnFragmentInteractionListener) activity;
////        } catch (ClassCastException e) {
////            throw new ClassCastException(activity.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public static MyPageFragment newInstance(int sectionNumber) {
        
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
//    }

}
