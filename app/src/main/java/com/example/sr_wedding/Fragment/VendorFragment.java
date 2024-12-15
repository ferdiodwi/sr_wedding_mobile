package com.example.sr_wedding.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sr_wedding.CateringActivity;
import com.example.sr_wedding.CustomAdapter;
import com.example.sr_wedding.R;
import com.example.sr_wedding.SpinnerItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Sebuah subclass sederhana dari {@link Fragment}.
 * Gunakan metode pabrik {@link VendorFragment#newInstance} untuk
 * membuat instance baru dari fragmen ini.
 */
public class VendorFragment extends Fragment {

    private ListView daftarLokasi, daftarGaun, daftarGaunPria;
    private EditText jumlahTamuEditText;
    private Button tombolHitung, tombolCatering;
    private TextView teksSelamatDatang, teksEstimasiBiaya;
    private SpinnerItem lokasiTerpilih, gaunTerpilih, gaunPriaTerpilih;
    private static final String PREFS_NAME = "UserPrefs";

    // TODO: Ganti nama argumen parameter, pilih nama yang sesuai
    // dengan parameter inisialisasi fragmen, misalnya ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Ganti nama dan tipe parameter
    private String mParam1;
    private String mParam2;

    public VendorFragment() {
        // Diperlukan konstruktor publik kosong
    }

    /**
     * Gunakan metode pabrik ini untuk membuat instance baru dari
     * fragmen ini menggunakan parameter yang disediakan.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return Instance baru dari fragmen VendorFragment.
     */
    public static VendorFragment newInstance(String param1, String param2) {
        VendorFragment fragment = new VendorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate tata letak untuk fragmen ini
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);

        inisialisasiKomponen(view);
        setelPesanSelamatDatang();
        setelDaftarView();
        setelPendengar();

        return view;
    }

    private void inisialisasiKomponen(View view) {
        teksSelamatDatang = view.findViewById(R.id.welcomeText);
        daftarLokasi = view.findViewById(R.id.locationListView);
        daftarGaun = view.findViewById(R.id.dressListView);
        daftarGaunPria = view.findViewById(R.id.menDressListView);
        jumlahTamuEditText = view.findViewById(R.id.editTextGuestCount);
        tombolHitung = view.findViewById(R.id.buttonCalculate);
        tombolCatering = view.findViewById(R.id.buttonCatering);
        teksEstimasiBiaya = view.findViewById(R.id.textViewEstimatedCost);
    }

    private void setelPesanSelamatDatang() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String username = preferences.getString("username", "Pengguna");
        teksSelamatDatang.setText("Selamat datang, " + username + "!");
    }

    private void setelDaftarView() {
        List<SpinnerItem> lokasi = new ArrayList<>();
        lokasi.add(new SpinnerItem("Pantai", 7500000, R.drawable.beach));
        lokasi.add(new SpinnerItem("Taman", 4500000, R.drawable.garden));
        lokasi.add(new SpinnerItem("Aula", 10500000, R.drawable.hall));
        lokasi.add(new SpinnerItem("Rumah", 15000000, R.drawable.home));

        List<SpinnerItem> gaun = new ArrayList<>();
        gaun.add(new SpinnerItem("Gaun Sederhana", 3000000, R.drawable.simpledress));
        gaun.add(new SpinnerItem("Gaun Elegan", 7500000, R.drawable.elegantdress));
        gaun.add(new SpinnerItem("Gaun Desainer", 15000000, R.drawable.designerdress));
        gaun.add(new SpinnerItem("Gaun Kustom", 12000000, R.drawable.customdress));

        List<SpinnerItem> gaunPria = new ArrayList<>();
        gaunPria.add(new SpinnerItem("Setelan Klasik", 4500000, R.drawable.m_simpledress));
        gaunPria.add(new SpinnerItem("Setelan Elegan", 9000000, R.drawable.m_elegantdress));
        gaunPria.add(new SpinnerItem("Pakaian Desainer", 6000000, R.drawable.m_designerdress));
        gaunPria.add(new SpinnerItem("Setelan Kustom", 3000000, R.drawable.m_customdress));

        CustomAdapter adapterLokasi = new CustomAdapter(getContext(), lokasi);
        daftarLokasi.setAdapter(adapterLokasi);

        CustomAdapter adapterGaun = new CustomAdapter(getContext(), gaun);
        daftarGaun.setAdapter(adapterGaun);

        CustomAdapter adapterGaunPria = new CustomAdapter(getContext(), gaunPria);
        daftarGaunPria.setAdapter(adapterGaunPria);
    }

    private void setelPendengar() {
        daftarLokasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lokasiTerpilih = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        daftarGaun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gaunTerpilih = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        daftarGaunPria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gaunPriaTerpilih = (SpinnerItem) parent.getItemAtPosition(position);
            }
        });

        tombolHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungBiaya();
            }
        });

        tombolCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CateringActivity.class));
            }
        });
    }

    private void hitungBiaya() {
        String jumlahTamuString = jumlahTamuEditText.getText().toString();

        if (jumlahTamuString.isEmpty() || lokasiTerpilih == null || gaunTerpilih == null || gaunPriaTerpilih == null) {
            Toast.makeText(getContext(), "Mohon isi semua bidang", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int jumlahTamu = Integer.parseInt(jumlahTamuString);
            if (jumlahTamu <= 0) {
                Toast.makeText(getContext(), "Jumlah tamu harus lebih dari nol", Toast.LENGTH_SHORT).show();
                return;
            }

            double estimasiBiaya = hitungEstimasiBiayaPernikahan(lokasiTerpilih, gaunTerpilih, gaunPriaTerpilih, jumlahTamu);

            // Format angka ke dalam Rupiah
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            String rincianBiaya = "Lokasi Terpilih: " + lokasiTerpilih.getName() + "\n" +
                    "Gaun Terpilih: " + gaunTerpilih.getName() + "\n" +
                    "Gaun Pria Terpilih: " + gaunPriaTerpilih.getName() + "\n" +
                    "Biaya Dasar: Rp" + formatRupiah.format(1000000).replace("Rp", "").trim() + "\n" +
                    "Biaya Lokasi: Rp" + formatRupiah.format(lokasiTerpilih.getCost()).replace("Rp", "").trim() + "\n" +
                    "Biaya Gaun: Rp" + formatRupiah.format(gaunTerpilih.getCost()).replace("Rp", "").trim() + "\n" +
                    "Biaya Gaun Pria: Rp" + formatRupiah.format(gaunPriaTerpilih.getCost()).replace("Rp", "").trim() + "\n" +
                    "Biaya Akomodasi per Tamu: Rp" + formatRupiah.format(50000).replace("Rp", "").trim() + "\n" +
                    "Jumlah Tamu: " + jumlahTamu + "\n" +
                    "Estimasi Biaya: Rp" + formatRupiah.format(estimasiBiaya).replace("Rp", "").trim();

            teksEstimasiBiaya.setText(rincianBiaya);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Jumlah tamu tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    private double hitungEstimasiBiayaPernikahan(SpinnerItem lokasi, SpinnerItem gaun, SpinnerItem gaunPria, int jumlahTamu) {
        // Semua biaya dalam Rupiah
        double biayaDasar = 1000000; // Rp 1.000.000
        double biayaLokasi = lokasi.getCost();
        double biayaGaun = gaun.getCost();
        double biayaGaunPria = gaunPria.getCost();
        return biayaDasar + biayaLokasi + biayaGaun + biayaGaunPria + (jumlahTamu * 50000); // Rp 50.000 per tamu
    }
}
