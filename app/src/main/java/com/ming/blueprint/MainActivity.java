package com.ming.blueprint;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ming.blueprint.db.DaoSession;
import com.ming.blueprint.db.Student;
import com.ming.blueprint.db.StudentDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView dataList;
    private StudentDao dao;
    private Adapter adapter;
    private List<Student> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.green_dao_toolbar);
        dataList = findViewById(R.id.green_dao_list);
        toolbar.inflateMenu(R.menu.green_dao_toolbar_menu);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_action_add:
                    insert();
                    setAdapter();
                    break;
                case R.id.item_action_delete:
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("删除表中所有的男生？")
                            .setPositiveButton("是", (dialogInterface, i) -> {
                                delete("男");
                                setAdapter();
                            }).setNegativeButton("不，删除女生", (dialogInterface, i) -> {
                                delete("女");
                                setAdapter();
                            }).show();
                    break;
                case R.id.item_action_update:
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("把表中所有的男生变成女生？")
                            .setPositiveButton("是", (dialogInterface, i) -> {
                                update("男");
                                setAdapter();
                            }).setNegativeButton("不，把所有的女生变成男生", (dialogInterface, i) -> {
                                update("女");
                                setAdapter();
                            }).show();
                    break;
                case R.id.item_action_clean:
                    deleteAll();
                    setAdapter();
                    break;
            }
            return false;
        });
        loadData();
    }

    private void loadData() {
        list = new ArrayList<>();
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        dao = daoSession.getStudentDao();
        dataList.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }


    private void setAdapter() {
        list.clear();
        List<Student> tempList = queryAll();
        for (int i = 0, size = tempList.size(); i < size; i++) {
            list.add(tempList.get(i));
        }
        if (adapter == null) {
            adapter = new Adapter(this, list);
            dataList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<Student> queryAll() {
        return dao.loadAll();
    }

    private int id = 1;
    private int name = 1;

    private void insert() {
        Student student = new Student();
        student.setId(id++);
        student.setName("小红" + name++);
        if ((int) (1 + Math.random() * (2 - 1 + 1)) == 1) {
            student.setSex("男");
        } else {
            student.setSex("女");
        }
        student.setAge(String.valueOf((int) (10 + Math.random() * (20 - 10 + 1))));
        student.setGrade(String.valueOf((int) (1 + Math.random() * (9 - 1 + 1))));
        student.setHobby("学习");

        dao.insert(student);
    }

    private void deleteAll() {
        dao.deleteAll();
    }

    private void delete(String sex) {
        Query query = dao.queryBuilder().where(StudentDao.Properties.Sex.eq(sex)).build();
        List<Student> student = query.list();
        dao.deleteInTx(student);
    }

    private void update(String sex) {
        Query query = dao.queryBuilder().where(StudentDao.Properties.Sex.eq(sex)).build();
        List<Student> student = query.list();
        for (int i = 0, size = student.size(); i < size; i++) {
            if (sex.equals("男")) {
                student.get(i).setSex("女");
            } else {
                student.get(i).setSex("男");
            }
        }
        dao.updateInTx(student);
    }
}
