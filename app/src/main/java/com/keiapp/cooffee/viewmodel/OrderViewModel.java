package com.keiapp.cooffee.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.keiapp.cooffee.model.OrderModel;
import com.keiapp.cooffee.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {

    private OrderRepository orderRepository;
    private MutableLiveData<List<OrderModel>> mutableLiveData;

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<List<OrderModel>> getOrders() {
        if(mutableLiveData==null){
            mutableLiveData = orderRepository.requestOrders();
        }
        return mutableLiveData;
    }
}
