package np.com.riteshakya.motionlayoutdemo

import androidx.annotation.StringDef

class MotionWrapper(
    @MotionType val motionState: String, val progress: Float,
    val isStart: Boolean = false,
    val set: Set
) {
    companion object {
        @StringDef(MOTION_STARTED, MOTION_COMPLETED, MOTION_PROGRESS)
        annotation class MotionType

        const val MOTION_STARTED = "started"
        const val MOTION_COMPLETED = "completed"
        const val MOTION_PROGRESS = "progress"
        const val MOTION_TRIGGER = "trigger"
    }

    class Set(val startId: Int, val endId: Int)

    override fun toString(): String {
        return "MotionWrapper(motionState='$motionState', progress=$progress, isStart=$isStart)"
    }

}
