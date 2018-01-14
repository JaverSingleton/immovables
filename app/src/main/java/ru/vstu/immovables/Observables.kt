package ru.vstu.immovables

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.completableOnNext(block: (T) -> Completable) =
        flatMap { block(it).andThen(Observable.just(it)) }

fun <T, R> Observable<T>.singleMap(block: (T) -> Single<R>) =
        flatMap { block(it).toObservable() }

fun <T> T?.toObservable() =
        if (this == null) {
            Observable.never()
        } else {
            Observable.just(this)
        }

fun <T> fromListener(listener: ((T) -> Unit) -> Unit) =
        Observable.create<T> { emitter ->
            listener { value -> emitter.onNext(value) }
        }