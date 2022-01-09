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
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GridView gridView;

    String[] namaProject = {"Event Party", "Graduation", "School Party", "Wedding Stage",
                        "Event Party", "Graduation", "School Party", "Wedding Stage"};
    int[] imageProject = {R.drawable.eventparty, R.drawable.graduation, R.drawable.schoolparty, R.drawable.weddingstage,
                        R.drawable.eventparty, R.drawable.graduation, R.drawable.schoolparty, R.drawable.weddingstage};
    int[] detailProject = {R.string.lorem, R.string.lorem, R.string.lorem, R.string.lorem,
                        R.string.lorem, R.string.lorem, R.string.lorem, R.string.lorem};

    public ProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
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
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        gridView = view.findViewById(R.id.gridViewProject);

        CustomAdapter customAdapter = new CustomAdapter(namaProject, imageProject, detailProject, this);

        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = namaProject[i];
                int selectedImage = imageProject[i];
                int selectedDetail = detailProject[i];

                Intent intent = new Intent(getActivity(), DetailProjectActivity.class);
                intent.putExtra("nama", selectedName);
                intent.putExtra("image", selectedImage);
                intent.putExtra("detail", selectedDetail);
                startActivity(intent);
            }
        });

        return view;
    }

    public class CustomAdapter extends BaseAdapter {

        private String[] namaProject;
        private int[] imageProject;
        private int[] detailProject;
//        private Context context;
        private LayoutInflater layoutInflater;
        private ProjectFragment projectFragment;

//        public CustomAdapter(String[] namaProject, int[] imageProject, Context context) {
//            this.namaProject = namaProject;
//            this.imageProject = imageProject;
//            this.context = context;
//            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }

        public CustomAdapter(String[] namaProject, int[] imageProject, int[] detailProject, ProjectFragment projectFragment) {
            this.namaProject = namaProject;
            this.imageProject = imageProject;
            this.detailProject = detailProject;
            this.projectFragment = projectFragment;
            this.layoutInflater = (LayoutInflater) projectFragment.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return imageProject.length;
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
                view = layoutInflater.inflate(R.layout.list_project, viewGroup, false);
            }

            TextView txtNamaProject = view.findViewById(R.id.txt_nama_project);
            ShapeableImageView imgProject = view.findViewById(R.id.img_project);
            int dtlProject;

            txtNamaProject.setText(namaProject[i]);
            imgProject.setImageResource(imageProject[i]);
            dtlProject = (detailProject[i]);

            return view;
        }
    }
}