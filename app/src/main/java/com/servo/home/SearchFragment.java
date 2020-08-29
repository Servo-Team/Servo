package com.servo.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.servo.auth.R;
import com.servo.database.Database;
import com.servo.database.Service;
import com.servo.database.ServiceDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchFragment extends BaseFragment {
    private View globalView;
    private SearchView search;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_search, container, false);

        search = (SearchView) globalView.findViewById(R.id.searchViewButton);
        listView = (ListView) globalView.findViewById(R.id.searchListView);

        List<String> arr = null;
        try {
            arr = findAllServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arr
        );

        listView.setAdapter(adapter);
        final List<String> finalArr = arr;
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search.clearFocus();
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setIconified(false);
            }
        });
        return globalView;
    }

    private List<String> findAllServices() throws Exception {
        Database db = new ServiceDatabase();
        List<String> ret = new ArrayList();
        List<Object> list = db.getObjs();

        for(int i=0; i<list.size(); i++){
            Service service = (Service) list.get(i);
            ret.add(service.getID() + " : " + service.getTitle());
        }

        return ret;
    }
}