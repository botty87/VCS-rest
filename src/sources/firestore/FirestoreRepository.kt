package com.vcs.sources.firestore

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.vcs.data.firestoreDB.*
import java.io.FileInputStream
import java.util.concurrent.CountDownLatch

class FirestoreRepository: DictionaryRepository, DepotsRepository, RetiresRepository, TrashContainersRepository, AreasRepository {
    private val db: Firestore
    private val dictionaryCollection: CollectionReference
    private val depotsCollection: CollectionReference
    private val retiresCollection: CollectionReference
    private val trashContsCollection: CollectionReference
    private val areasCollection: CollectionReference

    init {
        val serviceAccount = FileInputStream("vcs-ambiente-firebase-admin.json")

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://vcs-ambiente.firebaseio.com")
            .build()

        FirebaseApp.initializeApp(options)
        db = FirestoreClient.getFirestore()
        dictionaryCollection = db.collection("dictionary")
        depotsCollection = db.collection("depots")
        retiresCollection = db.collection("retires")
        trashContsCollection = db.collection("trash containers")
        areasCollection = db.collection("areas")
    }

    override fun getDictionary(): List<DictionaryItemFS> {
        dictionaryCollection.get().get().run {
            val dictionary = mutableListOf<DictionaryItemFS>()
            documents.forEach { document ->
                dictionary.add(document.toObject(DictionaryItemFS::class.java))
            }
            return dictionary
        }
    }

    override fun getDepots(): List<DepotItemFS> {
        depotsCollection.get().get().run {
            val depots = mutableListOf<DepotItemFS>()
            documents.forEach { document ->
                depots.add(document.toObject(DepotItemFS::class.java))
            }
            return depots
        }
    }

    override fun getRetires(): List<RetireItemFS> {
        retiresCollection.get().get().run {
            val retires = mutableListOf<RetireItemFS>()
            documents.forEach { document ->
                retires.add(
                        document.toObject(RetireItemFS::class.java).apply {
                            name = document.id
                        }
                )
            }
            return retires
        }
    }

    override fun getTrashContainers(): Multimap<String, TrashContainerFS> {
        val trashContainers = ArrayListMultimap.create<String, TrashContainerFS>()
        val trashDocs = trashContsCollection.listDocuments()
        val countDownLatch = CountDownLatch(trashDocs.count())


        trashDocs.forEach { docRef ->
            docRef.collection("Verde").get().get().run {
                documents.forEach { document ->
                    trashContainers.put(docRef.id, document.toObject(TrashContainerFS::class.java))
                }
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        return trashContainers
    }

    override fun getAreas(): List<AreaItemFS> {
        areasCollection.get().get().run {
            val areas = mutableListOf<AreaItemFS>()
            documents.forEach { document ->
                areas.add(document.toObject(AreaItemFS::class.java).apply {
                    val calendar = document["calendar"] as Map<String, List<DocumentReference>>?
                    calendar?.forEach { dayCalendar ->
                        dayCalendar.value.forEach { retireRef ->
                            calendarMap.put(dayCalendar.key, retireRef.id)
                        }
                    }
                    id = document.id.toInt()
                })
            }
            return areas
        }
    }

}