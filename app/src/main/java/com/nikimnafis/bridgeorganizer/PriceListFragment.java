package com.nikimnafis.bridgeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GridView gridView;

    String[] namaPaket = {"Engagement", "Pre-Wedding", "Akad/Reception", "Wedding Day",
            "Diamond", "Exclusive", "Additional Charge"};
    int[] imagePaket = {R.drawable.engagement, R.drawable.pre_wedding, R.drawable.reception, R.drawable.wedding_day,
            R.drawable.diamond, R.drawable.exclusive, R.drawable.additional_charge};
    int[] detailPaket = {R.string.pl_engagement, R.string.pl_pre_wedding, R.string.pl_akad, R.string.pl_wedding_day,
            R.string.pl_diamond, R.string.pl_exclusive, R.string.pl_additional_charge};

    public PriceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PriceListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PriceListFragment newInstance(String param1, String param2) {
        PriceListFragment fragment = new PriceListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_list, container, false);

        gridView = view.findViewById(R.id.gridViewPriceList);

        CustomAdapter customAdapter = new CustomAdapter(namaPaket, imagePaket, detailPaket, this);

        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = namaPaket[i];
                int selectedImage = imagePaket[i];
                int selectedDetail = detailPaket[i];

                Intent intent = new Intent(getActivity(), DetailPriceListActivity.class);
                intent.putExtra("nama", selectedName);
                intent.putExtra("image", selectedImage);
                intent.putExtra("detail", selectedDetail);
                startActivity(intent);
            }
        });

        return view;
    }

    public class CustomAdapter extends BaseAdapter {

        private String[] namaPaket;
        private int[] imagePaket;
        private int[] detailPaket;
        private LayoutInflater layoutInflater;
        private PriceListFragment priceListFragment;

        public CustomAdapter(String[] namaPaket, int[] imagePaket, int[] detailPaket, PriceListFragment priceListFragment) {
            this.namaPaket = namaPaket;
            this.imagePaket = imagePaket;
            this.detailPaket = detailPaket;
            this.priceListFragment = priceListFragment;
            this.layoutInflater = (LayoutInflater) priceListFragment.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return imagePaket.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_price_list, viewGroup, false);
            }

            TextView txtNamaPaket = view.findViewById(R.id.txt_nama_paket);
            ShapeableImageView imgPaket = view.findViewById(R.id.img_paket);
            int dtlPaket;

            txtNamaPaket.setText(namaPaket[i]);
            imgPaket.setImageResource(imagePaket[i]);
            dtlPaket = (detailPaket[i]);

            return view;
        }
    }
}