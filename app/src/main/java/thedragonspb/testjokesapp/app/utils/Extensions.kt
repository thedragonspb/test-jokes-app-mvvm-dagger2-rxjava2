package thedragonspb.testjokesapp.app.utils

import androidx.fragment.app.Fragment
import thedragonspb.testjokesapp.app.App
import thedragonspb.testjokesapp.app.di.AppComponent

fun Fragment.getApp(): App =
    (requireActivity().application as App)

fun Fragment.getAppComponent(): AppComponent =
    (requireActivity().application as App).appComponent