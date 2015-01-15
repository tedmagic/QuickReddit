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
 * Created by Simon on 1/6/15.
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
            holder.domain = (TextView) convertView.findViewById(R.id.domain);

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
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, r.getDisplayMetrics());
               /* layoutParams = new RelativeLayout.LayoutParams(px,px);
                px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, r.getDisplayMetrics());
                holder.scoreText.setPadding(holder.scoreText.getPaddingLeft(),px,
                        holder.scoreText.getPaddingRight(),holder.scoreText.getPaddingBottom());
                holder.scoreText.setLayoutParams(layoutParams);
                */
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




                /*layoutParams = new RelativeLayout.LayoutParams(px,px);
                px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, r.getDisplayMetrics());
                holder.scoreText.setPadding(holder.scoreText.getPaddingLeft(),px,
                        holder.scoreText.getPaddingRight(),holder.scoreText.getPaddingBottom());
                holder.scoreText.setLayoutParams(layoutParams);
                */


            }


        }
        holder.textView.setText(Html.fromHtml(data.get(position).getTitle().trim()));

        holder.scoreText.setText(data.get(position).getScore() + "");
        if (data.get(position).isStickied()) {
            holder.textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else
            holder.textView.setTextColor(context.getResources().getColor(R.color.abc_primary_text_material_light));

        holder.commentNumText.setText(data.get(position).getNumComments() + "\n");
        holder.userAndSub.setText(data.get(position).getAuthor() + " in " + data.get(position).getSubreddit() + "\n(" + data.get(position).getDomain() + ")\n");


        return convertView;
    }


    public static class Holder {
        TextView textView;
        ImageView imageView;
        TextView scoreText;
        TextView commentNumText;
        TextView userAndSub;
        TextView domain;


    }


}

