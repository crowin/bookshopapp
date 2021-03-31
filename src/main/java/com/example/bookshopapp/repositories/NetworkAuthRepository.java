package com.example.bookshopapp.repositories;

import com.example.bookshopapp.security.NetworkAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkAuthRepository extends JpaRepository<NetworkAuth, Integer> {

    NetworkAuth findNetworkAuthByNetworkId(String networkId);
}
