package lishui.example.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.android.exoplayer2.util.Assertions.checkNotNull;

/**
 * Created by lishui.lin on 20-10-22
 */
public class ExoPlayerActivity extends Activity implements PlaybackPreparer {

    private static final String TAG = "ExoVideoPlayer";
    private static final boolean isDebug = BuildConfig.DEBUG;

    // Saved instance state keys.
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";
    public static final String KEY_SOURCE_URI = "source_uri";
    public static final String KEY_SOURCE_TITLE = "source_title";

    private TextView sourceTitleView;
    protected StyledPlayerView playerView; // Exo播放视图层
    private SimpleExoPlayer player;        // Exo播放器
    private List<MediaItem> mediaItems;    // 播放列表

    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;
    private String uri;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_video_player);

        sourceTitleView = findViewById(R.id.source_title);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this::onVisibilityChange);
        playerView.showController();
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();

        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
            uri = savedInstanceState.getString(KEY_SOURCE_URI, "");
            title = savedInstanceState.getString(KEY_SOURCE_TITLE, "");
        } else {
            clearStartPosition();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
        outState.putString(KEY_SOURCE_URI, uri);
        outState.putString(KEY_SOURCE_TITLE, title);
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
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    /**
     * @return Whether initialization was successful.
     */
    private boolean initializePlayer() {
        if (player == null) {

            Intent intent = getIntent();
            mediaItems = createMediaItems(intent);
            if (mediaItems.isEmpty()) {
                finish();
                return false;
            }

            player = new SimpleExoPlayer.Builder(/* context= */ this).build();
            player.addListener(new PlayerEventListener());
            player.addAnalyticsListener(new EventLogger(null));
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

    private List<MediaItem> createMediaItems(Intent intent) {
        if (intent != null) {
            uri = intent.getStringExtra(KEY_SOURCE_URI);
            title = intent.getStringExtra(KEY_SOURCE_TITLE);
        }

        if (isDebug && TextUtils.isEmpty(uri)) {
            uri = "https://vipvideo.szzhangchu.com/cbc0f857a5344abc977e058feab59523/016bc391fb7ec97adf7467840876f052-ld-encrypt-stream.m3u8";
            title = "Debug ExoPlayer";
        }
        if (TextUtils.isEmpty(uri)) {
            Log.d(TAG, "createMediaItems with empty uri...");
            return Collections.emptyList();
        }

        sourceTitleView.setText(title);
        List<MediaItem> mediaItems = new ArrayList<>();
        // Build the media items
        MediaItem sourceItem = MediaItem.fromUri(uri);
        mediaItems.add(sourceItem);

        for (int i = 0; i < mediaItems.size(); i++) {
            MediaItem mediaItem = mediaItems.get(i);

            if (!Util.checkCleartextTrafficPermitted(mediaItem)) {
                showToast(R.string.error_cleartext_not_permitted);
                return Collections.emptyList();
            }
            if (Util.maybeRequestReadExternalStoragePermission(/* activity= */ this, mediaItem)) {
                // The player will be reinitialized if the permission is granted.
                return Collections.emptyList();
            }

            MediaItem.DrmConfiguration drmConfiguration =
                    checkNotNull(mediaItem.playbackProperties).drmConfiguration;
            if (drmConfiguration != null && !FrameworkMediaDrm.isCryptoSchemeSupported(drmConfiguration.uuid)) {
                showToast(R.string.error_drm_unsupported_scheme);
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

    public void onVisibilityChange(int visibility) {
        // Controller visibility changed
        Log.d(TAG, "ExoController onVisibilityChange: " + visibility);
        sourceTitleView.setVisibility(visibility);
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlaybackStateChanged(@Player.State int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                playerView.showController();
            }
        }

        @Override
        public void onPlayerError(@NonNull ExoPlaybackException e) {
            Log.d(TAG, "onPlayerError ExoPlaybackException type=" + e.type);
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            }
        }
    }

    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    protected void clearStartPosition() {
        startAutoPlay = false;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    protected void releasePlayer() {
        if (player != null) {
            updateStartPosition();
            player.release();
            player = null;
            mediaItems = Collections.emptyList();
        }
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        @NonNull
        public Pair<Integer, String> getErrorMessage(@NonNull ExoPlaybackException e) {
            String errorString = getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.codecInfo == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString = getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
                            errorString = getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                        }
                    } else {
                        errorString = getString(R.string.error_instantiating_decoder, decoderInitializationException.codecInfo.name);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}