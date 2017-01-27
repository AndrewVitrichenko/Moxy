package com.arellomobile.mvp.sample.kotlin


import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag

class MainActivity : MvpAppCompatActivity(), DialogView<String, Int> {

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var dialogPresenter: DialogPresenter<String>

    var alertDialog: AlertDialog? = null

    @ProvidePresenterTag(presenterClass = DialogPresenter::class, type = PresenterType.GLOBAL)
    fun provideDialogPresenterTag(): String = "Hello"

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideDialogPresenter() = DialogPresenter<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.activity_main).setOnClickListener { dialogPresenter.onShowDialogClick("123") }
    }

    override fun showDialog(title: String, code: Int) {
        alertDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("$code")
                .setOnDismissListener { dialogPresenter.onHideDialog() }
                .show()
    }

    override fun hideDialog() {
        alertDialog?.setOnDismissListener {  }
        alertDialog?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()

        hideDialog()
    }
}