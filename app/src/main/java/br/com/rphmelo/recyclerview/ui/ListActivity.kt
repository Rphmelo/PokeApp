package br.com.rphmelo.recyclerview.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import br.com.rphmelo.recyclerview.R
import br.com.rphmelo.recyclerview.api.getPokemonApi
import br.com.rphmelo.recyclerview.model.Pokemon
import br.com.rphmelo.recyclerview.model.PokemonResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        loadData()
    }

    private fun showInTheList(pokemons: List<Pokemon>) {
        rvPokemons.adapter = ListPokemonAdapter(this, pokemons, {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        })
        rvPokemons.layoutManager = LinearLayoutManager(this)
    }

    private fun showError(error: String){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun loadData() {
        getPokemonApi()
                .search(150)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<PokemonResponse>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(t: PokemonResponse) {
                        showInTheList(t.pokemons)
                    }

                    override fun onError(e: Throwable) {
                        showError(e.message!!)
                    }

                })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
