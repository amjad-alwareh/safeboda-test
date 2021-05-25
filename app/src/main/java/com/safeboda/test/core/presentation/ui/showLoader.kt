package com.safeboda.test.core.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.safeboda.test.R
import com.safeboda.test.core.utils.ScreeUtils

private val loadersTags = HashMap<String, LoaderDialogFragment>()

/**
 * By default, use [showLoader] without tag,
 * in case of concurrent API calls, use showLoader/dismissLoader with tag
 */
fun FragmentActivity.showLoader(tag: String = this::class.java.simpleName){
    LoaderDialogFragment().apply {
        loadersTags[tag] = this
        show(this@showLoader, tag)
    }
}

/**
 * By default, use [dismissLoader] without tag,
 * in case of concurrent API calls, use showLoader/dismissLoader with tag
 */
fun FragmentActivity.dismissLoader(tag: String = this::class.java.simpleName) {
    loadersTags[tag]?.dismissAllowingStateLoss()
    loadersTags.remove(tag)
}

/**
 * By default, use [showLoader] without tag,
 * in case of concurrent API calls, use showLoader/dismissLoader with tag
 */
fun Fragment.showLoader(tag: String = this::class.java.simpleName){
    requireActivity().showLoader(tag)
}

/**
 * By default, use dismissLoader without tag,
 * in case of concurrent API calls, use showLoader/dismissLoader with tag
 */
fun Fragment.dismissLoader(tag: String = this::class.java.simpleName){
    requireActivity().dismissLoader(tag)
}
class LoaderDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_dialog_loader, container, false)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (0.9 * ScreeUtils.calculateScreen(activity)).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
    }

    fun show(activity: FragmentActivity, tag: String = this::class.java.simpleName) {
        show(activity.supportFragmentManager, tag)
    }

}