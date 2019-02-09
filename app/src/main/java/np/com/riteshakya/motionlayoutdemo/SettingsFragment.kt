package np.com.riteshakya.motionlayoutdemo


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                mainActivity.settingsFragmentMotion(
                    MotionWrapper(
                        MotionWrapper.MOTION_TRIGGER,
                        p3,
                        set = MotionWrapper.Set(settingsMotionLayout.getStartId(), settingsMotionLayout.getEndId())
                    )
                )
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                mainActivity.settingsFragmentMotion(
                    MotionWrapper(
                        MotionWrapper.MOTION_STARTED,
                        0f,
                        set = MotionWrapper.Set(settingsMotionLayout.getStartId(), settingsMotionLayout.getEndId())
                    )
                )
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                mainActivity.settingsFragmentMotion(
                    MotionWrapper(
                        MotionWrapper.MOTION_PROGRESS,
                        p3,
                        set = MotionWrapper.Set(settingsMotionLayout.getStartId(), settingsMotionLayout.getEndId())
                    )
                )
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                mainActivity.settingsFragmentMotion(
                    MotionWrapper(
                        MotionWrapper.MOTION_COMPLETED,
                        100f,
                        p1 == settingsMotionLayout.getStartId(),
                        set = MotionWrapper.Set(settingsMotionLayout.getStartId(), settingsMotionLayout.getEndId())
                    )
                )
            }
        })
    }

    fun startMotion() {
        settingsMotionLayout.transitionToEnd()
    }

    fun revertMotion() {
        settingsMotionLayout.transitionToStart()
    }
}
