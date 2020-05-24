package com.example.huda_haryana.mybusiness

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.FragmentMybusinessBinding
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

const val PIC_IMAGE = 1

class BusinessFrag : Fragment() {
    var accnt: GoogleSignInAccount? = null
    lateinit var binding: FragmentMybusinessBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mybusiness, container, false)
        binding.addLogo.setOnClickListener {
            val intent = Intent(context, EditDetails::class.java)
            startActivity(intent)
        }
        accnt = GoogleSignIn.getLastSignedInAccount(FacebookSdk.getApplicationContext())
        val storage_mref = FirebaseStorage.getInstance().getReference("Logos").child(accnt?.id!!)
        storage_mref.downloadUrl.addOnSuccessListener {
            binding.addLogo.visibility = View.GONE
            binding.businessLogoImage.visibility = View.VISIBLE
            binding.progressLogo.visibility=View.VISIBLE
            Glide.with(requireContext())
                    .load(it)
                    .centerCrop()
                    .into(binding.businessLogoImage)
            binding.progressLogo.visibility=View.GONE
        }
        binding.editDetails.setOnClickListener {
            val intent = Intent(context, EditDetails::class.java)
            startActivity(intent)
        }
        binding.notes.setOnClickListener {
            val intent = Intent(context, NotesActivity::class.java)
            startActivity(intent)
        }
        binding.businessLogoImage.setOnClickListener {
            load_image()
        }
        val mref = FirebaseDatabase.getInstance().getReference("User").child(accnt?.id!!).child("UserData")
        mref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val data = p0.getValue(UserData::class.java)!!
                    binding.businessName.text = data.businessname
                    binding.addressBusiness.text = data.address
                    binding.moreBusiness.text = data.moredetails
                    binding.emailBusiness.text = data.email
                    binding.openingBusiness.text = data.opening
                    binding.descBusiness.text = data.description
                    binding.phoneBusiness.text = data.phone
                    binding.websiteBusiness.text = data.website
                    binding.whatsappBusiness.text = data.whatsapp
                }
            }

        })

        val first_dialog = Dialog(requireContext())
        val email_dialog = Dialog(requireContext())
        val phone_dia = Dialog(requireContext())
        phone_dia.setContentView(R.layout.business_phone_dia)
        email_dialog.setContentView(R.layout.business_email_dia)
        first_dialog.setContentView(R.layout.business_first_dialog)
        binding.sendBusiness.setOnClickListener {
            first_dialog.show()
        }
        first_dialog.findViewById<TextView>(R.id.email_dialog).setOnClickListener {
            first_dialog.dismiss()
            email_dialog.show()
        }
        first_dialog.findViewById<TextView>(R.id.phone_dialog).setOnClickListener {
            first_dialog.dismiss()
            phone_dia.show()
        }
        email_dialog.findViewById<Button>(R.id.second_dialog_ok).setOnClickListener {
            val email = email_dialog.findViewById<EditText>(R.id.entered_txt).text.toString()
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf(email)
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.putExtra(Intent.EXTRA_SUBJECT, binding.businessName.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, binding.businessName.text.toString() + "\n" + binding.descBusiness.text.toString() + "\n" + binding.websiteBusiness.text.toString() + "\n" + binding.addressBusiness.text.toString() + "\n" + binding.openingBusiness.text.toString() + "\n" + binding.phoneBusiness.text.toString() + "\n" + binding.whatsappBusiness.text.toString() + "\n" + binding.emailBusiness.text.toString() + "\n" + binding.moreBusiness.text.toString())
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }
        phone_dia.findViewById<Button>(R.id.phone_dialog_ok).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone_dia.findViewById<EditText>(R.id.entered_phone).text.toString(), null))
            intent.putExtra("sms_body", binding.businessName.text.toString() + "\n" + binding.descBusiness.text.toString() + "\n" + binding.websiteBusiness.text.toString() + "\n" + binding.addressBusiness.text.toString() + "\n" + binding.openingBusiness.text.toString() + "\n" + binding.phoneBusiness.text.toString() + "\n" + binding.whatsappBusiness.text.toString() + "\n" + binding.emailBusiness.text.toString() + "\n" + binding.moreBusiness.text.toString())
            startActivity(intent)
        }
        binding.addLogo.setOnClickListener {
            load_image()
        }

        return binding.root
    }

    fun load_image() {
        val gallery = Intent()
        gallery.setType("image/*")
        gallery.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PIC_IMAGE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PIC_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            binding.progressLogo.visibility=View.VISIBLE
            val storage_ref = FirebaseStorage.getInstance().getReference("Logos").child(accnt?.id!!)
            val imageUri = data.data!!
            val imagebit = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
            val baos = ByteArrayOutputStream()
            imagebit.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val databaos = baos.toByteArray()
            val uploadTask = storage_ref.putBytes(databaos)
            uploadTask.addOnSuccessListener {
                Toast.makeText(context, "Image Stored", Toast.LENGTH_SHORT).show()
                storage_ref.downloadUrl.addOnSuccessListener {
                    Glide.with(requireContext())
                            .load(it)
                            .centerCrop()
                            .into(binding.businessLogoImage)
                    binding.progressLogo.visibility=View.GONE
                }
            }
            uploadTask.addOnFailureListener {
                binding.progressLogo.visibility=View.GONE
                Toast.makeText(context, "Failure! Please try again.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
