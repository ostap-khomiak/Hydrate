package com.ostapkhomiak.hydrate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SettingsFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().setTheme(R.style.Theme_Hydrate)

        return inflater.inflate(R.layout.fragment_settings, container, false)
        (requireActivity() as MainActivity).setWeight(64.9) // test
    }


}