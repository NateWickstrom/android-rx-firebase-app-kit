package media.pixi.appkit.data.devices

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class GetInstanceIdFuture: Future<InstanceIdResult?>, OnCompleteListener<InstanceIdResult> {

    @Volatile private var result: InstanceIdResult? = null
    @Volatile private var cancelled = false

    private val countDownLatch = CountDownLatch(1)

    override fun get(): InstanceIdResult? {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(this)
        countDownLatch.await()
        return result
    }

    override fun get(timeout: Long, unit: TimeUnit?): InstanceIdResult? {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(this)
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

    override fun onComplete(task: Task<InstanceIdResult>) {
        if (cancelled) return

        if (!task.isSuccessful) {
            Timber.e("getInstanceId failed", task.exception)
            return
        }

        result = task.result
        countDownLatch.countDown()
    }
}