package lishui.example.app

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule


/**
 * Created by lishui.lin on 20-10-13
 */
@GlideModule(glideName = "DemoGlide")
class AppDemoGlideModule : AppGlideModule() {

    companion object {
        private const val WALLPAPER_DISK_CACHE_SIZE_BYTES = 30 * 1024 * 1024L
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Default Glide cache size is 250MB so make cache smaller at 30MB.
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context, WALLPAPER_DISK_CACHE_SIZE_BYTES
            )
        )

        // Default # of bitmap pool screens is 4, so reduce to 2 to make room for the additional memory
        // consumed by tiling large images in preview and also the large bitmap consumed by the live
        // wallpaper for daily rotation.
        val calculator = MemorySizeCalculator.Builder(context)
            .setBitmapPoolScreens(2f)
            .setMemoryCacheScreens(1.2f)
            .build()
        builder.setMemorySizeCalculator(calculator)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}