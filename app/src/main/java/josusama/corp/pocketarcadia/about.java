package josusama.corp.pocketarcadia;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

public class about extends Fragment {
    View view;
    private TextView about, school, section, lead, leader, assist, assistant , assistants, assistantss, prof, proffesor, sfx, sfxx;
    public about() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_about,container,false);

        school = (TextView)view.findViewById(R.id.groups);
        about = (TextView)view.findViewById(R.id.about);
        section = (TextView)view.findViewById(R.id.group);
        lead = (TextView)view.findViewById(R.id.lead);
        leader = (TextView)view.findViewById(R.id.leader);
        assist = (TextView)view.findViewById(R.id.assisst);
        assistant = (TextView)view.findViewById(R.id.assistant);
        assistants = (TextView)view.findViewById(R.id.assistantS);
        assistantss = (TextView)view.findViewById(R.id.assistantSS);
        prof = (TextView)view.findViewById(R.id.prof);
        proffesor = (TextView)view.findViewById(R.id.proffesor);
        sfx = (TextView)view.findViewById(R.id.sfx);
        sfxx = (TextView)view.findViewById(R.id.sfxx);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/DisposableDroidBB.ttf");

        school.setTypeface(typeface);
        about.setTypeface(typeface);
        section.setTypeface(typeface);
        lead.setTypeface(typeface);
        leader.setTypeface(typeface);
        assist.setTypeface(typeface);
        assistant.setTypeface(typeface);
        assistants.setTypeface(typeface);
        assistantss.setTypeface(typeface);
        prof.setTypeface(typeface);
        proffesor.setTypeface(typeface);
        sfx.setTypeface(typeface);
        sfxx.setTypeface(typeface);



        return view;



    }
}