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

public class GuildMusicManager extends AudioEventAdapter {

    private final AudioPlayer audioPlayer;
    private final BlockingQueue<AudioTrack> queue;

    private final List<AudioTrack> played;
    private final AudioPlayerSendHandler sendHandler;

    private boolean isRepeat = false;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.queue = new LinkedBlockingQueue<>();
        this.played = new LinkedList<>();
        this.audioPlayer.addListener(this);
        this.sendHandler = new AudioPlayerSendHandler(audioPlayer);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            next();
        }
    }

    public boolean queue(AudioTrack track) {
        if (!audioPlayer.startTrack(track, true)) {
            return queue.offer(track);
        } else {
            played.add(track);
            return true;
        }
    }

    public AudioTrack getCurrentTrack() {
        return audioPlayer.getPlayingTrack();
    }

    public BlockingQueue<AudioTrack> getQueue() {
        return new LinkedBlockingQueue<>(queue);
    }

    public void next() {
        AudioTrack track = null;
        if (!isRepeat || played.isEmpty()) {
            track = queue.poll();
            played.add(track);
        } else {
            AudioTrack trackTemp = played.get(played.size() - 1);
            if (trackTemp != null) {
                track = trackTemp.makeClone();
            }
        }

        if (track != null) {
            audioPlayer.startTrack(track, false);
        }
    }

    public void clear() {
         queue.clear();
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public List<AudioTrack> getPlayed() {
        return new ArrayList<>(played);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }
}
