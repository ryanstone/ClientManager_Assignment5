package layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stoneryan.android.clientmanager.LoginActivity;
import com.stoneryan.android.clientmanager.LogoffActivity;
import com.stoneryan.android.clientmanager.MainActivity;
import com.stoneryan.android.clientmanager.R;
import com.stoneryan.android.clientmanager.ViewCustomerActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayUsernameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayUsernameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayUsernameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public String name;
    public DisplayUsernameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DisplayUsernameFragment newInstance() {
        DisplayUsernameFragment fragment = new DisplayUsernameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_options_menu, menu);
        menu.findItem(R.id.settings_menu_item).setIntent(
                new Intent(getActivity(), LogoffActivity.class)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.settings_menu_item) {
            super.onOptionsItemSelected(item);
            startActivity(item.getIntent());

        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Look for saved username in SharedPreferences object (persistent between activities)
        SharedPreferences username = this.getActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String name = username.getString("username","");

        // Force user back to LoginActivity screen if no valid username is found.
        if (name.equals("")) {
            Intent i = new Intent(this.getActivity(), LoginActivity.class);
            Toast.makeText(this.getActivity(), R.string.logged_out, Toast.LENGTH_LONG).show();
            startActivity(i);
        }

        // Inflate the layout for this fragment
        // Display username programmatically.
        View layout = inflater.inflate(R.layout.fragment_display_username, container, false);
        TextView user = (TextView)layout.findViewById(R.id.username_tv);
        user.setText(name);
        return layout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
