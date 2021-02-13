package josusama.corp.pocketarcadia;

import android.content.Intent;
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
import android.widget.VideoView;

public class Arcadethree extends Fragment {
    View view;
    VideoView videoView;
    public Arcadethree() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_arcadethree,container,false);

        //Video
        videoView = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.arcadethree);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setZOrderOnTop(true);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.setZOrderOnTop(true);
                videoView.start(); //need to make transition seamless.
            }
        });

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.arcadeone);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Speedboy.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //Video
        videoView = (VideoView) view.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.arcadethree);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setZOrderOnTop(true);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.setZOrderOnTop(true);
                videoView.start(); //need to make transition seamless.
            }
        });

    }
}
