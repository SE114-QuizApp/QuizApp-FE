package com.example.quizapp_fe.fragments;


import static java.lang.String.*;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.api.account.profile.GetMeApi;
import com.example.quizapp_fe.dialogs.LoadingDialog;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.RankingUsers;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class ProfileFragment extends Fragment {
    ImageView imgAvatar;
    TextView tvName;
    TextView txtFollowing;
    TextView txtPoints;

    // variable for our bar chart
    BarChart barChart;

    PieChart pieChart;

    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        loadingDialog = new LoadingDialog(requireContext());
        imgAvatar = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tvName);
        txtFollowing = view.findViewById(R.id.txtFollowing);
        txtPoints = view.findViewById(R.id.txtPoints);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        ImageView btnSetting = view.findViewById(R.id.btnSetting);

        getMe();

        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.idBarChart);

        setDataBarChart();

        pieChart = view.findViewById(R.id.idPieChart);

        setDataPieChart();



        TextView txtRank = view.findViewById(R.id.txtRank);
        if(RankingUsers.getInstance().getCurrentUser() == null){
            txtPoints.setText("0");
            txtRank.setText("0");
        }else{
            txtPoints.setText(String.format("%d", RankingUsers.getInstance().getCurrentUser().getPoint()));
            txtRank.setText(String.format("%d", RankingUsers.getInstance().getCurrentUser().getRank()));
        }
        if(CredentialToken.getInstance(requireContext()).getUserProfile() == null){
            txtPoints.setText("0");
            txtRank.setText("0");
            txtFollowing.setText("0");
        }else{
            txtPoints.setText(String.format("%d", CredentialToken.getInstance(requireContext()).getUserProfile().getPoint()));
            txtFollowing.setText(String.format("%d", CredentialToken.getInstance(requireContext()).getUserProfile().getFriends().size()));
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, new ProfileSettingFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(requireActivity(), HomeActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        // Inflate the layout for this fragment
        return view;
    }


    private int random() {
        return (int) (Math.random() * 15) + 10;
    }
    void setDataPieChart(){
        String[] labels = {"Math", "English", "World Language",  "Science", "Physics", "Chemistry" ,  "Geography", "History"};
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            entries.add(new PieEntry((int) (Math.random() * 10) + 1));
        }



        PieDataSet dataSet = new PieDataSet(entries, "");

        Random random = new Random();
        ArrayList<Integer> customColors = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            customColors.add(color);
        }
        dataSet.setColors(customColors);
        dataSet.setSliceSpace(2f); // Khoảng cách giữa các phần
        dataSet.setValueTextSize(14f); // Đặt kích thước văn bản cho giá trị
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return valueOf((int) value); // Chuyển đổi giá trị thành số nguyên
            }
        };

        // Đặt định dạng giá trị cho dataSet của biểu đồ
        dataSet.setValueFormatter(valueFormatter);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.setEntryLabelTextSize(14f);// Đặt kích thước của nhãn


        // Ẩn nhãn trên các phần
        pieChart.setDrawEntryLabels(false);

        // Ẩn chú thích trên biểu đồ
        pieChart.getLegend().setEnabled(true);

        // Tạo chú thích riêng
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true); // Cho phép dòng chữ tự động xuống dòng nếu cần
        legend.setForm(Legend.LegendForm.SQUARE); // Chỉ định hình dạng của biểu tượng chú thích (hình vuông)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Căn chú thích về phía dưới của biểu đồ
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Căn chú thích vào giữa
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Đặt hướng của chú thích là ngang
        legend.setDrawInside(false); // Không vẽ chú thích bên trong biểu đồ
        legend.setTextSize(14f); // Đặt kích thước văn bản cho chú thích
        legend.setXEntrySpace(10f); // Khoảng cách giữa các chú thích theo chiều ngang
        legend.setYEntrySpace(5f); // Khoảng cách giữa các chú thích theo chiều dọc

        // Thiết lập màu và nhãn cho chú thích
