package jp.furyu.mvvm_test.exception

// 既知の例外はこれでラップする
class AppException(val exceptin: Throwable?): Exception()
