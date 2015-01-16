package umwat.quickreddit;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created on 1/6/15.
 */
public class PostAdapter extends ArrayAdapter<Submission> {
    Context context;
    int layoutResourceId;
    ArrayList<Submission> data = null;


    public PostAdapter(Context context, int resource, int textresource, ArrayList<Submission> data) {
        super(context, resource, textresource, data);
        this.context = context;
        layoutResourceId = resource;
        this.data = data;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new Holder();

            holder.textView = (TextView) convertView.findViewById(R.id.text1);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.scoreText = (TextView) convertView.findViewById(R.id.score);
            holder.commentNumText = (TextView) convertView.findViewById(R.id.commentsnumtext);
            holder.userAndSub = (TextView) convertView.findViewById(R.id.user_and_sub);

            if (!data.get(position).isSelf() && data.get(position).getThumbnailUrl().trim().length() != 0 && !data.get(position).isOver18()) {

                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, r.getDisplayMetrics());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(px, px);
                holder.imageView.setLayoutParams(layoutParams);

                Picasso.with(context).load(data.get(position).getThumbnailUrl()).fit().into(holder.imageView);


            } else {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
                holder.imageView.setLayoutParams(layoutParams);

                Resources r = context.getResources();

            }


            convertView.setTag(holder);


        } else {
            holder = (Holder) convertView.getTag();
            if (!data.get(position).isSelf() && data.get(position).getThumbnailUrl().trim().length() != 0 && !data.get(position).isOver18()) {

                Resources r = context.getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, r.getDisplayMetrics());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(px, px);
                holder.imageView.setLayoutParams(layoutParams);

                Picasso.with(context).load(data.get(position).getThumbnailUrl()).fit().into(holder.imageView);


            } else {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(0, 0);
                holder.imageView.setLayoutParams(layoutParams);


            }


        }
        holder.textView.setText(Html.fromHtml(data.get(position).getTitle().trim()));

        long epoch = System.currentTimeMillis() / 1000 - data.get(position).getCreated();
        String elapsedTime;

        if (epoch < 60) {
            elapsedTime = epoch + " seconds ago";

        } else if (epoch >= 60 && epoch < 3600) {
            if (epoch >= 120) elapsedTime = epoch / 60 + " minutes ago";
            else elapsedTime = epoch / 60 + " minute ago";

        } else if (epoch >= 3600 && epoch < 86400) {
            if (epoch >= 3600 * 2) elapsedTime = epoch / 3600 + " hours ago";
            else elapsedTime = epoch / 3600 + " hour ago";

        } else if (epoch >= 86400 && epoch < 604800) {
            if (epoch >= 86400 * 2) elapsedTime = epoch / 86400 + " days ago";
            else elapsedTime = epoch / 86400 + " day ago";

        } else if (epoch >= 604800 && epoch < 2629743) {
            if (epoch >= 604800 * 2) elapsedTime = epoch / 604800 + " weeks ago";
            else elapsedTime = epoch / 604800 + " week ago";

        } else if (epoch >= 2629743 && epoch < 31556926) {
            if (epoch >= 2629743 * 2) elapsedTime = epoch / 2629743 + " months ago";
            else elapsedTime = epoch / 2629743 + " month ago";

        } else if (epoch >= 31556926) {
            if (epoch >= 31556926 * 2) elapsedTime = epoch / 31556926 + " years ago";
            else elapsedTime = epoch / 31556926 + " year ago";

        } else {
            elapsedTime = System.currentTimeMillis() / 1000 / 31556926 + " years ago";
        }


        holder.scoreText.setText(data.get(position).getScore() + "");
        if (data.get(position).isStickied()) {
            holder.textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else
            holder.textView.setTextColor(context.getResources().getColor(R.color.abc_primary_text_material_light));

        holder.commentNumText.setText(data.get(position).getNumComments() + "\n");
        if (MainFragment.isFront) {
            if (!data.get(position).isSelf())
                holder.userAndSub.setText(data.get(position).getAuthor() + " in " + data.get(position)
                        .getSubreddit() + "\n" + elapsedTime + " (" + data.get(position).getDomain() + ")\n");
            else
                holder.userAndSub.setText(data.get(position).getAuthor() + " in " + data.get(position)
                        .getSubreddit() + "\n" + elapsedTime + " (self)\n");
        } else {
            if (!data.get(position).isSelf())
                holder.userAndSub.setText(data.get(position).getAuthor() +
                        "\n" + elapsedTime + " (" + data.get(position).getDomain() + ")\n");
            else
                holder.userAndSub.setText(data.get(position).getAuthor() +
                        "\n" + elapsedTime + " (self)\n");
        }


        return convertView;
    }


    public static class Holder {
        TextView textView;
        ImageView imageView;
        TextView scoreText;
        TextView commentNumText;
        TextView userAndSub;


    }


}

