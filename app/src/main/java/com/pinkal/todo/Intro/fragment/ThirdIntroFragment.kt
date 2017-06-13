package com.pinkal.todo.Intro.fragment

/**
 * Created by Pinkal on 13/6/17.
 */
class ThirdIntroFragment : android.support.v4.app.Fragment() {

    companion object {
        fun newInstance(pageNum: Int): com.pinkal.todo.Intro.fragment.ThirdIntroFragment {

            val fragmentThird = com.pinkal.todo.Intro.fragment.ThirdIntroFragment()

            val bundle = android.os.Bundle()
            bundle.putInt(com.pinkal.todo.utils.KEY_PAGE_NUMBER, pageNum)

            fragmentThird.arguments = bundle

            return fragmentThird
        }
    }

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View? {
        val view = inflater!!.inflate(com.pinkal.todo.R.layout.fragment_third_intro, container, false)

        initialize(view)
        return view
    }

    private fun initialize(view: android.view.View?) {

    }
}