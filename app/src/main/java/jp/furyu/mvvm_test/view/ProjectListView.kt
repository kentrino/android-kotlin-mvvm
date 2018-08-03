package jp.furyu.mvvm_test.view

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.databinding.DataBindingUtil.inflate
import jp.furyu.mvvm_test.BR
import jp.furyu.mvvm_test.R
import org.w3c.dom.Text

class ProjectListView(val inflater: LayoutInflater, var listner: Listener) {
    private var eventHandler: EventHandler
    private var view: ActivityMainBinding
    init {
        eventHandler = EventHandler(listner)
        // android.view.View.inflateではない
        view = inflate(inflater, R.layout.activity_main, null, false)

    }

    class Model: BaseObservable() {
        @Bindable var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }
    }

    interface Listener {
        fun getProjectList(newValue: String)
    }

    class EventHandler(val listener: Listener) {
        fun queryChanged(): TextWatcher {
            return object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    listener.getProjectList(p1.toString())
                }
            }
        }
    }
}
