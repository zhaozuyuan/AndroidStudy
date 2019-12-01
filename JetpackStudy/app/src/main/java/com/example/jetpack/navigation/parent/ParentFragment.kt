package com.example.jetpack.navigation.parent

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.jetpack.R
import kotlinx.android.synthetic.main.parent_fragment.*

class ParentFragment : Fragment() {

    private lateinit var viewModel: ParentViewModel

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         mView = inflater.inflate(R.layout.parent_fragment, container, false)

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ParentViewModel::class.java)

        btnParentToChild.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_parent_to_child))
    }

}
