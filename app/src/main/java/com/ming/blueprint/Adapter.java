package com.ming.blueprint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ming.blueprint.db.Student;

import java.util.List;

/**
 * Created by ming on 2017/9/12.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.GreenDaoViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Student> list;

    public Adapter(Context context, List<Student> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GreenDaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GreenDaoViewHolder(layoutInflater.inflate(R.layout.item_green_dao_list, parent, false));
    }

    @Override
    public void onBindViewHolder(GreenDaoViewHolder holder, int position) {
        holder.item_student_id.setText(String.valueOf(list.get(position).getId()));
        holder.item_student_name.setText(list.get(position).getName());
        holder.item_student_sex.setText(list.get(position).getSex());
        holder.item_student_age.setText(list.get(position).getAge());
        holder.item_student_grade.setText(list.get(position).getGrade());
        holder.item_student_hobby.setText(list.get(position).getHobby());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GreenDaoViewHolder extends RecyclerView.ViewHolder {
        TextView item_student_id,
                item_student_name,
                item_student_sex,
                item_student_age,
                item_student_grade,
                item_student_hobby;

        public GreenDaoViewHolder(View itemView) {
            super(itemView);
            item_student_id = itemView.findViewById(R.id.item_student_id);
            item_student_name = itemView.findViewById(R.id.item_student_name);
            item_student_sex = itemView.findViewById(R.id.item_student_sex);
            item_student_age = itemView.findViewById(R.id.item_student_age);
            item_student_grade = itemView.findViewById(R.id.item_student_grade);
            item_student_hobby = itemView.findViewById(R.id.item_student_hobby);
        }
    }

}
