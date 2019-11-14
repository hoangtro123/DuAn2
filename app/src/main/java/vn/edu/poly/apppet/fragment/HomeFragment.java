package vn.edu.poly.apppet.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.poly.apppet.Constant;
import vn.edu.poly.apppet.R;
import vn.edu.poly.apppet.adapter.PetAdapter;
import vn.edu.poly.apppet.database.DatabaseHelper;
import vn.edu.poly.apppet.database.PetDAO;
import vn.edu.poly.apppet.database.TypeDAO;
import vn.edu.poly.apppet.database.UserDAO;
import vn.edu.poly.apppet.model.Pet;
import vn.edu.poly.apppet.model.Type;
import vn.edu.poly.apppet.model.User;

public class HomeFragment extends Fragment {

    private View h;

    private ImageView imvAvt;
    private TextView tvName;
    private TextView tvOld;
    private TextView tvType;
    private ImageView imvUp;

    private RecyclerView rcvPet;
    private DatabaseHelper databaseHelper;
    private List<Pet> pets;
    private PetAdapter petAdapter;
    private LinearLayoutManager linearLayoutManager;
    private PetDAO petDAO;


    private UserDAO userDAO;


    private TypeDAO typeDAO;


    private String loai;
    private long datePicker = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        h = inflater.inflate (R.layout.fragment_home, container, false);
        intivity ();

        //xet avt


        ///


        databaseHelper = new DatabaseHelper (getActivity ());
        petDAO = new PetDAO (databaseHelper);
        userDAO = new UserDAO (databaseHelper);
        pets = petDAO.getAllPet ();


        Pet p = new Pet ();
        p.idPet = "000000001";
        p.idType = "000000011";
        p.ten = "Xương chó";
        p.ngay = "56454666";
        p.gio = "10000";
        p.note = "Không có ghi chú";

        pets.add (p);

        petAdapter = new PetAdapter (getActivity (), pets, petDAO);

        rcvPet.setAdapter (petAdapter);
        linearLayoutManager = new LinearLayoutManager (getActivity ());
        rcvPet.setLayoutManager (linearLayoutManager);


