package lishui.example.app.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import lishui.example.app.messaging.ConversationListItemView

/**
 * Created by lishui.lin on 20-10-13
 */
class SnackDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val argStr = arguments?.getString(ConversationListItemView.TAG, "")
        return SnackDialog.showTestDialog(context, argStr)
    }
}