package lishui.example.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import lishui.example.app.base.BaseFragment
import lishui.example.app.viewmodel.MainViewModel
import lishui.example.player.ExoVideoPlayer

class MainFragment : BaseFragment() {

    companion object {
        private const val TAG = "MainFragment"
    }

    private lateinit var messagingItemButton: Button
    private lateinit var cameraItemButton: Button
    private lateinit var videoItemButton: Button

    private lateinit var mViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        //LogUtils.d(TAG, fromContext<MainActivity>(requireContext()).resources.configuration.toString())
    }

    private fun initViews(rootView: View) {
        messagingItemButton = rootView.findViewById(R.id.btn_messaging_fragment)
        cameraItemButton = rootView.findViewById(R.id.btn_camera_fragment)
        videoItemButton = rootView.findViewById(R.id.btn_video_fragment)

        messagingItemButton.setOnClickListener {
            with(findNavController(this)) {
                if (this.currentDestination?.id == R.id.nav_messaging_fragment) return@with
                navigate(R.id.action_mainFragment_to_messagingFragment)
                //popBackStack()
            }
        }

        cameraItemButton.setOnClickListener {
            with(findNavController(this)) {
                if (this.currentDestination?.id == R.id.nav_camera_fragment) return@with
                navigate(R.id.action_mainFragment_to_cameraFragment)
            }
        }

        videoItemButton.setOnClickListener {
            val intent = Intent(this@MainFragment.requireActivity(), ExoVideoPlayer::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}