package com.example.rxbus.regions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxbus.R
import com.example.rxbus.reactivex.RxBus
import com.example.rxbus.reactivex.RxEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_regions.*

class RegionsActivity : AppCompatActivity() {

    lateinit var regionsAdapter: RegionsAdapter
    lateinit var regionsListReachedDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_regions)

        regionsAdapter = RegionsAdapter(resources.getStringArray(R.array.regions).toMutableList())
        regionsRv.layoutManager = LinearLayoutManager(this)
        regionsRv.adapter = regionsAdapter

        regionsListReachedDisposable =
            RxBus.listen(RxEvent.EventBottomOfRegionsListReached::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    regionsAdapter.addItems(resources.getStringArray(R.array.regions).toMutableList())
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!regionsListReachedDisposable.isDisposed) {
            regionsListReachedDisposable.dispose()
        }
    }
}
