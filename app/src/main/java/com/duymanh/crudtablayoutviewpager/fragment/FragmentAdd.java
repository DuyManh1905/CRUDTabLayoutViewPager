package com.duymanh.crudtablayoutviewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.crudtablayoutviewpager.MainActivity;
import com.duymanh.crudtablayoutviewpager.R;
import com.duymanh.crudtablayoutviewpager.adapter.RecycleViewAdapter;
import com.duymanh.crudtablayoutviewpager.adapter.SpinnerAdapter;
import com.duymanh.crudtablayoutviewpager.model.Cat;

public class FragmentAdd extends Fragment implements RecycleViewAdapter.CatItemListener{

    private RecycleViewAdapter adapter;
    private Spinner spinner;
    private EditText editName, editPrice, eidtInfo;
    private Button btAdd, btUpdate;
    private RecyclerView recyclerView;
    private int poscurr;
    private int[] imgs={R.drawable.cat1,R.drawable.cat2,R.drawable.cat3,
                        R.drawable.cat4,R.drawable.cat5,R.drawable.cat6};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = spinner.getSelectedItem().toString();
                int img;
                try {
                    img = imgs[Integer.parseInt(i)];
                    double price = Double.parseDouble(editPrice.getText().toString());
                    Cat cat = new Cat(img,editName.getText().toString(),price,eidtInfo.getText().toString());
                    adapter.add(cat);
                }catch (NumberFormatException e){

                }
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = spinner.getSelectedItem().toString();
                int img;
                try {
                    img = imgs[Integer.parseInt(i)];
                    double price = Double.parseDouble(editPrice.getText().toString());
                    Cat cat = new Cat(img, editName.getText().toString(), price, eidtInfo.getText().toString());
                    adapter.update(poscurr,cat);
                    adapter.notifyDataSetChanged();
                    btUpdate.setVisibility(View.INVISIBLE);
                    btAdd.setVisibility(View.VISIBLE);
                } catch (NumberFormatException e) {

                }
            }
        });

    }

    private void initView(View view) {
        //
        spinner = view.findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(),imgs);
        spinner.setAdapter(adapter);
        //
        editName = view.findViewById(R.id.editName);
        editPrice = view.findViewById(R.id.editPrice);
        eidtInfo = view.findViewById(R.id.editInfo);
        //
        btAdd = view.findViewById(R.id.btnAdd);
        btUpdate = view.findViewById(R.id.btnUpdate);
        //
        recyclerView = view.findViewById(R.id.reView);
        //
        btUpdate.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        System.out.println("da vao ham sua item");
        btAdd.setVisibility(View.INVISIBLE);
        btUpdate.setVisibility(View.VISIBLE);
        poscurr = position;
        Cat cat = adapter.getItem(position);
        int img = cat.getImg();
        int p=0;
        for(int i=0;i<imgs.length;i++){
            if (img==imgs[i]){
                p=i;
                break;
            }
        }
        spinner.setSelection(p);
        editName.setText(cat.getName());
        editPrice.setText(cat.getPrice()+"");
        eidtInfo.setText(cat.getInfor());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).list=adapter.getListCat();
    }
}