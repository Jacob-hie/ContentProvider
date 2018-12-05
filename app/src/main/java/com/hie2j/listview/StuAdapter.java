package com.hie2j.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class StuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Student> studentArrayList;
    private IOnDataChangeListener listener;

    public StuAdapter(Context context,
                      ArrayList<Student> studentArrayList,
                      IOnDataChangeListener listener) {
        this.context = context;
        this.studentArrayList = studentArrayList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return studentArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(
                    R.layout.stu_item, viewGroup, false);

            StuViewHolder viewHolder = new StuViewHolder();
            viewHolder.tvStuno = view.findViewById(R.id.txt_stuno);
            viewHolder.tvName = view.findViewById(R.id.txt_name);
            viewHolder.tvAge = view.findViewById(R.id.txt_age);
            viewHolder.ivDel = view.findViewById(R.id.iv_del);

            view.setTag(viewHolder);
        }

        final Student student = studentArrayList.get(i);

        StuViewHolder viewHolder = (StuViewHolder) view.getTag();
        viewHolder.tvStuno.setText(student.getStuno());
        viewHolder.tvName.setText(student.getName());
        viewHolder.tvAge.setText(String.valueOf(student.getAge()));
        viewHolder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 不要在这里进行数据库操作
                listener.del(student);
            }
        });

        return view;
    }
}