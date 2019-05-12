package com.unsplash.photo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.unsplash.photo.R
import com.unsplash.photo.base.BaseBottomSheetDialogFragment

class AddCollectionFragment : BaseBottomSheetDialogFragment() {

    override fun layoutRes(): Int {
        return R.layout.fragment_add_collections
    }

    override fun onFragmentCreatedWithUser(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(fragmentManager: FragmentManager) =
            AddCollectionFragment().show(fragmentManager, AddCollectionFragment::class.java.name)
    }
}