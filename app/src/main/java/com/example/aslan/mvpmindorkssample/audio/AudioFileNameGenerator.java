package com.example.aslan.mvpmindorkssample.audio;

import android.net.Uri;

import com.danikula.videocache.file.FileNameGenerator;

/**
 * Created by MAO on 8/1/2017.
 */

public class AudioFileNameGenerator implements FileNameGenerator {
    @Override
    public String generate(String url) {
        Uri uri = Uri.parse(url);
        return uri.getLastPathSegment();
    }
}
