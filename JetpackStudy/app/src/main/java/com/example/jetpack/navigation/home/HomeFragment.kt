package com.example.jetpack.navigation.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.jetpack.R
import com.example.jetpack.d
import com.example.jetpack.databinding.HomeFragmentBinding
import com.example.jetpack.navigation.dialog.MyDialogFragment
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/*
 onAttach(onAttachFragment) - onCreate - onCreateView - onActivityCreated
 - onStart - onResume - onPause - onStop - onDestroyView - onDestroy - onDetach
 */
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var mViewModel: HomeViewModel

    private lateinit var mBinding: HomeFragmentBinding

    private lateinit var mController: NavController

    private var mViewRoot: ViewGroup? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        d("onAttach")
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        d("onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        d("onCreateView")
        mBinding = if (mViewRoot == null) {
            val attachToParent = false
            val binding: HomeFragmentBinding = DataBindingUtil
                    .inflate(inflater, R.layout.home_fragment, container, attachToParent)
            mViewRoot = binding.root as ViewGroup
            binding.listener = this
            binding
        } else mBinding

        return mViewRoot
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("onViewCreated")

        mController = Navigation.findNavController(mBinding.root)
        mController.navigate(R.id.action_home_to_parent)
    }

    override fun onInflate(activity: Activity?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(activity, attrs, savedInstanceState)
        d("onInflate")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d("onActivityCreated")

        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mViewModel.mTextData.observe(this, Observer<String> {
            mBinding.tvText.text = it
        })
    }

    override fun onStart() {
        super.onStart()
        d("onStart")
    }

    override fun onResume() {
        super.onResume()
        mController = Navigation.findNavController(mBinding.root)
        d("onResume")
    }

    override fun onPause() {
        super.onPause()
        d("onPause")
    }

    override fun onStop() {
        super.onStop()
        d("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        d("onDetach")
    }

    override fun onClick(v: View?) {
        v ?: return

        when(v.id) {
            R.id.btnToParent -> {
                mController.navigate(R.id.action_home_to_parent)
            }
            R.id.btnShowText -> {
                mViewModel.showText()
            }
            else -> {}
        }
    }
}
