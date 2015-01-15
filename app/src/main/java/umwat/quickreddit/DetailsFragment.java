package umwat.quickreddit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Simon on 12/31/14.
 */
public class DetailsFragment extends Fragment {

    static TextView textView;
    static ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.imageView);


        textView = (TextView) rootView.findViewById(R.id.theTextView);

        textView.setText(Html.fromHtml(parseContents()));

        textView.setMovementMethod(LinkMovementMethod.getInstance());


        return rootView;
    }

    public String parseContents() {
        Bundle bundle = this.getArguments();
        String str = "";
        String comms = "";

        if (bundle != null) {
            str = bundle.getString("DESC");
            str = StringEscapeUtils.unescapeHtml4(str);


            comms = "<a href = " + bundle.getString("COMM") +"> Comments </a>";


        }


        if(!str.equals("null") || str.length()!=0)return str + "\n\n" + comms;
        else return comms;
    }

}
