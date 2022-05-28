package dev.extrreme.extrremebot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MusicManager extends AudioEventAdapter {

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;

    private final List<AudioTrack> played;
    private final AudioPlayerSendHandler sendHandler;

    private boolean isRepeat = false;

    public MusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.queue = new LinkedBlockingQueue<>();
        this.played = new LinkedList<>();
        this.audioPlayer.addListener(this);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            next();
        }
    }

    public boolean queue(AudioTrack track) {
        if (!this.audioPlayer.startTrack(track, true)) {
            return this.queue.offer(track);
        } else {
            played.add(track);
            return true;
        }
    }

    public AudioTrack getCurrentTrack() {
        return this.audioPlayer.getPlayingTrack();
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return new LinkedBlockingQueue<>(this.queue);
    }

    public void next() {
        AudioTrack track = null;
        if (!this.isRepeat || this.played.isEmpty()) {
            track = this.queue.poll();
            this.played.add(track);
        } else {
            AudioTrack trackTemp = this.played.get(played.size() - 1);
            if (trackTemp != null) {
                track = trackTemp.makeClone();
            }
        }

        if (track != null) {
            this.audioPlayer.startTrack(track, false);
        }
    }

    public void clear() {
         this.queue.clear();
    }

    public void setRepeat(boolean repeat) {
        this.isRepeat = repeat;
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public List<AudioTrack> getPlayed() {
        return new ArrayList<>(this.played);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return this.sendHandler;
    }
}
