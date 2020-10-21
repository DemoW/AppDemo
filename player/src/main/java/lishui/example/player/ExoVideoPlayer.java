package lishui.example.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;

public class ExoVideoPlayer extends Activity implements StyledPlayerControlView.VisibilityListener, PlaybackPreparer, PlayerControlView.VisibilityListener {

    // Saved instance state keys.
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    protected StyledPlayerView playerView;
    private SimpleExoPlayer player;
    private List<MediaItem> mediaItems;

    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_video_player);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);
        playerView.showController();
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            DefaultTrackSelector.ParametersBuilder builder =
                    new DefaultTrackSelector.ParametersBuilder(/* context= */ this);
            trackSelectorParameters = builder.build();
            clearStartPosition();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        releasePlayer();
        clearStartPosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.onPause();
        }
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * @return Whether initialization was successful.
     */
    private boolean initializePlayer() {
        if (player == null) {
            mediaItems = createMediaItems();
            if (mediaItems.isEmpty()) {
                return false;
            }

            trackSelector = new DefaultTrackSelector(/* context= */ this);
            trackSelector.setParameters(trackSelectorParameters);
            player = new SimpleExoPlayer.Builder(/* context= */ this)
                    .setTrackSelector(trackSelector)
                    .build();
            player.addListener(new PlayerEventListener());
            player.addAnalyticsListener(new EventLogger(trackSelector));
            player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
            player.setPlayWhenReady(startAutoPlay);
            playerView.setPlayer(player);
            playerView.setPlaybackPreparer(this);
        }

        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startWindow, startPosition);
        }
        player.setMediaItems(mediaItems, /* resetPosition= */ !haveStartPosition);
        player.prepare();
        return true;
    }

    private List<MediaItem> createMediaItems() {
        List<MediaItem> mediaItems = new ArrayList<>();
        // Build the media items.
        MediaItem firstItem = MediaItem.fromUri("https://vipvideo.szzhangchu.com/4f0d347e8d5b42b4bff635b00296f2fa/a4cf587676b08944a28d189d40df7d21-ld-encrypt-stream.m3u8");
        MediaItem secondItem = MediaItem.fromUri("https://vipvideo.szzhangchu.com/1d6ecc4802684fa0aa52f9ab0cf66bf7/efcfa05a106968e4158451ef1d8680ad-ld-encrypt-stream.m3u8");
        mediaItems.add(firstItem);
        mediaItems.add(secondItem);
        for (int i = 0; i < mediaItems.size(); i++) {
            MediaItem mediaItem = mediaItems.get(i);

            if (!Util.checkCleartextTrafficPermitted(mediaItem)) {
                //showToast(R.string.error_cleartext_not_permitted);
                return Collections.emptyList();
            }
            if (Util.maybeRequestReadExternalStoragePermission(/* activity= */ this, mediaItem)) {
                // The player will be reinitialized if the permission is granted.
                return Collections.emptyList();
            }

            MediaItem.DrmConfiguration drmConfiguration =
                    checkNotNull(mediaItem.playbackProperties).drmConfiguration;
            if (drmConfiguration != null && !FrameworkMediaDrm.isCryptoSchemeSupported(drmConfiguration.uuid)) {
                //showToast(R.string.error_drm_unsupported_scheme);
                finish();
                return Collections.emptyList();
            }
        }
        return mediaItems;
    }

    @Override
    public void preparePlayback() {
        player.prepare();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlaybackStateChanged(@Player.State int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                //showControls();
            }
            //updateButtonVisibility();
        }

        @Override
        public void onPlayerError(@NonNull ExoPlaybackException e) {
//            if (isBehindLiveWindow(e)) {
//                clearStartPosition();
//                initializePlayer();
//            } else {
//                updateButtonVisibility();
//                showControls();
//            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(
                @NonNull TrackGroupArray trackGroups, @NonNull TrackSelectionArray trackSelections) {
//            updateButtonVisibility();
//            if (trackGroups != lastSeenTrackGroupArray) {
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast(R.string.error_unsupported_video);
//                    }
//                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
//                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast(R.string.error_unsupported_audio);
//                    }
//                }
//                lastSeenTrackGroupArray = trackGroups;
//            }
        }
    }

    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    protected void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    protected void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            player.release();
            player = null;
            mediaItems = Collections.emptyList();
            trackSelector = null;
        }
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        @NonNull
        public Pair<Integer, String> getErrorMessage(@NonNull ExoPlaybackException e) {
            String errorString = "error demo video";
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.codecInfo == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            //errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
//                            errorString =
//                                    getString(
//                                            R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
//                            errorString =
//                                    getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                        }
                    } else {
//                        errorString =
//                                getString(
//                                        R.string.error_instantiating_decoder,
//                                        decoderInitializationException.codecInfo.name);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}