        //fab_button
        FloatingActionButton fabThuocTinh = h.findViewById (R.id.fab_thuoctinh);
        fabThuocTinh.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                DialogThemThuocTinh ();


            }
        });


        FloatingActionButton fabPet = h.findViewById (R.id.fab_pet);
        fabPet.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DialogPet ();
            }
        });


        //imvUp setOnClick

        imvUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText (getActivity (), "imvUp", Toast.LENGTH_SHORT).show ();
                showDialogInfomation ();
            }
        });

        setInformation();

        return h;
    }

    private void setInformation() {
        //setInfomation
        User uu = new User ();
        uu = userDAO.getUserById ("1");



        tvName.setText (uu.name);
        tvOld.setText (uu.old);
        tvType.setText (uu.type);

    }

    private void DialogPet() {

        final Dialog dialog = new Dialog (getActivity ());
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView (R.layout.dialog_pet);


        final Spinner spType;
        final EditText edtName, edtNote;
        final TextView tvGio;
        final Button btGio;
        final TextView tvNgay;
        final Button btNgay;
        final Button btHuy;
        final Button btThem;

        spType = dialog.findViewById (R.id.spType);
        edtName = dialog.findViewById (R.id.edtName);
        tvGio = dialog.findViewById (R.id.tvGio);
        btGio = dialog.findViewById (R.id.btGio);
        tvNgay = dialog.findViewById (R.id.tvNgay);
        btNgay = dialog.findViewById (R.id.btNgay);
        edtNote = dialog.findViewById (R.id.edtNote);
        btHuy = dialog.findViewById (R.id.btHuy);
        btThem = dialog.findViewById (R.id.btThem);

        //xu ly spiner
        final TypeDAO manager;
        manager = new TypeDAO (databaseHelper);
        List<String> list = new ArrayList<> ();

        list = manager.getAllType ();


        final ArrayAdapter<String> adapter = new ArrayAdapter (getActivity (), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource (android.R.layout.simple_list_item_single_choice);
        spType.setAdapter (adapter);


        spType.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                loai = spType.getSelectedItem () + "";
                Toast.makeText (getActivity (), spType.getSelectedItem () + "", Toast.LENGTH_SHORT).show ();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btGio.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showTimePickerDialog (tvGio);
            }
        });

        btNgay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showDatePickerDialog (tvNgay);
            }
        });

        //nút thêm

        btThem.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Pet pet = new Pet ();
                String type = loai.toString ();
                String name = edtName.getText ().toString ().trim ();
                String gio = tvGio.getText ().toString ();
                String ngay = tvNgay.getText ().toString ().trim ();
                String note = edtNote.getText ().toString ().trim ();

                pet.idType = type;
                pet.ten = name;
                pet.gio = gio;
                pet.ngay = ngay;
                pet.note = note;

                if (type.equals ("") || name.equals ("") || gio.equals ("") || ngay.equals ("")) {
                    Toast.makeText (getActivity (), "Không được để trống", Toast.LENGTH_SHORT).show ();
                    return;
                } else {
                    long re = petDAO.insertPet (pet);
                    if (re > 0) {

                        pets.add (0, pet);

                        // f5 list view
                        petAdapter.notifyDataSetChanged ();
                        Toast.makeText (getActivity (), "Đã thêm", Toast.LENGTH_SHORT).show ();

                        dialog.cancel ();


                    } else {
                        Toast.makeText (getActivity (), "Lỗi!", Toast.LENGTH_SHORT).show ();
                    }


                }


            }
        });

        btHuy.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.cancel ();
            }
        });


        //custom dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams ();
        lp.copyFrom (dialog.getWindow ().getAttributes ());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show ();
        dialog.getWindow ().setAttributes (lp);


    }

    private void DialogThemThuocTinh() {
        final Dialog dialog = new Dialog (getActivity ());
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView (R.layout.dialog_themthuoctinh);


        final EditText edtType;
        final Button btHuyThuocTinh;
        final Button btnAdd;

        edtType = dialog.findViewById (R.id.edtType);
        btHuyThuocTinh = dialog.findViewById (R.id.btHuyThuocTinh);
        btnAdd = dialog.findViewById (R.id.btnAdd);


        typeDAO = new TypeDAO (databaseHelper);


        btnAdd.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Type type = typeDAO.getTypeByID (edtType.getText ().toString ().trim ());

                if (type == null) {

                    type = new Type ();
                    String id = edtType.getText ().toString ().trim ();


                    type.id = id;

                    long re = typeDAO.insertType (type);

                    if (re > 0) {
                        //Thông báo thành công và tắt dialog

                        //cập nhật list

                        Toast.makeText (getActivity (), "Đã thêm", Toast.LENGTH_SHORT).show ();
                        dialog.cancel ();


                    } else {
                        // Thong bao loi xay ra
                        Toast.makeText (getActivity (), "Có lỗi sảy ra", Toast.LENGTH_SHORT).show ();
                    }


                } else {

                    Toast.makeText (getActivity (), "Thuộc tính đã tồn tại", Toast.LENGTH_SHORT).show ();
                    dialog.cancel ();

                }


            }
        });


        btHuyThuocTinh.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.cancel ();
            }
        });


        //custom dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams ();
        lp.copyFrom (dialog.getWindow ().getAttributes ());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show ();
        dialog.getWindow ().setAttributes (lp);

    }

    private void intivity() {
        imvAvt = h.findViewById (R.id.imvAvt);
        tvName = h.findViewById (R.id.tvName);
        tvOld = h.findViewById (R.id.tvOld);
        tvType = h.findViewById (R.id.tvType);
        rcvPet = h.findViewById (R.id.rcvPet);
        imvUp = h.findViewById (R.id.imvUp);


    }


    public void showTimePickerDialog(final TextView gio) {

        //lấy thời gian và hiện tại
        Calendar cal = Calendar.getInstance ();
        final int hour = cal.get (Calendar.HOUR_OF_DAY);
        int minute = cal.get (Calendar.MINUTE);

        //b2: khoi tạo time picker dialog
        TimePickerDialog time = new TimePickerDialog (getActivity (), new TimePickerDialog.OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                Toast.makeText (getActivity (), hourOfDay+ ":" + minute, Toast.LENGTH_SHORT).show ();
                gio.setText (hourOfDay + ":" + minute);

            }
        }, hour, minute, false);

        //b3: hien thi
        time.show ();


    }

    public void showDatePickerDialog(final TextView ngay) {

        //b1: lấy thông tin ngày tháng
        Calendar cal = Calendar.getInstance ();
        int year = cal.get (Calendar.YEAR);
        int month = cal.get (Calendar.MONTH);
        int day = cal.get (Calendar.DAY_OF_MONTH);

        //b2: khởi tạo date picker dialog
        DatePickerDialog date = new DatePickerDialog (getActivity (), new DatePickerDialog.OnDateSetListener () {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                Toast.makeText (getActivity (), year+"/"+month+"/"+dayOfMonth, Toast.LENGTH_SHORT).show ();
                ngay.setText (dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);


        date.show ();


    }


    public void showDialogInfomation() {

        final Dialog dialog = new Dialog (getActivity ());
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.setContentView (R.layout.dialog_infomation);

        final EditText edName;
        final EditText edOld;
        final EditText idType;
        final EditText idNote;
        final Button btUp;
        final Button btCande;

        edName = dialog.findViewById (R.id.edName);
        edOld = dialog.findViewById (R.id.edOld);
        idType = dialog.findViewById (R.id.idType);
        idNote = dialog.findViewById (R.id.idNote);
        btUp = dialog.findViewById (R.id.btUp);
        btCande = dialog.findViewById (R.id.btCande);

//
        userDAO = new UserDAO (databaseHelper);
        User u = new User ();
        u.id = "1";
        u.name = "a";
        u.old = "2";
        u.type = "ad";
        long a =  userDAO.insertUser (u);
        Toast.makeText (getActivity (), a+"", Toast.LENGTH_SHORT).show ();

        User user1 = userDAO.getUserById ("1");

        edName.setText (user1.name);
        edOld.setText (user1.old+"");
        idType.setText (user1.type);



        btUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText (getActivity (), "btUp", Toast.LENGTH_SHORT).show ();

                User user = new User ();
                String name = edName.getText ().toString ().trim ();
                String old =  edOld.getText ().toString ().trim ();
                String type = idType.getText ().toString ().trim ();

                user.id = "1";
                user.name = name;
                user.old = old;
                user.type = type;

                userDAO = new UserDAO (databaseHelper);
                long re = userDAO.updateUser (user);
                if (re > 0) {

                    setInformation ();
                    Toast.makeText (getActivity (), "Cập nhật thành công!", Toast.LENGTH_SHORT).show ();
                    dialog.cancel ();
                } else {

                    Toast.makeText (getActivity (), "Xảy ra lỗi !!!", Toast.LENGTH_SHORT).show ();
                }


            }
        });


        btCande.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.cancel ();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams ();
        lp.copyFrom (dialog.getWindow ().getAttributes ());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show ();
        dialog.getWindow ().setAttributes (lp);




    }


}
