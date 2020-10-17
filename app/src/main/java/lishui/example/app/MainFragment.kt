package lishui.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import lishui.example.app.base.BaseFragment
import lishui.example.app.viewmodel.MainViewModel

class MainFragment : BaseFragment() {

    private lateinit var messagingItemButton: Button
    private lateinit var cameraItemButton: Button

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
    }

    private fun initViews(rootView: View) {
        messagingItemButton = rootView.findViewById(R.id.btn_messaging_fragment)
        cameraItemButton = rootView.findViewById(R.id.btn_camera_fragment)

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}