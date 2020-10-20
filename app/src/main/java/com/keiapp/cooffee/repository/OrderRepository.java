package com.keiapp.cooffee.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.keiapp.cooffee.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private static final String TAG = "ORDER_REPO_LOG";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("Menu");

    public MutableLiveData<List<OrderModel>> requestOrders() {
        final MutableLiveData<List<OrderModel>> mutableLiveData = new MutableLiveData<>();

        final List<OrderModel> orderModelList = new ArrayList<>();

        orderRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot doc: queryDocumentSnapshots) {
                    orderModelList.add(doc.toObject(OrderModel.class));
                }

                mutableLiveData.setValue(orderModelList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e);
            }
        });

        return mutableLiveData;
    }
}
