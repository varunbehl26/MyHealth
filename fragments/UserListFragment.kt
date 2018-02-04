package com.lifeapps.myhealth.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lifeapps.myhealth.R
import com.lifeapps.myhealth.adapter.MyUserRecyclerViewAdapter
import com.lifeapps.myhealth.model.MasterReponse
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.network.RetrofitManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class UserListFragment : Fragment() {
    //    private var mListener: OnListFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        MainPageThread(1).start()
        return view
    }

//
//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is OnListFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        mListener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
//    interface OnListFragmentInteractionListener {
////        fun onListFragmentInteraction(item: DummyItem)
//    }

    companion object {

        fun newInstance(columnCount: Int): UserListFragment {
            val fragment = UserListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var usersList: List<User>

    private inner class MainPageThread internal constructor(requestType: Int) : Thread() {

        override fun run() {
            super.run()

            val topRatedObservable = RetrofitManager.getInstance(activity).userInfo
            topRatedObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : Subscriber<MasterReponse>() {
                        override fun onCompleted() {
                            recyclerView.setAdapter(MyUserRecyclerViewAdapter(usersList))
                        }

                        override fun onError(e: Throwable) {
                            Log.v("Exception", e.message)
                        }

                        override fun onNext(masterReponse: MasterReponse) {
                            usersList = masterReponse.responseData!!

                        }
                    }
                    )
        }

    }
}
