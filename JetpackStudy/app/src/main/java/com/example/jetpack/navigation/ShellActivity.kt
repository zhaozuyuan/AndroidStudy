package com.example.jetpack.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.jetpack.R
import com.example.jetpack.d
import com.example.jetpack.room.AbsDatabase
import com.example.jetpack.room.AppDatabase
import com.example.jetpack.room.PersonEntity
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ShellActivity : AppCompatActivity() {

    private lateinit var mModel: ShellModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shell)

        d("---->onCreate")

        //隐藏标题栏
        supportActionBar?.hide()

        //获取model
        mModel = ViewModelProviders.of(this@ShellActivity).get(ShellModel::class.java)

        AppDatabase.getInstance(this)
                .peopleDao()
                .insertPersons(PersonEntity(1, "小明", 12, "篮球"))

        AppDatabase.getInstance(this)
                .peopleDao()
                .queryPerson(1)
                .compose(threadTransformer())
                .subscribe (object : SingleObserver<PersonEntity> {
                    override fun onSuccess(t: PersonEntity) {
                        Log.d("TAGG", t.toString())
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Log.d("TAGG", "not found where id == 1")
                    }
                })
    }

    private fun <T> threadTransformer() = SingleTransformer<T, T> {
        it.subscribeOn(Schedulers.computation())
    }

    override fun onStart() {
        super.onStart()
        d("---->onStart")
    }

    override fun onResume() {
        super.onResume()
        d("---->onResume")
    }
}
