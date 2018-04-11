package com.lifeapps.myhealth.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.lifeapps.myhealth.R
import com.lifeapps.myhealth.adapter.MyUserRecyclerViewAdapter
import com.lifeapps.myhealth.model.MainViewModel
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_user_list.view.*


class UserListFragment : Fragment() {

    companion object {
        fun newInstance(): UserListFragment {
            val fragment = UserListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var usersList: List<User>
    private val viewModel by lazy { getViewModel<MainViewModel>() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_user_list, container, false)
        view?.list?.layoutManager = LinearLayoutManager(context)
        view?.progress_bar?.visibility = VISIBLE

        viewModel.getUsersLiveData().observe(this, Observer {
            it.apply {
                view?.progress_bar?.visibility = GONE;
                view?.list?.visibility = VISIBLE;
                view?.list?.adapter = MyUserRecyclerViewAdapter(usersList)
            }

        })
        return view
    }


//    private inner class MainPageThread internal constructor(requestType: Int) : Thread() {
//
//        override fun run() {
//            super.run()
//
//
//
//            val topRatedObservable = RetrofitManager.getInstance(activity).userInfo
//            topRatedObservable
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(object : Subscriber<MasterReponse>() {
//                        override fun onCompleted() {
//                            view?.progress_bar?.visibility = GONE;
//                            view?.list?.visibility = VISIBLE;
//
//                            view?.list?.adapter = MyUserRecyclerViewAdapter(usersList)
//                        }
//
//                        override fun onError(e: Throwable) {
//                            Log.v("Exception", e.message)
//                        }
//
//                        override fun onNext(masterReponse: MasterReponse) {
//                            usersList = masterReponse.responseData!!
//
//                        }
//                    })
//        }
//
//    }
//}


}
