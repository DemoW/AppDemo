package lishui.example.app.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import lishui.example.app.R
import lishui.example.app.base.BaseFragment
import lishui.example.app.FILE_PROVIDER_AUTHORITY
import lishui.example.app.databinding.FragmentCameraLayoutBinding
import lishui.example.app.viewmodel.CameraViewModel
import lishui.example.app.wrapper.PackageManagerWrapper
import java.io.File
import java.io.IOException

class CameraFragment : BaseFragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val TEMP_PIC_FILE_NAME = "CAPTURE_CAMERA.jpg"
    }

    private lateinit var captureView: ImageView
    private lateinit var viewModel: CameraViewModel

    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        captureView = view.findViewById(R.id.capture_picture)
        captureView.setOnLongClickListener { dispatchTakePictureIntent() }

        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        with(File(storageDir,
            TEMP_PIC_FILE_NAME
        )) {
            if (exists()) {
                Glide.with(this@CameraFragment)
                    .applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                    .load(absolutePath)
                    .into(captureView)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //val imageBitmap = data?.extras?.get("data") as Bitmap
            Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .load(currentPhotoPath)
                .into(captureView)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir,
            TEMP_PIC_FILE_NAME
        ).also { currentPhotoPath = it.absolutePath }
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            currentPhotoPath = absolutePath
//        }
    }

    private fun dispatchTakePictureIntent(): Boolean {
        if (PackageManagerWrapper.get().hasSystemFeature()) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            FILE_PROVIDER_AUTHORITY,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent,
                            REQUEST_IMAGE_CAPTURE
                        )
                    }

                }
            }
            return true
        }
        return false
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            context?.sendBroadcast(mediaScanIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(requireContext()).clearMemory()
    }
}