//        String[] labels = {"Math", "English", "World Language", "Fun", "Science", "Physics", "Chemistry", "Biology", "Social science", "Geography", "History", "Art"};

        // Khởi tạo một ArrayList để chứa các màu
        ArrayList<Integer> colors = new ArrayList<>();

        // Lặp qua mảng màu và thêm từng màu vào ArrayList
        for (int color : customColors) {
            colors.add(color);
        }

        ArrayList<LegendEntry> legendEntries = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.label = labels[i];
            entry.formColor = colors.get(i);
            legendEntries.add(entry);
        }
        legend.setCustom(legendEntries);
    }
    void setDataBarChart() {
        // calling method to get bar entries.
        // creating a new array list
        ArrayList barEntriesArrayList = new ArrayList<>();
        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        // x increase from 0 to 5, and y random value from 1 to 30 concat with character "f".
        barEntriesArrayList.add(new BarEntry(0, random()));
        barEntriesArrayList.add(new BarEntry(1, random()));
        barEntriesArrayList.add(new BarEntry(2, random()));
        barEntriesArrayList.add(new BarEntry(3, random()));
        barEntriesArrayList.add(new BarEntry(4, random()));
        barEntriesArrayList.add(new BarEntry(5, random()));

        BarDataSet dataSet = new BarDataSet(barEntriesArrayList, "Months");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return valueOf((int) value); // Chuyển đổi giá trị thành số nguyên
            }
        };

        // Đặt định dạng giá trị cho dataSet của biểu đồ
        dataSet.setValueFormatter(valueFormatter);
        dataSet.setValueTextSize(14f); // Đặt kích thước văn bản cho giá trị

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.getDescription().setEnabled(false); // Tắt chú thích
        barChart.getLegend().setEnabled(false); // Tắt trục
        barChart.getAxisRight().setEnabled(false); // Tắt trục bên phải
        barChart.getXAxis().setDrawGridLines(false); // Tắt đường kẻ ngang
        barChart.getAxisLeft().setAxisMinimum(1f); // Đặt giá trị tối thiểu cho trục bên trái
        barChart.getAxisLeft().setAxisMaximum(30f); // Đặt giá trị tối đa cho trục bên trái
        barChart.setExtraBottomOffset(30f); // Đặt lệch dưới

        // Cấu hình trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Đặt vị trí của trục X ở dưới
        xAxis.setDrawGridLines(false); // Tắt các đường lưới của trục X
        xAxis.setDrawAxisLine(false); // Tắt đường viền của trục X
        xAxis.setTextSize(14f); // Đặt kích thước văn bản cho trục X
        xAxis.setYOffset(8f); // Đặt lệch theo trục Y
        xAxis.setGranularity(1f); // Đặt độ chia của trục X
        xAxis.setGranularityEnabled(true); // Bật độ chia của trục X

        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun"})); // Đặt các nhãn cho trục X

    }

    private void getMe() {
        GetMeApi.getAPI(requireContext()).getMe().enqueue(new retrofit2.Callback<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, @NonNull retrofit2.Response<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;

                    System.out.println(response.body().getUser().getUsername());
                    System.out.printf("%s %s%n", response.body().getUser().getFirstName(), response.body().getUser().getLastName());
                    if (response.body().getUser().getFirstName() != null && response.body().getUser().getLastName() != null) {
                        System.out.println("Full name:");
                        tvName.setText(format("%s %s", response.body().getUser().getFirstName(), response.body().getUser().getLastName()));
                    } else {
                        tvName.setText(response.body().getUser().getUsername());
                    }
                    txtPoints.setText(String.valueOf(response.body().getUser().getPoint()));
                    txtFollowing.setText(String.valueOf(response.body().getUser().getFriends().size()));


                    if (response.body().getUser().getAvatar() != null) {
                        Glide.with(requireContext())
                             .load(response.body().getUser().getAvatar())
                             .circleCrop()
                             .into(imgAvatar);
                    }

                } else {
                    Toast.makeText(requireContext(), "Get Me failure",
                                   Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.example.quizapp_fe.api.account.auth.LoginWithPasswordApiResult> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(requireContext(), "Call API failure",
                               Toast.LENGTH_SHORT).show();
            }
        });
    }
}