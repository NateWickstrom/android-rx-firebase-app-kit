package media.pixi.appkit.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Thread.sleep
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class GetUserFuture: Future<FirebaseUser?> {

    @Volatile private var result: FirebaseUser? = null
    @Volatile private var cancelled = false

    private val countDownLatch = CountDownLatch(1)
    private val auth = FirebaseAuth.getInstance()

    override fun get(): FirebaseUser? {
        thread(true) { getFirebaseUser() }
        countDownLatch.await()
        return result
    }

    override fun get(timeout: Long, unit: TimeUnit?): FirebaseUser? {
        thread { getFirebaseUser() }.start()
        countDownLatch.await(timeout, unit)
        return result
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        if (isDone) {
            return false
        } else {
            countDownLatch.countDown()
            cancelled = true
            return !isDone
        }
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun isDone(): Boolean {
        return countDownLatch.count == 0L
    }

    private fun getFirebaseUser() {
        result = auth.currentUser

        while (result == null) {
            sleep(50)
            result = auth.currentUser
        }
        countDownLatch.countDown()
    }

}