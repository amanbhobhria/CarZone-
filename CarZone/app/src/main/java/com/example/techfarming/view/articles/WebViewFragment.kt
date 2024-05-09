package com.example.techfarming.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.techfarming.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WebViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val maruti="https://www.cardekho.com/maruti-suzuki-cars"
        val hyundai="https://www.carwale.com/hyundai-cars/"
        val toyota="https://www.carwale.com/toyota-cars/"
        val mahindra="https://www.carwale.com/mahindra-cars/"
        val tata="https://www.carwale.com/tata-cars/"




        val bundle = arguments

        val receivedInt = bundle?.getInt("intKey")


        val view= inflater.inflate(R.layout.fragment_web_view, container, false)

        // Initialize the WebView
        webView = view.findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Load the desired URL
        if(receivedInt==1)
        {
            webView.loadUrl(maruti)

        }

        else if(receivedInt==2) {
            webView.loadUrl(hyundai)
        }

        else if(receivedInt==3) {
            webView.loadUrl(toyota)
        }
        else if(receivedInt==4) {
            webView.loadUrl(tata)
        }
        else if(receivedInt==5) {
            webView.loadUrl(mahindra)
        }
        return  view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}