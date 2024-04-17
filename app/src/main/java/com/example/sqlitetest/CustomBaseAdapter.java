package com.example.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    ArrayList<String> names;
    ArrayList<String> branch;
    ArrayList<String> marks;
    Context context;
    LayoutInflater inflater;
    public CustomBaseAdapter(Context ctx, ArrayList<String> names, ArrayList<String> branch, ArrayList<String> marks){
        this.context = ctx;
        this.names = names;
        this.branch = branch;
        this.marks = marks;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_list_view, null);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView4);
        TextView branchTextView = (TextView) convertView.findViewById(R.id.textView5);
        TextView marksTextView = (TextView) convertView.findViewById(R.id.textView6);
        nameTextView.setText(names.get(position));
        branchTextView.setText(branch.get(position));
        marksTextView.setText(marks.get(position));
        return convertView;
    }

    public void filteredStudents(ArrayList<String> n, ArrayList<String> b, ArrayList<String> m){
        names = n;
        branch = b;
        marks = m;
        notifyDataSetChanged();
    }
}
