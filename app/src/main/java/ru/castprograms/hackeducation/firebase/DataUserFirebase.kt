package ru.castprograms.hackeducation.firebase

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore
import ru.castprograms.hackeducation.networkinterface.DataUserInterface
import ru.castprograms.hackeducation.tools.Resource
import ru.castprograms.hackeducation.tools.Teacher

class DataUserFirebase(private val firebaseFirestore: FirebaseFirestore) : DataUserInterface {
    override val currentTeacher = MutableLiveData<Resource<Teacher>>()

    override fun getTeacher(idTeacher: String): MutableLiveData<Resource<Teacher>> {
        val mutableLiveData = MutableLiveData<Resource<Teacher>>(Resource.Loading())
        firebaseFirestore.collection(teachers_tag)
            .document(idTeacher).addSnapshotListener { value, error ->
                if (value != null)
                    value.toObject(Teacher::class.java)?.let {
                        mutableLiveData.postValue(Resource.Success(it))
                    }
                else
                    mutableLiveData.postValue(Resource.Error(error?.message.toString()))
            }

        return mutableLiveData
    }

    override fun getAllTeachers(): MutableLiveData<Resource<List<Teacher>>> {
        val mutableLiveData = MutableLiveData<Resource<List<Teacher>>>(Resource.Loading())
        firebaseFirestore.collection(teachers_tag).addSnapshotListener { value, error ->
            if (value != null)
                mutableLiveData.postValue(Resource.Success(value.toObjects(Teacher::class.java)))
            else
                mutableLiveData.postValue(Resource.Error(error?.message.toString()))
        }
        return mutableLiveData
    }

    override fun addTeacher(
        idTeacher: String,
        teacher: Teacher
    ): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        firebaseFirestore.collection(teachers_tag).document(idTeacher)
            .set(teacher).addOnCompleteListener {
                if (it.isSuccessful)
                    mutableLiveData.postValue(Resource.Success("Success"))
                else
                    mutableLiveData.postValue(Resource.Error(it.exception?.message.toString()))
            }

        return mutableLiveData
    }

    override fun loadTeacher(idTeacher: String) {
        if (currentTeacher.value !is Resource.Success)
            firebaseFirestore.collection(teachers_tag)
                .document(idTeacher).addSnapshotListener { value, error ->
                    if (value != null && value.data != null)
                        value.toObject(Teacher::class.java)?.let {
                            currentTeacher.postValue(Resource.Success(it))
                        }
                    else
                        currentTeacher.postValue(Resource.Error(error?.message.toString()))
                }
    }

    private var googleAccount: GoogleSignInAccount? = null
    fun getGoogleSignInAccount(context: Context): GoogleSignInAccount? {
        if (googleAccount == null)
            googleAccount = GoogleSignIn.getLastSignedInAccount(context)
        return googleAccount
    }



    companion object {
        const val teachers_tag = "teachers"

    }
}