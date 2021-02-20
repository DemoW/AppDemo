package lishui.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import lishui.example.app.base.BaseFragment
import lishui.example.app.databinding.FragmentMainLayoutBinding

class MainFragment : BaseFragment() {

    companion object {
        private const val TAG = "MainFragment"
    }

    private lateinit var binding: FragmentMainLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        //LogUtils.d(TAG, fromContext<MainActivity>(requireContext()).resources.configuration.toString())
    }

    private fun initViews() {
        binding.btnMessagingFragment.setOnClickListener {
            with(findNavController(this)) {
                if (this.currentDestination?.id == R.id.nav_messaging_fragment) return@with
                navigate(R.id.action_mainFragment_to_messagingFragment)
                //popBackStack()
            }
        }
    }
}