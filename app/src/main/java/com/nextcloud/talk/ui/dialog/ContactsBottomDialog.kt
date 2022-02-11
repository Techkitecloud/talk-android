package com.nextcloud.talk.ui.dialog

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import autodagger.AutoInjector
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nextcloud.talk.R
import com.nextcloud.talk.application.NextcloudTalkApplication
import com.nextcloud.talk.controllers.bottomsheet.EntryMenuController
import com.nextcloud.talk.databinding.DialogBottomContactsBinding

@AutoInjector(NextcloudTalkApplication::class)
class ContactsBottomDialog(
    val activity: Activity,
    val bundle: Bundle
) : BottomSheetDialog(activity) {

    private var dialogRouter: Router? = null

    private lateinit var binding: DialogBottomContactsBinding

    init {
        NextcloudTalkApplication.sharedApplication?.componentApplication?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogBottomContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        executeEntryMenuController(bundle)
    }

    private fun executeEntryMenuController(bundle : Bundle) {
        dialogRouter = Conductor.attachRouter(activity, binding.root, null)

        dialogRouter!!.pushController(
            RouterTransaction.with(EntryMenuController(bundle))
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler())
        )
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = findViewById<View>(R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    companion object {
        private const val TAG = "ContactsBottomDialog"
    }
}