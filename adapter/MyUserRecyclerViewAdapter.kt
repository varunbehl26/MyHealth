package com.lifeapps.myhealth.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lifeapps.myhealth.R
import com.lifeapps.myhealth.databinding.FragmentUserBinding
//import com.lifeapps.myhealth.fragments.UserListFragment.OnListFragmentInteractionListener
import com.lifeapps.myhealth.model.User

class MyUserRecyclerViewAdapter(private val mValues: List<User>) : RecyclerView.Adapter<MyUserRecyclerViewAdapter.MyUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUserViewHolder {
        val binding: FragmentUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_user, parent, false)
        return MyUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyUserViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class MyUserViewHolder(val binding: FragmentUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val user = mValues.get(adapterPosition)
            binding.nameText.text = user.userName
            binding.userTypeText.text = "Gold"
            binding.userSexText.text = "User sex :" + user.userSex

        }

    }
}